package com.xingkaichun.helloworldblockchain.language.generator.operand;

public class Label extends Offset{

    private String label;

    public Label(String label){
        super(0);
        this.label = label;
    }




    @Override
    public String toString() {
        return this.label;
    }

    public String getLabel() {
        return this.label;
    }
}
