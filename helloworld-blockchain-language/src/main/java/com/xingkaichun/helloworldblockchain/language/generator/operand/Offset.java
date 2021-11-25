package com.xingkaichun.helloworldblockchain.language.generator.operand;

public class Offset extends Operand {

    private int offset;

    public Offset(int offset) {
        super();
        this.offset = offset;
    }




    @Override
    public String toString() {
        return this.offset + "";
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
