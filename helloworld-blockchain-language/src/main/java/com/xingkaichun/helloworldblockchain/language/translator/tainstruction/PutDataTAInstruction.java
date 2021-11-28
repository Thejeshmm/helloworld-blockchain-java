package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class PutDataTAInstruction extends TAInstruction {

    private Symbol key;
    private Symbol value;

    public PutDataTAInstruction(Symbol key, Symbol value) {
        this.key = key;
        this.value = value;
    }




    @Override
    public String toString() {
        return " PutData " + key + " " + value;
    }

    public Symbol getKey() {
        return key;
    }

    public Symbol getValue() {
        return value;
    }
}
