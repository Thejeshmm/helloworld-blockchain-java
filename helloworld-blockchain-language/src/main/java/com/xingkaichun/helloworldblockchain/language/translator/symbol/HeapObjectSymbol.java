package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class HeapObjectSymbol extends VariableSymbol {

    protected int offset;
    protected int layerOffset = 0;
    private String variableName;

    public static HeapObjectSymbol createHeapObjectSymbol(Token lexeme, String name, String variableName, String structTypeName, int offset) {
        HeapObjectSymbol symbol = new HeapObjectSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.variableName = variableName;
        symbol.structTypeName = structTypeName;
        return symbol;
    }

    public HeapObjectSymbol copy() {
        HeapObjectSymbol symbol = new HeapObjectSymbol();
        symbol.name = this.name;
        symbol.variableName = variableName;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }

    public HeapObjectFieldSymbol createHeapObjectFieldSymbol(String name, int fieldIndex, String fieldType, String fieldName,boolean fieldIsArray) {
        HeapObjectFieldSymbol symbol = new HeapObjectFieldSymbol();
        symbol.name = name;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.fieldIndex = fieldIndex;
        symbol.fieldType = fieldType;
        symbol.fieldName = fieldName;
        symbol.structTypeName = this.structTypeName;
        symbol.fieldIsArray = fieldIsArray;
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
