package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.FunctionLabelSymbol;

public class CallTAInstruction extends TAInstruction {

    private FunctionLabelSymbol functionLabelSymbol;

    public CallTAInstruction(FunctionLabelSymbol functionLabelSymbol) {
        this.functionLabelSymbol = functionLabelSymbol;
    }




    @Override
    public String toString() {
        return "CALL " + this.functionLabelSymbol;
    }

    public FunctionLabelSymbol getFunctionLabelSymbol() {
        return functionLabelSymbol;
    }
}
