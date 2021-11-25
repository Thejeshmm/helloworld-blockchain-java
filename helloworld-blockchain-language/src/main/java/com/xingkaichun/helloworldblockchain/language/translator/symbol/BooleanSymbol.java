package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;

public class BooleanSymbol extends VariableSymbol {

    private int offset;
    private int layerOffset = 0;
    private String variableName;


    public static BooleanSymbol createBooleanSymbol(Token lexeme, String name, String variableName, int offset){
        BooleanSymbol symbol = new BooleanSymbol();
        symbol.offset = offset;
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.structTypeName = TokenType.BOOLEAN.getName();
        symbol.variableName = variableName;
        return symbol;
    }

    public BooleanSymbol copy() {
        BooleanSymbol symbol = new BooleanSymbol();
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
