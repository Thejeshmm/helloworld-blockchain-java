package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class AssertTAInstruction extends TAInstruction {

    private Symbol object;

    public AssertTAInstruction(Symbol object) {
        this.object = object;
    }




    @Override
    public String toString() {
        return "ASSERT " +  this.object;
    }

    public Symbol getObject() {
        return object;
    }
}
