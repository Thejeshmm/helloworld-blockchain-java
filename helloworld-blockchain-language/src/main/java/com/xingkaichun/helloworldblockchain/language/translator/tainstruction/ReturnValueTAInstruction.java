package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class ReturnValueTAInstruction extends TAInstruction {

    private int offset;
    private Symbol value;

    public ReturnValueTAInstruction(int offset, Symbol value) {
        this.offset = offset;
        this.value = value;
    }




    @Override
    public String toString() {
        return "RETURN_VALUE " +  this.offset + " " + this.value;
    }

    public int getOffset() {
        return offset;
    }

    public Symbol getValue() {
        return value;
    }
}
