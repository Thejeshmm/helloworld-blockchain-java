package com.xingkaichun.helloworldblockchain.language.generator;

import com.xingkaichun.helloworldblockchain.language.generator.operand.ImmediateNumber;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Label;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Offset;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Register;

import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class Instructions {

    public static Instruction newStringInstruction(Register target, Register loadFromBase, Offset offset) {
        Instruction i = new Instruction(OpCode.NEW_STRING);
        i.operands.add(target);
        i.operands.add(loadFromBase);
        i.operands.add(offset);
        return i;
    }
    public static Instruction newObjectInstruction(Register r1,ImmediateNumber immediateNumber) {
        Instruction i = new Instruction(OpCode.NEW_OBJECT);
        i.operands.add(r1);
        i.operands.add(immediateNumber);
        return i;
    }
    public static Instruction newArrayInstruction(Register r1,Register r2) {
        Instruction i = new Instruction(OpCode.NEW_ARRAY);
        i.operands.add(r1);
        i.operands.add(r2);
        return i;
    }


    public static Instruction loadInstruction(Register loadTo, Register loadFromBase, Offset offset) {
        Instruction i = new Instruction(OpCode.LOAD);
        i.operands.add(loadTo);
        i.operands.add(loadFromBase);
        i.operands.add(offset);
        return i;
    }
    public static Instruction loadArrayIndexInstruction(Register target, Register baseAddress, Register offset) {
        Instruction i = new Instruction(OpCode.LOAD_ARRAY_INDEX);
        i.operands.add(target);
        i.operands.add(baseAddress);
        i.operands.add(offset);
        return i;
    }
    public static Instruction loadArrayLengthInstruction(Register target, Register baseAddress) {
        Instruction i = new Instruction(OpCode.LOAD_ARRAY_LENGTH);
        i.operands.add(target);
        i.operands.add(baseAddress);
        return i;
    }
    public static Instruction loadImmediateNumberInstruction(Register target, ImmediateNumber immediateNumber) {
        Instruction i = new Instruction(OpCode.LOAD_IMMEDIATE_NUMBER);
        i.operands.add(target);
        i.operands.add(immediateNumber);
        return i;
    }
    public static Instruction loadObjectFieldInstruction(Register a, Register source, ImmediateNumber immediateNumber) {
        Instruction i = new Instruction(OpCode.LOAD_OBJECT_FIELD);
        i.operands.add(a);
        i.operands.add(source);
        i.operands.add(immediateNumber);
        return i;
    }
    public static Instruction loadThisInstruction(Register storeTo) {
        Instruction i = new Instruction(OpCode.LOAD_THIS);
        i.operands.add(storeTo);
        return i;
    }

    public static Instruction storeInstruction(Register storeTo, Register storeFromBase, Offset offset) {
        Instruction i = new Instruction(OpCode.STORE);
        i.operands.add(storeTo);
        i.operands.add(storeFromBase);
        i.operands.add(offset);
        return i;
    }
    public static Instruction storeArrayIndexInstruction(Register source, Register targetBaseAddress, Register offset) {
        Instruction i = new Instruction(OpCode.STORE_ARRAY_INDEX);
        i.operands.add(source);
        i.operands.add(targetBaseAddress);
        i.operands.add(offset);
        return i;
    }
    public static Instruction storeObjectFieldInstruction(Register a, Register source, ImmediateNumber immediateNumber) {
        Instruction i = new Instruction(OpCode.STORE_OBJECT_FIELD);
        i.operands.add(a);
        i.operands.add(source);
        i.operands.add(immediateNumber);
        return i;
    }



    public static Instruction printObjectInstruction(Register r1) {
        Instruction i = new Instruction(OpCode.PRINT_OBJECT);
        i.operands.add(r1);
        return i;
    }
    public static Instruction printIntInstruction(Register r1) {
        Instruction i = new Instruction(OpCode.PRINT_INT);
        i.operands.add(r1);
        return i;
    }
    public static Instruction printCharInstruction(Register r1) {
        Instruction i = new Instruction(OpCode.PRINT_CHAR);
        i.operands.add(r1);
        return i;
    }
    public static Instruction printBooleanInstruction(Register r1) {
        Instruction i = new Instruction(OpCode.PRINT_BOOLEAN);
        i.operands.add(r1);
        return i;
    }
    public static Instruction printNullInstruction() {
        Instruction i = new Instruction(OpCode.PRINT_NULL);
        return i;
    }


    public static Instruction assertInstruction(Register r1, Offset offset) {
        Instruction i = new Instruction(OpCode.ASSERT);
        i.operands.add(r1);
        i.operands.add(offset);
        return i;
    }


    public static Instruction beq(Register a, Register b, String label) {
        Instruction i = new Instruction(OpCode.BEQ);
        i.operands.add(a);
        i.operands.add(b);//TODO delete ???
        i.operands.add(new Label(label));
        return i;
    }

    public static Instruction register(OpCode code, Register a, Register b, Register c) {
        Instruction i = new Instruction(code);
        i.operands.add(a);
        if(b != null) {
            i.operands.add(b);
        }
        if(c != null) {
            i.operands.add(c);
        }
        return i;
    }

    public static Instruction immediate(OpCode code, Register r, ImmediateNumber number) {
        Instruction i = new Instruction(code);
        i.operands.add(r);
        i.operands.add(number);
        return i;
    }

    public static Instruction putData(Register s0, Register s1) {
        Instruction i = new Instruction(OpCode.PUT_DATA);
        i.operands.add(s0);
        i.operands.add(s1);
        return i;
    }

    public static Instruction getData(Register s0, Register s1) {
        Instruction i = new Instruction(OpCode.GET_DATA);
        i.operands.add(s0);
        i.operands.add(s1);
        return i;
    }
}
