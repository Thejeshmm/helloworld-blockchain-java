package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class StaticThisSymbol extends AbstractTypeSymbol {


    public static StaticThisSymbol createStaticThisSymbol(Token lexeme){
        StaticThisSymbol symbol = new StaticThisSymbol();
        symbol.lexeme = lexeme;
        symbol.name = lexeme.getValue();
        symbol.structTypeName = "This";
        return symbol;
    }

    public StaticThisSymbol copy() {
        StaticThisSymbol symbol = new StaticThisSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }
}
