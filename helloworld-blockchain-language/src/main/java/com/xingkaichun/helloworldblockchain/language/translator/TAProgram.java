package com.xingkaichun.helloworldblockchain.language.translator;


import com.xingkaichun.helloworldblockchain.language.translator.symbol.*;
import com.xingkaichun.helloworldblockchain.language.translator.tainstruction.*;
import com.xingkaichun.helloworldblockchain.language.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TAProgram {

    private List<TAInstruction> instructions;
    private StaticSymbolTable staticSymbolTable;

    public TAProgram() {
        this.instructions = new ArrayList<>();
        this.staticSymbolTable = new StaticSymbolTable();
    }


    public void add(TAInstruction code) {
        instructions.add(code);
    }

    public GotoTAInstruction addGoto(String labelName) {
        GotoTAInstruction gotoTAInstruction = new GotoTAInstruction(labelName);
        instructions.add(gotoTAInstruction);
        return gotoTAInstruction;
    }
    public LabelTAInstruction addLabel(String labelName) {
        LabelTAInstruction labelTAInstruction = new LabelTAInstruction(labelName);
        instructions.add(labelTAInstruction);
        return labelTAInstruction;
    }
    public IfTAInstruction addIf(Symbol conditionSymbol) {
        IfTAInstruction ifTAInstruction = new IfTAInstruction(conditionSymbol,null);
        instructions.add(ifTAInstruction);
        return ifTAInstruction;
    }
    public void addFunctionBegin() {
        instructions.add(new FunctionBeginTAInstruction());
    }
    public void addReturn() {
        instructions.add(new ReturnTAInstruction());
    }
    public void addPrint(Symbol object) {
        instructions.add(new PrintTAInstruction(object));
    }
    public void addAssert(Symbol object) {
        instructions.add(new AssertTAInstruction(object));
    }
    public void addReturnValue(int offset, Symbol value) {
        instructions.add(new ReturnValueTAInstruction(offset, value));
    }
    public void addAssignment(Symbol result, String op, Symbol arg1, Symbol arg2) {
        instructions.add(new AssignmentTAInstruction(result, op, arg1, arg2));
    }
    public void addPutData(Symbol key, Symbol value) {
        instructions.add(new PutDataTAInstruction(key, value));
    }
    public void addGetData(Symbol result, Symbol key) {
        instructions.add(new GetDataTAInstruction(result, key));
    }

    public void setStaticSymbols(SymbolTable symbolTable) {
        for(Symbol symbol : symbolTable.getSymbols()) {
            if(symbol instanceof StaticIntSymbol) {
                staticSymbolTable.add((StaticIntSymbol) symbol);
            }
            if(symbol instanceof StaticCharSymbol) {
                staticSymbolTable.add((StaticCharSymbol) symbol);
            }
            if(symbol instanceof StaticBooleanSymbol) {
                staticSymbolTable.add((StaticBooleanSymbol) symbol);
            }
            if(symbol instanceof StaticStringSymbol) {
                staticSymbolTable.add((StaticStringSymbol) symbol);
            }
        }

        for(SymbolTable child : symbolTable.getChildren()) {
            setStaticSymbols(child);
        }
    }



    public List<TAInstruction> getInstructions() {
        return instructions;
    }

    public StaticSymbolTable getStaticSymbolTable(){
        return this.staticSymbolTable;
    }

    @Override
    public String toString() {
        List<String> lines = new ArrayList<>();
        for(TAInstruction opcode : instructions) {
            lines.add(opcode.toString());
        }
        return StringUtil.join(lines, "\n");
    }
}

