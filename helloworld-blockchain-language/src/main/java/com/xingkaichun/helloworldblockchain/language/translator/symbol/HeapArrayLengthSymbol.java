package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class HeapArrayLengthSymbol extends HeapArraySymbol {

    public static HeapArrayLengthSymbol createHeapArrayLengthSymbol(Token lexeme, String name, String variableName, String structTypeName, int offset) {
        HeapArrayLengthSymbol symbol = new HeapArrayLengthSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.variableName = variableName;
        symbol.structTypeName = structTypeName;
        return symbol;
    }

    public HeapArrayLengthSymbol copy() {
        HeapArrayLengthSymbol symbol = new HeapArrayLengthSymbol();
        symbol.name = this.name;
        symbol.variableName = variableName;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }
}
