package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class PrintTAInstruction extends TAInstruction {

    private Symbol object;

    public PrintTAInstruction(Symbol object) {
        this.object = object;
    }




    @Override
    public String toString() {
        return "PRINT " +  this.object;
    }

    public Symbol getObject() {
        return object;
    }
}
