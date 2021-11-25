package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class VariableExpression extends Expression {

    public VariableExpression() {
        super();
        this.type = ASTNodeTypes.VARIABLE_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        VariableExpression callExpression = new VariableExpression();
        callExpression.setLexeme(reader.next());
        return callExpression;
    }

    public String getVariableName() {
        return this.lexeme.getValue();
    }
}
