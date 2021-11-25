package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class Variable extends Factor {

    private Token typeLexeme = null;

    public Variable(Token token) {
        super(token);
        this.type = ASTNodeTypes.VARIABLE;
    }


    public void setTypeLexeme(Token token) {
        this.typeLexeme = token;
    }
    public Token getTypeLexeme(){
        return this.typeLexeme;
    }
}
