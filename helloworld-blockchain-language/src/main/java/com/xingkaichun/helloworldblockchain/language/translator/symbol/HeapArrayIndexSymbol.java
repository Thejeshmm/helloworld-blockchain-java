package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class HeapArrayIndexSymbol extends HeapArraySymbol {

    protected Symbol arrayIndexSymbol;

    public static HeapArrayIndexSymbol createHeapArrayIndexSymbol(Token lexeme, String name, String variableName, String structTypeName, int offset, Symbol arrayIndexSymbol) {
        HeapArrayIndexSymbol symbol = new HeapArrayIndexSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.variableName = variableName;
        symbol.structTypeName = structTypeName;
        symbol.arrayIndexSymbol = arrayIndexSymbol;
        return symbol;
    }

    public HeapArrayIndexSymbol copy() {
        HeapArrayIndexSymbol symbol = new HeapArrayIndexSymbol();
        symbol.name = this.name;
        symbol.variableName = variableName;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        symbol.arrayIndexSymbol = this.arrayIndexSymbol;
        return symbol;
    }


    public Symbol getArrayIndexSymbol() {
        return arrayIndexSymbol;
    }
}
