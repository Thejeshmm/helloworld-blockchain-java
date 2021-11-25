package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class StaticIntSymbol extends AbstractTypeSymbol {

    private int offset;


    public static StaticIntSymbol createStaticIntSymbol(Token lexeme){
        StaticIntSymbol symbol = new StaticIntSymbol();
        symbol.lexeme = lexeme;
        symbol.name = lexeme.getValue();
        return symbol;
    }

    public StaticIntSymbol copy() {
        StaticIntSymbol symbol = new StaticIntSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getIntValue() {
        return Integer.valueOf(this.lexeme.getValue());
    }

}
