package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

public class LabelSymbol extends Symbol {

    protected String label;
    public static int labelCounter = 0;


    public static Symbol createLabelSymbol(Token lexeme) {
        String label = getNextLabel();
        LabelSymbol symbol = new LabelSymbol();
        symbol.label = label;
        symbol.lexeme = lexeme;
        symbol.name = label;
        return symbol;
    }

    public LabelSymbol copy() {
        LabelSymbol symbol = new LabelSymbol();
        symbol.name = this.name;
        symbol.lexeme = this.lexeme;
        symbol.label = this.label;
        return symbol;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static String getNextLabel() {
        String label = "L" + LabelSymbol.labelCounter++;
        return label;
    }
}
