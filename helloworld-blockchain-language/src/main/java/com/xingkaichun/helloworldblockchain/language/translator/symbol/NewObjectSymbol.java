package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.parser.struct.StructUtil;

public class NewObjectSymbol extends VariableSymbol {

    private int filedCount;


    public static NewObjectSymbol createNewObjectSymbol(Token lexeme, String structTypeName) {
        String name = "new " + structTypeName+"()";
        NewObjectSymbol symbol = new NewObjectSymbol();
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.structTypeName = structTypeName;
        symbol.filedCount = StructUtil.getFiledCount(structTypeName);
        return symbol;
    }


    public NewObjectSymbol copy() {
        NewObjectSymbol symbol = new NewObjectSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.structTypeName = this.structTypeName;
        symbol.filedCount = this.filedCount;
        return symbol;
    }


    public String getStructTypeName() {
        return structTypeName;
    }

    public void setStructTypeName(String structTypeName) {
        this.structTypeName = structTypeName;
    }

    public int getFiledCount() {
        return filedCount;
    }
}
