package com.xingkaichun.helloworldblockchain.language.translator;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.*;
import com.xingkaichun.helloworldblockchain.language.parser.expression.*;
import com.xingkaichun.helloworldblockchain.language.parser.struct.StructUtil;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.*;
import com.xingkaichun.helloworldblockchain.language.translator.tainstruction.*;
import com.xingkaichun.helloworldblockchain.language.util.FunctionDeclareStatementCache;
import com.xingkaichun.helloworldblockchain.language.util.TranslatorException;

import java.util.ArrayList;
import java.util.List;

public class Translator {

    public TAProgram translate(ASTNode astNode) throws TranslatorException {
        Contract contract = (Contract) astNode;
        TAProgram program = new TAProgram();
        SymbolTable symbolTable = new SymbolTable();

        for(ASTNode child : contract.getChildren()) {
            if(child instanceof Struct){
                translateStructStmt(program, child, symbolTable);
            }else if(child instanceof FunctionDeclareStatement){
                translateFunctionDeclareStatement(program, child, symbolTable);
            }else if(child instanceof DeclareStatement){
                translateDeclareStatement(program, child, symbolTable);
            }else if(child instanceof AssignStatement){
                translateAssignStatement(program, child, symbolTable);
            }else {
                throw new TranslatorException();
            }
        }
        program.setStaticSymbols(symbolTable);

        if(symbolTable.existsFunction("main")) {
            symbolTable.createAddressSymbol(null,null,null,null);
            program.add(new SpTAInstruction(-symbolTable.localSize()));
            program.add(new CallTAInstruction((FunctionLabelSymbol) symbolTable.getSymbolByFunctionName("main")));
            program.add(new SpTAInstruction(symbolTable.localSize()));
        }
        return program;
    }

