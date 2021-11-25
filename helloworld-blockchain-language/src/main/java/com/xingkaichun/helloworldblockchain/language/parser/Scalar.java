package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class Scalar extends Factor{

    public Scalar(Token token) {
        super(token);
        this.type = ASTNodeTypes.SCALAR;
    }
}
