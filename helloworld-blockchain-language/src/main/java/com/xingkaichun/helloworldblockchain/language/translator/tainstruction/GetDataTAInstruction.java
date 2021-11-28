package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class GetDataTAInstruction extends TAInstruction {

    private Symbol result;
    private Symbol key;

    public GetDataTAInstruction(Symbol result, Symbol key) {
        this.result = result;
        this.key = key;
    }




    @Override
    public String toString() {
        return " GutData " + result + " = " + key;
    }

    public Symbol getResult() {
        return result;
    }

    public Symbol getKey() {
        return key;
    }
}
