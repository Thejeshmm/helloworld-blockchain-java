package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class HeapObjectFieldSymbol extends HeapObjectSymbol {

    protected int fieldIndex;
    protected String fieldType;
    protected String fieldName;
    protected boolean fieldIsArray;


    public static HeapObjectFieldSymbol createHeapObjectFieldSymbol(Token lexeme, String name, String structTypeName, int fieldIndex, String fieldType, String fieldName, int offset) {
        HeapObjectFieldSymbol symbol = new HeapObjectFieldSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;

        symbol.structTypeName = structTypeName;
        symbol.fieldIndex = fieldIndex;
        symbol.fieldType = fieldType;
        symbol.fieldName = fieldName;
        return symbol;
    }

    public HeapObjectFieldSymbol copy() {
        HeapObjectFieldSymbol symbol = new HeapObjectFieldSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.fieldIndex = this.fieldIndex;
        symbol.fieldType = this.fieldType;
        symbol.fieldName = this.fieldName;
        symbol.structTypeName = this.structTypeName;
        symbol.fieldIsArray = this.fieldIsArray;
        return symbol;
    }



    public String getFieldName() {
        return fieldName;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public String getFieldType() {
        return fieldType;
    }
}