    public void translateStructStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
    }

    public void translateStmt(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        switch (node.getType()) {
            case IF_STATEMENT:
                translateIfStatement(program, node, symbolTable);
                return;
            case ASSIGN_STATEMENT:
                translateAssignStatement(program, node, symbolTable);
                return;
            case DECLARE_STATEMENT:
                translateDeclareStatement(program, node, symbolTable);
                return;
            case FUNCTION_DECLARE_STATEMENT:
                translateFunctionDeclareStatement(program, node, symbolTable);
                return;
            case RETURN_STATEMENT:
                translateReturnStatement(program, node, symbolTable);
                return;
            case CALL_EXPRESSION:
                translateCallExpression(program, node, symbolTable);
                return;
            case FOR_STATEMENT:
                translateForStatement(program, node, symbolTable);
                return;
        }
        throw new TranslatorException("Translator not implement " + node.getType());
    }

    public void translateFunctionBlock(TAProgram program, ASTNode block, SymbolTable parent) throws TranslatorException {
        for(ASTNode child : block.getChildren()) {
            translateStmt(program, child, parent);
        }
    }

    public void translateForBlock(TAProgram program, ASTNode block, SymbolTable symbolTable, String forEndLabelName, String forUpdateStartLabelName) throws TranslatorException {
        for(ASTNode child : block.getChildren()) {
            if(child instanceof BreakStatement){
                program.addGoto(forEndLabelName);
            }else if(child instanceof ContinueStatement){
                program.addGoto(forUpdateStartLabelName);
            }else {
                translateStmt(program, child, symbolTable);
            }
        }
    }

    private void translateReturnStatement(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        ReturnStatement returnStatement = (ReturnStatement)node;
        ReturnVariables returnVariables = returnStatement.getReturnVariables();
        List<ASTNode> astNodeReturnVariables = returnVariables.getReturnVariables();

        List<Symbol> resultValues = new ArrayList<>();
        for (int i=0;i<astNodeReturnVariables.size();i++) {
            ASTNode astNodeReturnVariable = astNodeReturnVariables.get(i);
            if(astNodeReturnVariable instanceof CallExpression){
                List<Symbol> resultValue = translateCallExpression(program, astNodeReturnVariables.get(i), symbolTable);
                resultValues.addAll(resultValue);
            }else {
                Symbol resultValue = translateExpression(program, astNodeReturnVariables.get(i), symbolTable);
                resultValues.add(resultValue);
            }
        }
        for(int i=0;i<resultValues.size();i++){
            program.addReturnValue(resultValues.size()-i, resultValues.get(i));
        }
        program.addReturn();
    }

    private void translateFunctionDeclareStatement(TAProgram program, ASTNode node, SymbolTable parent) throws TranslatorException {
        FunctionDeclareStatement function = (FunctionDeclareStatement)node;
        LabelSymbol functionSymbol = (LabelSymbol) parent.createFunctionLabelSymbol(function.getFunctionName().getLexeme(), function.getFunctionName().getLexeme().getValue());

        program.addLabel(functionSymbol.getLabel());
        program.addFunctionBegin();

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.createAddressSymbol(null,null,null,null);

        FunctionParameters args = function.getFunctionParameters();
        parent.addChildSymbolTable(symbolTable);
        for(DeclareVariable functionParameter : args.getFunctionParameters()) {
            translateDeclareVariable(symbolTable,functionParameter);
        }

        FunctionBlock functionBlock = function.getBlock();
        translateFunctionBlock(program, functionBlock, symbolTable);

        TAInstruction lastTAInstruction = program.getInstructions().get(program.getInstructions().size()-1);
        if(!(lastTAInstruction instanceof ReturnValueTAInstruction)){
            program.addReturn();
        }
    }

    private void translateDeclareStatement(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        DeclareStatement declareStatement = (DeclareStatement)node;
        DeclareVariables declareVariables = declareStatement.getDeclareVariables();
        Expression expression = declareStatement.getExpression();

        List<Symbol> assigneds = new ArrayList<>();
        for(DeclareVariable declareVariable:declareVariables.getDeclareVariables()){
            String variableName = declareVariable.getDeclareVariableName().getDeclareVariableName();
            if(symbolTable.existsVariable(variableName)) {
                throw new TranslatorException("Syntax Error, Identifier " + variableName + " is already defined.");
            }
            Symbol assigned = translateDeclareVariable(symbolTable,declareVariable);
            assigneds.add(assigned);
        }

        if(expression != null){
            if(expression instanceof CallExpression){
                List<Symbol> symbols = translateCallExpression(program, expression, symbolTable);
                for (int i=0;i<symbols.size();i++) {
                    program.addAssignment(assigneds.get(i), "=", symbols.get(i), null);
                }
            }else {
                Symbol address = translateExpression(program, expression, symbolTable);
                program.addAssignment(assigneds.get(0), "=", address, null);
            }
        }
    }

    private Symbol translateDeclareVariable(SymbolTable symbolTable,DeclareVariable declareVariable) {
        DeclareVariableType declareVariableType = declareVariable.getDeclareVariableType();
        DeclareVariableName declareVariableName = declareVariable.getDeclareVariableName();
        String variableType = declareVariableType.getDeclareTypeName();
        String variableName = declareVariableName.getDeclareVariableName();
        Token lexeme = declareVariableName.getToken();
        if(declareVariableType.isArray()){
            Symbol assigned = symbolTable.createHeapArraySymbol(lexeme,variableType,variableName);
            return assigned;
        }else {
            if(TokenType.INT.getName().equals(variableType)){
                Symbol assigned = symbolTable.createIntSymbol(lexeme,variableName,variableName);
                return assigned;
            }else if(TokenType.CHAR.getName().equals(variableType)){
                Symbol assigned = symbolTable.createCharSymbol(lexeme,variableName,variableName);
                return assigned;
            }else if(TokenType.BOOLEAN.getName().equals(variableType)){
                Symbol assigned = symbolTable.createBooleanSymbol(lexeme,variableName,variableName);
                return assigned;
            }else {
                Symbol assigned = symbolTable.createHeapObjectSymbol(lexeme,variableName,variableType,variableName);
                return assigned;
            }
        }
    }

    private void translateAssignStatement(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        AssignStatement assignStatement = (AssignStatement)node;
        AssignVariables assignVariables = assignStatement.getAssignVariables();
        Expression expression = assignStatement.getExpression();

        List<Symbol> assigneds = new ArrayList<>();
        for(ASTNode assignVariable:assignVariables.getAssignVariable()){
            if(assignVariable instanceof ComplexVariableExpression){
                Symbol fieldSymbol = translateComplexFieldExpression(program,assignVariable,symbolTable);
                assigneds.add(fieldSymbol);
            }else {
                AssignVariable assignV = (AssignVariable) assignVariable;
                Token lexeme = assignV.getAssignVariableName();
                Symbol cloneAssigned = symbolTable.cloneVariableSymbol(lexeme, lexeme.getValue());
                if(cloneAssigned == null) {
                    throw new TranslatorException("Syntax Error, Identifier " + lexeme.getValue() + " is not defined.");
                }
                assigneds.add(cloneAssigned);
            }
        }

        if(expression != null){
            if(expression instanceof CallExpression){
                List<Symbol> symbols = translateCallExpression(program, expression, symbolTable);
                for (int i=0;i<symbols.size();i++) {
                    program.addAssignment(assigneds.get(i), "=", symbols.get(i), null);
                }
            }else {
                Symbol address = translateExpression(program, expression, symbolTable);
                program.addAssignment(assigneds.get(0), "=", address, null);
            }
        }
    }

    public void translateIfStatement(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        IfStatement ifStatement = (IfStatement)node;
        ASTNode expr = ifStatement.getCondition();
        Symbol conditionSymbol = translateExpression(program,expr,symbolTable);
        IfTAInstruction ifTAInstruction = program.addIf(conditionSymbol);

        translateIfBlock(program, ifStatement.getBody(), symbolTable);

        GotoTAInstruction gotoInstruction = null;
        if(ifStatement.hasTail()) {
            gotoInstruction = program.addGoto(null);
            LabelTAInstruction labelEndIf = program.addLabel(LabelSymbol.getNextLabel());
            ifTAInstruction.setLabelName(labelEndIf.getLabelName());
        }

        if(ifStatement.getElseBody() != null) {
            translateIfTailBlock(program, ifStatement.getElseBody(), symbolTable);
        } else if(ifStatement.getElseIfStatement() != null) {
            translateIfStatement(program, ifStatement.getElseIfStatement(), symbolTable);
        }

        LabelTAInstruction labelEnd = program.addLabel(LabelSymbol.getNextLabel());
        if(!ifStatement.hasTail()) {
            ifTAInstruction.setLabelName(labelEnd.getLabelName());
        } else {
            gotoInstruction.setLabelName(labelEnd.getLabelName());
        }
    }

    public void translateIfBlock(TAProgram program, ASTNode block, SymbolTable parent) throws TranslatorException {
        SymbolTable symbolTable = new SymbolTable();
        parent.addChildSymbolTable(symbolTable);
        for(ASTNode child : block.getChildren()) {
            translateStmt(program, child, symbolTable);
        }
    }

    public void translateIfTailBlock(TAProgram program, ASTNode block, SymbolTable parent) throws TranslatorException {
        SymbolTable symbolTable = new SymbolTable();
        parent.addChildSymbolTable(symbolTable);
        for(ASTNode child : block.getChildren()) {
            translateStmt(program, child, symbolTable);
        }
    }

    private void translateForStatement(TAProgram program, ASTNode node, SymbolTable parent) {
        SymbolTable symbolTable = new SymbolTable();
        parent.addChildSymbolTable(symbolTable);
        symbolTable.setOffsetIndex(parent.getOffsetIndex());

        ForStatement forStatement = (ForStatement) node;


        ASTNode astNode0 = forStatement.getInitialization();
        if(astNode0 instanceof AssignStatement){
            translateAssignStatement(program,astNode0,symbolTable);
        }else if(astNode0 instanceof DeclareStatement){
            translateDeclareStatement(program,astNode0,symbolTable);
        }else {
            throw new TranslatorException();
        }


        LabelTAInstruction conditionLabel = program.addLabel(LabelSymbol.getNextLabel());
        ASTNode astNode1 = forStatement.getTermination();
        Symbol conditionSymbol = translateExpression(program,astNode1,symbolTable);
        IfTAInstruction ifTAInstruction = program.addIf(conditionSymbol);

        //TODO block create son SymbolTable ?
        String forEndLabelName = LabelSymbol.getNextLabel();
        String forUpdateStartLabelName = LabelSymbol.getNextLabel();

        translateForBlock(program, forStatement.getBody(), symbolTable, forEndLabelName, forUpdateStartLabelName);


        program.addLabel(forUpdateStartLabelName);
        translateAssignStatement(program,forStatement.getUpdate(),symbolTable);


        if(((ForStatement) node).getUpdate() != null) {
            program.addGoto(conditionLabel.getLabelName());
        }


        LabelTAInstruction endLabel = program.addLabel(forEndLabelName);
        ifTAInstruction.setLabelName(endLabel.getLabelName());
    }

    public Symbol translateExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        if(node.getType() == ASTNodeTypes.SCALAR_EXPRESSION) {
            ScalarExpression scalarExpression = (ScalarExpression) node;
            if(scalarExpression.getScalarType().equals(TokenType.CHAR.getName())){
                Symbol address = symbolTable.createStaticCharSymbol(node.getLexeme());
                return address;
            }else if(scalarExpression.getScalarType().equals(TokenType.INT.getName())){
                Symbol address = symbolTable.createStaticIntSymbol(node.getLexeme());
                return address;
            }else if(scalarExpression.getScalarType().equals(TokenType.BOOLEAN.getName())){
                Symbol address = symbolTable.createStaticBooleanSymbol(node.getLexeme());
                return address;
            }else {
                throw new TranslatorException();
            }
        }else if(node.getType() == ASTNodeTypes.VARIABLE_EXPRESSION) {
            VariableExpression variableExpression = (VariableExpression) node;
            Symbol address = symbolTable.cloneVariableSymbol(null,variableExpression.getVariableName());
            if(address == null){
                throw new TranslatorException();
            }
            return address;
        }else if(node.getType() == ASTNodeTypes.CALL_EXPRESSION) {
            Symbol address = translateCallExpression(program,node,symbolTable).get(0);
            return address;
        }else if(node.getType() == ASTNodeTypes.STATIC_STRING_EXPRESSION) {
            Symbol address = translateStaticStringExpression(program,node,symbolTable);
            return address;
        }else if(node.getType() == ASTNodeTypes.NEW_OBJECT_EXPRESSION) {
            Symbol address = translateNewObjectExpression(program,node,symbolTable);
            return address;
        }else if(node.getType() == ASTNodeTypes.NEW_ARRAY_EXPRESSION) {
            Symbol address = translateNewArrayExpression(program,node,symbolTable);
            return address;
        }else if(node.getType() == ASTNodeTypes.COMPLEX_VARIABLE_EXPRESSION) {
            Symbol address = translateComplexFieldExpression(program,node,symbolTable);
            return address;
        }else if(node.getType() == ASTNodeTypes.NULL_EXPRESSION) {
            Symbol address = symbolTable.createNullSymbol(node.getLexeme());
            return address;
        }else if(node instanceof Expression){
            List<Symbol> expressionAddresss = new ArrayList<>();
            for(ASTNode child : node.getChildren()) {
                Symbol expressionAddress = translateExpression(program,child, symbolTable);
                expressionAddresss.add(expressionAddress);
            }
            Symbol returnExpressionAddress;
            if(TokenType.BOOLEAN.EQ.getName().equals(node.getLexeme().getValue())){
                returnExpressionAddress = symbolTable.createBooleanSymbol(node.getLexeme(),null,null);
            }else {
                returnExpressionAddress = symbolTable.createAddressSymbol(node.getLexeme(),null,null,null);
            }
            if(expressionAddresss.size() == 1){
                program.addAssignment(returnExpressionAddress, node.getLexeme().getValue(), expressionAddresss.get(0), null);
            }else if(expressionAddresss.size() == 2){
                program.addAssignment(returnExpressionAddress, node.getLexeme().getValue(), expressionAddresss.get(0), expressionAddresss.get(1));
            }else {
                throw new TranslatorException();
            }
            return returnExpressionAddress;
        }
        throw new TranslatorException("Unexpected node type :" + node.getType());
    }

    private Symbol translateStaticStringExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) {
        StaticStringExpression staticStringExpression = (StaticStringExpression)node;
        return symbolTable.createStaticStringSymbol(staticStringExpression.getLexeme());
    }

    private Symbol translateComplexFieldExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) {
        ComplexVariableExpression expression = (ComplexVariableExpression)node;
        Symbol clone = symbolTable.cloneVariableSymbol(expression.getLexeme(),expression.getVariableName()).copy();
        ComplexSonExpression complexSonExpression = (ComplexSonExpression) expression.getChildren().get(0);
        while (complexSonExpression != null){
            if(clone instanceof HeapObjectFieldSymbol && complexSonExpression instanceof ComplexFieldExpression){
                HeapObjectFieldSymbol heapObjectFieldSymbol = (HeapObjectFieldSymbol)clone;
                ComplexFieldExpression complexFieldExpression = (ComplexFieldExpression) complexSonExpression;

                boolean tempFieldIsArray = StructUtil.getFiledIsArray(heapObjectFieldSymbol.getStructTypeName(),heapObjectFieldSymbol.getFieldName());
                if(tempFieldIsArray){
                    if(complexFieldExpression.getFieldName().equals("length")){
                        HeapArraySymbol temp = (HeapArraySymbol) symbolTable.createHeapArraySymbol(complexFieldExpression.getLexeme(),heapObjectFieldSymbol.getFieldType(),null);
                        program.addAssignment(temp,"=", clone, null);
                        String name = temp.getName()+".length";
                        clone = temp.createHeapArrayLengthSymbol(name);
                    }else {
                        throw new TranslatorException();
                    }
                }else {
                    HeapObjectSymbol temp = (HeapObjectSymbol) symbolTable.createHeapObjectSymbol(complexFieldExpression.getLexeme(),null,heapObjectFieldSymbol.getFieldType(),null);
                    program.addAssignment(temp,"=", clone, null);

                    int tempFieldIndex = StructUtil.getFiledIndex(heapObjectFieldSymbol.getFieldType(),complexFieldExpression.getFieldName());
                    String tempFieldType = StructUtil.getFiledType(heapObjectFieldSymbol.getFieldType(),complexFieldExpression.getFieldName());
                    String tempName = heapObjectFieldSymbol.getFieldName()+"."+complexFieldExpression.getFieldName();
                    clone = temp.createHeapObjectFieldSymbol(tempName,tempFieldIndex,tempFieldType,complexFieldExpression.getFieldName(),tempFieldIsArray);
                }
            }else if(clone instanceof HeapObjectFieldSymbol && complexSonExpression instanceof ComplexArrayExpression){
                HeapObjectFieldSymbol heapObjectFieldSymbol = (HeapObjectFieldSymbol)clone;
                ComplexArrayExpression complexArrayExpression = (ComplexArrayExpression) complexSonExpression;
                HeapArraySymbol temp = (HeapArraySymbol) symbolTable.createHeapArraySymbol(complexArrayExpression.getLexeme(),heapObjectFieldSymbol.getFieldType(),null);
                program.addAssignment(temp,"=", clone, null);

                Symbol arrayIndexSymbol = translateExpression(program,complexArrayExpression.getArrayIndexExpression(),symbolTable);
                if(arrayIndexSymbol instanceof StaticIntSymbol){
                    StaticIntSymbol staticIntSymbol = (StaticIntSymbol) arrayIndexSymbol;
                    String name = temp.getName()+"["+staticIntSymbol.getIntValue()+"]";
                    clone = temp.createHeapArrayIndexSymbol(name,staticIntSymbol);
                }else if(arrayIndexSymbol instanceof IntSymbol){
                    IntSymbol intSymbol = (IntSymbol) arrayIndexSymbol;
                    String name = temp.getName()+"[...]";
                    clone = temp.createHeapArrayIndexSymbol(name,intSymbol);
                }else {
                    throw new TranslatorException();
                }
            }else if(clone instanceof HeapObjectSymbol && complexSonExpression instanceof ComplexFieldExpression){
                HeapObjectSymbol heapObjectSymbol = (HeapObjectSymbol)clone;
                ComplexFieldExpression complexFieldExpression = (ComplexFieldExpression) complexSonExpression;
                int tempFieldIndex = StructUtil.getFiledIndex(heapObjectSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                boolean tempFieldIsArray = StructUtil.getFiledIsArray(heapObjectSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                String tempFieldType = StructUtil.getFiledType(heapObjectSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                String tempName = heapObjectSymbol.getName()+"."+complexFieldExpression.getFieldName();
                clone = heapObjectSymbol.createHeapObjectFieldSymbol(tempName,tempFieldIndex,tempFieldType,complexFieldExpression.getFieldName(),tempFieldIsArray);
            }else if(clone instanceof HeapObjectSymbol && complexSonExpression instanceof ComplexArrayExpression){
                HeapObjectSymbol heapObjectSymbol = (HeapObjectSymbol)clone;
                ComplexArrayExpression complexArrayExpression = (ComplexArrayExpression) complexSonExpression;
                HeapArraySymbol temp = (HeapArraySymbol) symbolTable.createHeapArraySymbol(complexArrayExpression.getLexeme(),heapObjectSymbol.getStructTypeName(),null);
                program.addAssignment(temp,"=", clone, null);

                Symbol arrayIndexSymbol = translateExpression(program,complexArrayExpression.getArrayIndexExpression(),symbolTable);
                if(arrayIndexSymbol instanceof StaticIntSymbol){
                    StaticIntSymbol staticIntSymbol = (StaticIntSymbol) arrayIndexSymbol;
                    String name = temp.getName()+"["+staticIntSymbol.getIntValue()+"]";
                    clone = temp.createHeapArrayIndexSymbol(name,staticIntSymbol);
                }else {
                    throw new TranslatorException();
                }
            }else if(clone instanceof HeapArrayIndexSymbol && complexSonExpression instanceof ComplexFieldExpression){
                HeapArrayIndexSymbol heapArrayIndexSymbol = (HeapArrayIndexSymbol)clone;
                ComplexFieldExpression complexFieldExpression = (ComplexFieldExpression) complexSonExpression;
                HeapObjectSymbol temp = (HeapObjectSymbol) symbolTable.createHeapObjectSymbol(complexFieldExpression.getLexeme(),null,heapArrayIndexSymbol.getStructTypeName(),null);
                program.addAssignment(temp,"=", clone, null);

                int tempFieldIndex = StructUtil.getFiledIndex(heapArrayIndexSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                boolean tempFieldIsArray = StructUtil.getFiledIsArray(heapArrayIndexSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                String tempFieldType = StructUtil.getFiledType(heapArrayIndexSymbol.getStructTypeName(),complexFieldExpression.getFieldName());
                String tempName = temp.getName()+"."+complexFieldExpression.getFieldName();
                clone = temp.createHeapObjectFieldSymbol(tempName,tempFieldIndex,tempFieldType,complexFieldExpression.getFieldName(),tempFieldIsArray);
            }else if(clone instanceof HeapArrayIndexSymbol && complexSonExpression instanceof ComplexArrayExpression){
                HeapArrayIndexSymbol heapArrayIndexSymbol = (HeapArrayIndexSymbol)clone;
                ComplexArrayExpression complexArrayExpression = (ComplexArrayExpression) complexSonExpression;

                HeapArraySymbol heapArraySymbol = (HeapArraySymbol) symbolTable.createHeapArraySymbol(heapArrayIndexSymbol.getLexeme(),heapArrayIndexSymbol.getStructTypeName(),null);
                program.addAssignment(heapArraySymbol,"=", heapArrayIndexSymbol, null);

                Symbol arrayIndexSymbol = translateExpression(program,complexArrayExpression.getArrayIndexExpression(),symbolTable);
                if(arrayIndexSymbol instanceof StaticIntSymbol){
                    StaticIntSymbol staticIntSymbol = (StaticIntSymbol) arrayIndexSymbol;
                    String name = heapArraySymbol.getName()+"["+staticIntSymbol.getIntValue()+"]";
                    clone = heapArraySymbol.createHeapArrayIndexSymbol(name,staticIntSymbol);
                }else {
                    throw new TranslatorException();
                }
            }else if(clone instanceof HeapArraySymbol && complexSonExpression instanceof ComplexArrayExpression){
                HeapArraySymbol heapArraySymbol = (HeapArraySymbol)clone;
                ComplexArrayExpression complexArrayExpression = (ComplexArrayExpression) complexSonExpression;

                Symbol arrayIndexSymbol = translateExpression(program,complexArrayExpression.getArrayIndexExpression(),symbolTable);
                if(arrayIndexSymbol instanceof StaticIntSymbol){
                    StaticIntSymbol staticIntSymbol = (StaticIntSymbol) arrayIndexSymbol;
                    String name = heapArraySymbol.getName()+"["+staticIntSymbol.getIntValue()+"]";
                    clone = heapArraySymbol.createHeapArrayIndexSymbol(name,staticIntSymbol);
                }else {
                    String name = heapArraySymbol.getName()+"["+arrayIndexSymbol.getName()+"]";
                    clone = heapArraySymbol.createHeapArrayIndexSymbol(name,arrayIndexSymbol);
                }
            }else if(clone instanceof HeapArraySymbol && complexSonExpression instanceof ComplexFieldExpression){
                HeapArraySymbol heapArraySymbol = (HeapArraySymbol)clone;
                String name = heapArraySymbol.getName()+".length";
                clone = heapArraySymbol.createHeapArrayLengthSymbol(name);
            }else {
                throw new TranslatorException();
            }
            if (complexSonExpression.getChildren().isEmpty()){
                complexSonExpression = null;
            }else {
                complexSonExpression = (ComplexSonExpression) complexSonExpression.getChildren().get(0);
            }
        }
        return clone;
    }

    private Symbol translateNewObjectExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) {
        NewObjectExpression newObjectExpression = (NewObjectExpression) node;
        Symbol tempAddress = symbolTable.createHeapObjectSymbol(newObjectExpression.getObjectVariableType(),null,newObjectExpression.getObjectVariableType().getValue(),null);
        Symbol address = symbolTable.createNewObjectSymbol(newObjectExpression.getObjectVariableType(), newObjectExpression.getObjectVariableType().getValue());
        program.addAssignment(tempAddress,"=", address, null);
        return tempAddress;
    }

    private Symbol translateNewArrayExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) {
        NewArrayExpression newArrayExpression = (NewArrayExpression) node;
        HeapArraySymbol arrayAddress = (HeapArraySymbol) symbolTable.createHeapArraySymbol(newArrayExpression.getLexeme(), newArrayExpression.getArrayVariableType(),null);

        Symbol arrayLengthSymbol = translateExpression(program,newArrayExpression.getArrayLengthExpression(),symbolTable);
        Symbol address = symbolTable.createNewArraySymbol(newArrayExpression.getLexeme(), newArrayExpression.getArrayVariableType(),arrayLengthSymbol);
        program.addAssignment(arrayAddress,"=", address, null);

        List<ASTNode> values = newArrayExpression.getValues();
        for(int i=0; i<values.size(); i++){
            Symbol valuei = translateExpression(program,values.get(i),symbolTable);
            Symbol tempArrayIndexAddress = arrayAddress.createHeapArrayIndexSymbol(null,ImmediateIntSymbol.createImmediateIntSymbol(null,i));
            program.addAssignment(tempArrayIndexAddress,"=", valuei, null);
        }
        return arrayAddress;
    }

    private List<Symbol> translateCallExpression(TAProgram program, ASTNode node, SymbolTable symbolTable) throws TranslatorException {
        CallExpression callExpression = (CallExpression) node;

        String functionName = callExpression.getFunctionName();
        List<ASTNode> parameters = callExpression.getParameters();

        if("print".equals(functionName)){
            ASTNode expr = parameters.get(0);
            Symbol address = translateExpression(program, expr, symbolTable);
            program.addPrint(address);
            return new ArrayList<>();
        }
        if("assert".equals(functionName)){
            ASTNode expr = parameters.get(0);
            Symbol address = translateExpression(program, expr, symbolTable);
            program.addAssert(address);
            return new ArrayList<>();
        }
        if("put".equals(functionName)){
            ASTNode parameter1 = parameters.get(0);
            Symbol parameter1Address = translateExpression(program, parameter1, symbolTable);
            ASTNode parameter2 = parameters.get(1);
            Symbol parameter2Address = translateExpression(program, parameter2, symbolTable);
            program.addPutData(parameter1Address,parameter2Address);
            return new ArrayList<>();
        }
        if("get".equals(functionName)){
            ASTNode parameter1 = parameters.get(0);
            Symbol parameter1Address = translateExpression(program, parameter1, symbolTable);
            Symbol result = symbolTable.createHeapObjectSymbol(null,null,"string",null);
            program.addGetData(result,parameter1Address);
            List<Symbol> returnValues = new ArrayList<>();
            returnValues.add(result);
            return returnValues;
        }

        List<Symbol> parameterValues = new ArrayList<>();
        for(int i = 0; i < parameters.size(); i++) {
            ASTNode expr = parameters.get(i);
            Symbol address = translateExpression(program, expr, symbolTable);
            parameterValues.add(address);
        }

        List<Symbol> returnValues = new ArrayList<>();
        FunctionDeclareStatement functionDeclareStatement = FunctionDeclareStatementCache.getByName(functionName);
        List<FunctionReturn> functionReturns = functionDeclareStatement.getFunctionReturns().getFunctionReturns();
        for(int i=0; i<functionReturns.size(); i++){
            FunctionReturn functionReturn = functionReturns.get(0);
            String variableType = functionReturn.getReturnType();
            String name = "return"+i;
            Token lexeme = functionReturn.getLexeme();
            Symbol returnValue;
            if(TokenType.INT.getName().equals(variableType)){
                returnValue = symbolTable.createIntSymbol(lexeme,name,null);
            }else if(TokenType.CHAR.getName().equals(variableType)){
                returnValue = symbolTable.createCharSymbol(lexeme,name,null);
            }else if(TokenType.BOOLEAN.getName().equals(variableType)){
                returnValue = symbolTable.createBooleanSymbol(lexeme,name,null);
            }else {
                returnValue = symbolTable.createHeapObjectSymbol(lexeme,name,variableType,null);
            }
            returnValues.add(returnValue);
        }

        for(int i = 0; i < parameters.size(); i++) {
            Symbol parameterValue = parameterValues.get(i);
            int parameterOffset = -(symbolTable.localSize() + i + 1);
            program.add(new ParameterTAInstruction(parameterOffset,parameterValue));
        }

        Symbol functionAddress = symbolTable.getSymbolByFunctionName(functionName);
        if(functionAddress == null) {
            throw new TranslatorException("function " + functionName + " not found");
        }
        program.add(new SpTAInstruction(-symbolTable.localSize()));
        program.add(new CallTAInstruction((FunctionLabelSymbol) functionAddress));
        program.add(new SpTAInstruction(symbolTable.localSize()));
        return returnValues;
    }
}
