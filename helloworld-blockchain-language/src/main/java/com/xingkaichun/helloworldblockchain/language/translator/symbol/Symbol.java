package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public abstract class Symbol {

    protected Token lexeme;
    protected String name;


    public abstract Symbol copy() ;


    @Override
    public String toString() {
        return name;
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public Token getLexeme() {
        return this.lexeme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
