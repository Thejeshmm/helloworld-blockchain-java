package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class EmptyExpression extends Expression {

    public EmptyExpression() {
        super();
        this.type = ASTNodeTypes.EMPTY_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        EmptyExpression emptyExpression = new EmptyExpression();
        return emptyExpression;
    }
}
