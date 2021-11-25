package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;

public class GotoTAInstruction extends TAInstruction {

    private String labelName;

    public GotoTAInstruction(String labelName) {
        this.labelName = labelName;
    }




    @Override
    public String toString() {
        return "GOTO " + this.labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
