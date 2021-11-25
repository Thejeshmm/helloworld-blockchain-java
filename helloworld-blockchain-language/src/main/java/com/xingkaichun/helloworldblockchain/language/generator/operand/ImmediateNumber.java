package com.xingkaichun.helloworldblockchain.language.generator.operand;


public class ImmediateNumber extends Operand {

    private int value;

    public ImmediateNumber(int value) {
        this.value = value;
    }




    @Override
    public String toString() {
        return this.value + "";
    }

    public int getValue() {
        return this.value;
    }
}
