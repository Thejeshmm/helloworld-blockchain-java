package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class NewArraySymbol extends VariableSymbol {

    private Symbol arrayLengthSymbol;


    public static NewArraySymbol createNewArraySymbol(Token lexeme, String structTypeName, Symbol arrayLengthSymbol) {
        String name;
        if(arrayLengthSymbol instanceof StaticIntSymbol){
            name =  "new " + structTypeName+"["+((StaticIntSymbol) arrayLengthSymbol).getIntValue()+"]";
        }else {
            name =  "new " + structTypeName+"[...]";
        }
        NewArraySymbol symbol = new NewArraySymbol();
        symbol.lexeme = lexeme;
        symbol.name = name;
        symbol.structTypeName = structTypeName;
        symbol.arrayLengthSymbol = arrayLengthSymbol;
        return symbol;
    }


    public NewArraySymbol copy() {
        NewArraySymbol symbol = new NewArraySymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.arrayLengthSymbol = this.arrayLengthSymbol;
        symbol.structTypeName = this.structTypeName;
        return symbol;
    }




    public Symbol getArrayLengthSymbol() {
        return arrayLengthSymbol;
    }
}
