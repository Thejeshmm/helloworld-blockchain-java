package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class AddressSymbol extends VariableSymbol {

    private int offset;
    private int layerOffset = 0;
    private String variableName;


    public static AddressSymbol createAddressSymbol(Token lexeme, String name, String structTypeName, String variableName, int offset){
        AddressSymbol symbol = new AddressSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.structTypeName = structTypeName;
        symbol.variableName = variableName;
        return symbol;
    }

    public AddressSymbol copy() {
        AddressSymbol symbol = new AddressSymbol();
        symbol.name = this.name;
        symbol.variableName = this.variableName;
        symbol.lexeme = this.lexeme;
        symbol.offset = this.offset;
        symbol.layerOffset = this.layerOffset;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }


    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setLayerOffset(int offset) {
        this.layerOffset = offset;
    }

    public int getLayerOffset(){
        return this.layerOffset;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
