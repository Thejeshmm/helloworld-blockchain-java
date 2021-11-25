package com.xingkaichun.helloworldblockchain.language.translator.tainstruction;

import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.Symbol;

public class AssignmentTAInstruction extends TAInstruction {

    private Symbol result;
    private String op;
    private Symbol arg1;
    private Symbol arg2;

    public AssignmentTAInstruction(Symbol result, String op, Symbol arg1, Symbol arg2) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }




    @Override
    public String toString() {
        if(arg2 == null) {
            return result + " = " + arg1;
        } else {
            return result + " = " + arg1 + " "+ op +" " + arg2;
        }
    }

    public Symbol getResult() {
        return result;
    }

    public void setArg1(Symbol arg) {
        this.arg1 = arg;
    }

    public Symbol getArg1() {
        return this.arg1;
    }

    public void setArg2(Symbol arg) {
        this.arg2 = arg;
    }

    public Symbol getArg2() {return this.arg2;}

    public void setResult(Symbol address) {
        this.result = address;
    }

    public String getOp() {
        return this.op;
    }
}
