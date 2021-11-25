package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ReturnVariable extends ASTNode{

    public ReturnVariable() {
        super(ASTNodeTypes.RETURN_VARIABLE);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        ASTNode expression = Expression.parse(reader);
        return expression;
    }
}
