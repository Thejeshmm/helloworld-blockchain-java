package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;

public class SpTAInstruction extends TAInstruction {

    private int offset;

    public SpTAInstruction(int offset) {
        this.offset = offset;
    }




    @Override
    public String toString() {
        return "SP " + this.offset;
    }

    public int getOffset() {
        return offset;
    }
}
