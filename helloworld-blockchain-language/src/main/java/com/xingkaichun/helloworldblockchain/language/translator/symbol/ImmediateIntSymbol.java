package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class ImmediateIntSymbol extends AbstractTypeSymbol {

    int value;


    public static ImmediateIntSymbol createImmediateIntSymbol(Token lexeme,int value){
        ImmediateIntSymbol symbol = new ImmediateIntSymbol();
        symbol.lexeme = lexeme;
        symbol.value = value;
        return symbol;
    }

    public ImmediateIntSymbol copy() {
        ImmediateIntSymbol symbol = new ImmediateIntSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.structTypeName = this.structTypeName;
        symbol.value = this.value;
        return symbol;
    }

    public int getIntValue() {
        return value;
    }

}
