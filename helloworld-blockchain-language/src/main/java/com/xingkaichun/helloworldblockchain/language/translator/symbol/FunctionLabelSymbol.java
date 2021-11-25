package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class FunctionLabelSymbol extends LabelSymbol {

    protected String functionName;


    public static Symbol createFunctionLabelSymbol(Token lexeme, String functionName) {
        String label = getNextLabel();
        FunctionLabelSymbol symbol = new FunctionLabelSymbol();
        symbol.label = label;
        symbol.lexeme = lexeme;
        symbol.name = label;
        symbol.functionName = functionName;
        return symbol;
    }

    public FunctionLabelSymbol copy() {
        FunctionLabelSymbol symbol = new FunctionLabelSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.label = this.label;
        symbol.functionName = functionName;
        return symbol;
    }




    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
