package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class NullSymbol extends Symbol {



    public static Symbol createNullSymbol(Token lexeme) {
        NullSymbol symbol = new NullSymbol();
        symbol.lexeme = lexeme;
        symbol.name = "null";
        return symbol;
    }

    public NullSymbol copy() {
        NullSymbol symbol = new NullSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        return symbol;
    }
}
