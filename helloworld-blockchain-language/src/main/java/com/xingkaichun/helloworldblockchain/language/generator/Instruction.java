package com.xingkaichun.helloworldblockchain.language.generator;


import com.xingkaichun.helloworldblockchain.language.generator.operand.ImmediateNumber;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Offset;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Operand;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Register;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.*;
import com.xingkaichun.helloworldblockchain.language.util.GeneratorException;
import com.xingkaichun.helloworldblockchain.language.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Instruction {

    public OpCode code;
    public List<Operand> operands;

    public Instruction(OpCode code){
        this.code = code;
        operands = new ArrayList<>();
    }


    public static List<Instruction> loadToRegister(Register target, Symbol arg) {
        List<Instruction> instructions = new ArrayList<>();
        if(arg instanceof ImmediateIntSymbol) {
            ImmediateIntSymbol immediateIntSymbol = (ImmediateIntSymbol)arg;
            instructions.add(Instructions.loadImmediateNumberInstruction(target, new ImmediateNumber(immediateIntSymbol.getIntValue())));
            return instructions;
        }else if(arg instanceof AddressSymbol) {
            AddressSymbol addressSymbol = (AddressSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-addressSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof StaticIntSymbol) {
            StaticIntSymbol staticIntSymbol = (StaticIntSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.ZERO, new Offset(staticIntSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof StaticCharSymbol) {
            StaticCharSymbol staticCharSymbol = (StaticCharSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.ZERO, new Offset(staticCharSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof StaticBooleanSymbol) {
            StaticBooleanSymbol staticBooleanSymbol = (StaticBooleanSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.ZERO, new Offset(staticBooleanSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof StaticStringSymbol) {
            StaticStringSymbol staticStringSymbol = (StaticStringSymbol)arg;
            instructions.add(Instructions.newStringInstruction(target, Register.ZERO, new Offset(staticStringSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof NewObjectSymbol) {
            NewObjectSymbol newObjectSymbol = (NewObjectSymbol)arg;
            instructions.add(Instructions.newObjectInstruction(target, new ImmediateNumber(newObjectSymbol.getFiledCount())));
            return instructions;
        }else if(arg instanceof NewArraySymbol) {
            NewArraySymbol newArraySymbol = (NewArraySymbol)arg;
            instructions.addAll(loadToRegister(Register.S6, newArraySymbol.getArrayLengthSymbol()));
            instructions.add(Instructions.newArrayInstruction(target, Register.S6));
            return instructions;
        }else if(arg instanceof HeapObjectFieldSymbol) {
            HeapObjectFieldSymbol heapObjectFieldSymbol = (HeapObjectFieldSymbol)arg;
            instructions.add(Instructions.loadInstruction(Register.S6, Register.SP, new Offset(-heapObjectFieldSymbol.getOffset())));
            instructions.add(Instructions.loadObjectFieldInstruction(target, Register.S6, new ImmediateNumber(heapObjectFieldSymbol.getFieldIndex())));
            return instructions;
        }else if(arg instanceof HeapObjectSymbol) {
            HeapObjectSymbol heapObjectSymbol = (HeapObjectSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-heapObjectSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof CharSymbol) {
            CharSymbol charSymbol = (CharSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-charSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof BooleanSymbol) {
            BooleanSymbol booleanSymbol = (BooleanSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-booleanSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof IntSymbol) {
            IntSymbol intSymbol = (IntSymbol)arg;
            instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-intSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof HeapArraySymbol) {
            if(arg instanceof HeapArrayIndexSymbol){
                HeapArrayIndexSymbol heapArrayIndexSymbol = (HeapArrayIndexSymbol)arg;
                instructions.addAll(loadToRegister(Register.S6, heapArrayIndexSymbol.getArrayIndexSymbol()));
                instructions.add(Instructions.loadInstruction(Register.S7, Register.SP, new Offset(-heapArrayIndexSymbol.getOffset())));
                instructions.add(Instructions.loadArrayIndexInstruction(target, Register.S7, Register.S6));
                return instructions;
            }else if(arg instanceof HeapArrayLengthSymbol){
                HeapArrayLengthSymbol heapArrayLengthSymbol = (HeapArrayLengthSymbol)arg;
                instructions.add(Instructions.loadInstruction(Register.S6, Register.SP, new Offset(-heapArrayLengthSymbol.getOffset())));
                instructions.add(Instructions.loadArrayLengthInstruction(target, Register.S6));
                return instructions;
            }else {
                HeapArraySymbol heapArraySymbol = (HeapArraySymbol)arg;
                instructions.add(Instructions.loadInstruction(target, Register.SP, new Offset(-heapArraySymbol.getOffset())));
                return instructions;
            }
        }else if(arg instanceof NullSymbol) {
            instructions.add(Instructions.loadImmediateNumberInstruction(target, new ImmediateNumber(0)));
            return instructions;
        }
        throw new GeneratorException();
    }

    public static List<Instruction> storeToMemory(Register source, Symbol arg) {
        List<Instruction> instructions = new ArrayList<>();
        if(arg instanceof AddressSymbol){
            instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-((AddressSymbol)arg).getOffset())));
            return instructions;
        }else if(arg instanceof IntSymbol){
            instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-((IntSymbol)arg).getOffset())));
            return instructions;
        }else if(arg instanceof CharSymbol){
            instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-((CharSymbol)arg).getOffset())));
            return instructions;
        }else if(arg instanceof BooleanSymbol){
            instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-((BooleanSymbol)arg).getOffset())));
            return instructions;
        }else if(arg instanceof HeapObjectFieldSymbol){
            HeapObjectFieldSymbol heapObjectFieldSymbol = (HeapObjectFieldSymbol) arg;
            instructions.add(Instructions.loadInstruction(Register.S4, Register.SP, new Offset(-heapObjectFieldSymbol.getOffset())));
            instructions.add(Instructions.storeObjectFieldInstruction(source, Register.S4, new ImmediateNumber(heapObjectFieldSymbol.getFieldIndex())));
            return instructions;
        }else if(arg instanceof HeapObjectSymbol){
            HeapObjectSymbol heapObjectSymbol = (HeapObjectSymbol) arg;
            instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-heapObjectSymbol.getOffset())));
            return instructions;
        }else if(arg instanceof HeapArraySymbol){
            if(arg instanceof HeapArrayIndexSymbol){
                HeapArrayIndexSymbol heapArrayIndexSymbol = (HeapArrayIndexSymbol)arg;
                instructions.addAll(loadToRegister(Register.S4, heapArrayIndexSymbol.getArrayIndexSymbol()));
                instructions.add(Instructions.loadInstruction(Register.S5, Register.SP, new Offset(-heapArrayIndexSymbol.getOffset())));
                instructions.add(Instructions.storeArrayIndexInstruction(source, Register.S5, Register.S4));
                return instructions;
            }else {
                HeapArraySymbol heapArraySymbol = (HeapArraySymbol) arg;
                instructions.add(Instructions.storeInstruction(source, Register.SP, new Offset(-heapArraySymbol.getOffset())));
                return instructions;
            }
        }else {
            throw new GeneratorException();
        }
    }




    @Override
    public String toString() {
        String s = this.code.toString();

        List<String> operands = new ArrayList<>();
        for(Operand op : this.operands) {
            operands.add(op.toString());
        }
        return s + " " + StringUtil.join(operands, " ");
    }

    public OpCode getOpCode() {
        return this.code;
    }

    public List<Operand> getOperands() {
        return operands;
    }

    public Operand getOperand(int index) {
        return this.operands.get(index);
    }
}
