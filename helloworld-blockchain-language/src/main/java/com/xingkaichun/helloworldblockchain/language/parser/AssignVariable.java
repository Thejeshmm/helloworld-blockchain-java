package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.parser.expression.ComplexVariableExpression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class AssignVariable extends ASTNode{

    public AssignVariable() {
        super(ASTNodeTypes.ASSIGN_VARIABLE);
    }

    public static ASTNode parse(TokenReader reader) throws ParseException {
        if(reader.hasNextNext() && (reader.nextNextIs(".") || reader.nextNextIs("["))){
            return ComplexVariableExpression.parse(reader);
        }else {
            Token lexeme = reader.next();
            AssignVariable declareVariable = new AssignVariable();
            declareVariable.setLexeme(lexeme);
            return declareVariable;
        }
    }

    public Token getAssignVariableName() {
        return this.lexeme;
    }
}
