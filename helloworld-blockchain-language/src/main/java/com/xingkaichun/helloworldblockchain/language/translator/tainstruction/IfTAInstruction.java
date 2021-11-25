package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class IfTAInstruction extends TAInstruction {

    private Symbol conditionSymbol;
    private String labelName;

    public IfTAInstruction(Symbol conditionSymbol, String labelName) {
        this.conditionSymbol = conditionSymbol;
        this.labelName = labelName;
    }




    @Override
    public String toString() {
        return "IF "+this.conditionSymbol+" ELSE "+this.labelName;
    }

    public Symbol getConditionSymbol() {
        return conditionSymbol;
    }

    public void setConditionSymbol(Symbol conditionSymbol) {
        this.conditionSymbol = conditionSymbol;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
