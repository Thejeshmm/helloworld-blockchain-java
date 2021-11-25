package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class HeapArraySymbol extends VariableSymbol {

    protected int offset;
    protected int layerOffset = 0;
    protected String variableName;

    public static HeapArraySymbol createHeapArraySymbol(Token lexeme, String name, String variableName, String structTypeName, int offset) {
        HeapArraySymbol symbol = new HeapArraySymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.variableName = variableName;
        symbol.structTypeName = structTypeName;
        return symbol;
    }

    public HeapArraySymbol copy() {
        HeapArraySymbol symbol = new HeapArraySymbol();
        symbol.name = this.name;
        symbol.variableName = variableName;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }

    public HeapArrayIndexSymbol createHeapArrayIndexSymbol(String name,Symbol arrayIndexSymbol) {
        HeapArrayIndexSymbol symbol = new HeapArrayIndexSymbol();
        symbol.name = this.name;
        symbol.name = name;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        symbol.arrayIndexSymbol = arrayIndexSymbol;
        return symbol;
    }

    public HeapArrayLengthSymbol createHeapArrayLengthSymbol(String name) {
        HeapArrayLengthSymbol symbol = new HeapArrayLengthSymbol();
        symbol.name = this.name;
        symbol.name = name;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLayerOffset() {
        return layerOffset;
    }

    public void setLayerOffset(int layerOffset) {
        this.layerOffset = layerOffset;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
