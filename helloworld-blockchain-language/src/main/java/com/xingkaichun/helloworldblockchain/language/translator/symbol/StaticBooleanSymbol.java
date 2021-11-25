package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class StaticBooleanSymbol extends VariableSymbol {

    private int offset;


    public static StaticBooleanSymbol createStaticBooleanSymbol(Token lexeme){
        StaticBooleanSymbol symbol = new StaticBooleanSymbol();
        symbol.lexeme = lexeme;
        symbol.name = lexeme.getValue();
        return symbol;
    }

    public StaticBooleanSymbol copy() {
        StaticBooleanSymbol symbol = new StaticBooleanSymbol();
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
}
