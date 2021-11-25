package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class StaticCharSymbol extends VariableSymbol {

    private int offset;


    public static StaticCharSymbol createStaticCharSymbol(Token lexeme){
        StaticCharSymbol symbol = new StaticCharSymbol();
        symbol.lexeme = lexeme;
        symbol.name = lexeme.getValue();
        return symbol;
    }

    public StaticCharSymbol copy() {
        StaticCharSymbol symbol = new StaticCharSymbol();
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
