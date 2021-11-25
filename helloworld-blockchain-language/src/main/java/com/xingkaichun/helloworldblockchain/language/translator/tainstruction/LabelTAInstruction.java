package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;

public class LabelTAInstruction extends TAInstruction {

    private String labelName;

    public LabelTAInstruction(String labelName) {
        this.labelName = labelName;
    }




    @Override
    public String toString() {
        return this.labelName + ":";
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
