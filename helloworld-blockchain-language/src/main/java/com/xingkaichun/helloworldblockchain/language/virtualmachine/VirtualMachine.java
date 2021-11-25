package com.xingkaichun.helloworldblockchain.language.virtualmachine;


import com.xingkaichun.helloworldblockchain.language.generator.Instruction;
import com.xingkaichun.helloworldblockchain.language.generator.OpCode;
import com.xingkaichun.helloworldblockchain.language.generator.operand.ImmediateNumber;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Offset;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Register;
import com.xingkaichun.helloworldblockchain.language.util.StringUtil;
import com.xingkaichun.helloworldblockchain.language.util.VirtualMachineException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VirtualMachine {

    private String[] registers = new String[31];
    private int HEAP_MEMORY_SIZE = 1024*1024*128;
    private String[] memory = new String[HEAP_MEMORY_SIZE];
    private Instruction[] opcodeMemory;
    private boolean debug = false;
    private HeapMemory heapMemory;
    private static final int NON_NULL_OBJECT_FLAG = 13579;

    private static final int OBJECT_HEAD_SIZE = 1;
    private static final int ARRAY_HEAD_SIZE = 1;
    public VirtualMachine(List<String> staticArea, List<Instruction> opcodes, Integer entry) {
        for(int i=0; i < memory.length; i++) {
            memory[i] = "0";
        }
        for(int i=0; i < registers.length; i++) {
            registers[i] = "0";
        }

        for(int i=0; i < staticArea.size(); i++) {
            memory[i] = staticArea.get(i);
        }

        opcodeMemory = new Instruction[opcodes.size()];
        for(int i=0; i < opcodes.size(); i++) {
            opcodeMemory[i] = opcodes.get(i);
        }

        int heapMemoryStart = 1024*1024*32;
        int heapMemoryEnd = 1024*1024*64;
        heapMemory = new HeapMemory(this,memory,heapMemoryStart,heapMemoryEnd);

        setRegisterValue(Register.PC,opcodeMemory.length-3);
        setRegisterValue(Register.SP,memory.length-1);
    }

    private Instruction fetch() {
        int pc = getRegisterValue(Register.PC);
        return opcodeMemory[pc];
    }

    private void execute(Instruction instruction) {
        OpCode code = instruction.getOpCode();
        if(code == OpCode.ADD){
            Register r0 = (Register)instruction.getOperand(0);
            Register r1 = (Register)instruction.getOperand(1);
            Register r2 = (Register)instruction.getOperand(2);
            setRegisterValue(r0,getRegisterValue(r1) + getRegisterValue(r2));
        }else if(code == OpCode.SUB){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            setRegisterValue(r0,getRegisterValue(r1) - getRegisterValue(r2));
        }else if(code == OpCode.MUL){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            setRegisterValue(r0,getRegisterValue(r1) * getRegisterValue(r2));
        }else if(code == OpCode.DIV){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            setRegisterValue(r0,getRegisterValue(r1) / getRegisterValue(r2));
        }else if(code == OpCode.MOD){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            setRegisterValue(r0,getRegisterValue(r1) % getRegisterValue(r2));
        }else if(code == OpCode.ADDI){
            Register r0 = (Register) instruction.getOperand(0);
            ImmediateNumber r1 = (ImmediateNumber) instruction.getOperand(1);
            setRegisterValue(r0,getRegisterValue(r0) + r1.getValue());
        }else if(code == OpCode.SUBI){
            Register r0 = (Register) instruction.getOperand(0);
            ImmediateNumber r1 = (ImmediateNumber) instruction.getOperand(1);
            setRegisterValue(r0,getRegisterValue(r0) - r1.getValue());
        }else if(code == OpCode.EQ){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(StringUtil.equals(getRegisterStringValue(r1),getRegisterStringValue(r2))){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.LT){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(getRegisterValue(r1) < getRegisterValue(r2)){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.LE){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(getRegisterValue(r1) <= getRegisterValue(r2)){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.GT){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(getRegisterValue(r1) > getRegisterValue(r2)){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.GE){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(getRegisterValue(r1) >= getRegisterValue(r2)){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.NOT){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            if(getRegisterValue(r1) == BooleanEnum.TRUE.getValue()){
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }
        }else if(code == OpCode.NE){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            if(getRegisterValue(r1) != getRegisterValue(r2)){
                setRegisterValue(r0,BooleanEnum.TRUE.getValue());
            }else {
                setRegisterValue(r0,BooleanEnum.FALSE.getValue());
            }
        }else if(code == OpCode.STORE){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Offset offset = (Offset) instruction.getOperand(2);
            int r1Value = getRegisterValue(r1);
            setMemoryValue((r1Value + offset.getOffset()), getRegisterStringValue(r0));
        }else if(code == OpCode.LOAD){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Offset offset = (Offset) instruction.getOperand(2);
            int r1Value = getRegisterValue(r1);
            setRegisterValue(r0, getMemoryStringValue(r1Value + offset.getOffset()));
        }else if(code == OpCode.LOAD_ARRAY_INDEX){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Register r2 = (Register) instruction.getOperand(2);
            int r1Value = getRegisterValue(r1);
            int r2Value = getRegisterValue(r2);
            if(r2Value <0 || heapMemory.getHeapMemoryValue(r1Value) <= r2Value){
                throw new VirtualMachineException();
            }
            setRegisterValue(r0, heapMemory.getHeapMemoryStringValue(ARRAY_HEAD_SIZE+r1Value + r2Value));
        }else if(code == OpCode.LOAD_ARRAY_LENGTH){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            int r1Value = getRegisterValue(r1);
            setRegisterValue(r0, heapMemory.getHeapMemoryValue(r1Value));
        }else if(code == OpCode.LOAD_IMMEDIATE_NUMBER){
            Register r0 = (Register) instruction.getOperand(0);
            ImmediateNumber r1 = (ImmediateNumber) instruction.getOperand(1);
            setRegisterValue(r0, r1.getValue());
        }else if(code == OpCode.NEW_STRING){
            Register targetStringAddressRegister = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            Offset offset = (Offset) instruction.getOperand(2);
            String stringValue = getMemoryStringValue(getRegisterValue(r1)+offset.getOffset());

            int stringAddress = newObject(1);
            setRegisterValue(targetStringAddressRegister, stringAddress);

            int charsAddress = newArray(stringValue.length());
            storeObjectField(stringAddress,0,charsAddress);

            for(int i=0;i<stringValue.length();i++){
                storeArrayIndex(charsAddress,i,String.valueOf(stringValue.charAt(i)));
            }
        }else if(code == OpCode.NEW_OBJECT){
            Register targetObjectAddressRegister = (Register) instruction.getOperand(0);
            ImmediateNumber objectFieldCount = (ImmediateNumber) instruction.getOperand(1);
            int objectMemoryAddress = newObject(objectFieldCount.getValue());
            setRegisterValue(targetObjectAddressRegister, objectMemoryAddress);
        }else if(code == OpCode.NEW_ARRAY){
            Register targetArrayAddressRegister = (Register) instruction.getOperand(0);
            Register arrayLengthRegister = (Register) instruction.getOperand(1);
            int arrayLength = getRegisterValue(arrayLengthRegister);
            int arrayMemoryAddress = newArray(arrayLength);
            setRegisterValue(targetArrayAddressRegister, arrayMemoryAddress);
        }else if(code == OpCode.STORE_OBJECT_FIELD){
            Register objectValueRegister = (Register) instruction.getOperand(0);
            Register objectAddressRegister = (Register) instruction.getOperand(1);
            ImmediateNumber objectFieldIndexImmediateNumber = (ImmediateNumber) instruction.getOperand(2);
            int objectBaseAddress = getRegisterValue(objectAddressRegister);
            int objectFieldIndex = objectFieldIndexImmediateNumber.getValue();
            int objectValue = getRegisterValue(objectValueRegister);
            storeObjectField(objectBaseAddress, objectFieldIndex, objectValue);
        }else if(code == OpCode.STORE_ARRAY_INDEX){
            Register valueRegister = (Register) instruction.getOperand(0);
            Register arrayBaseAddressRegister = (Register) instruction.getOperand(1);
            Register indexRegister = (Register) instruction.getOperand(2);
            int arrayBaseAddress = getRegisterValue(arrayBaseAddressRegister);
            int index = getRegisterValue(indexRegister);
            String value = getRegisterStringValue(valueRegister);
            storeArrayIndex(arrayBaseAddress,index,value);
        }else if(code == OpCode.LOAD_OBJECT_FIELD){
            Register r0 = (Register) instruction.getOperand(0);
            Register r1 = (Register) instruction.getOperand(1);
            ImmediateNumber immediateNumber = (ImmediateNumber) instruction.getOperand(2);
            if(heapMemory.getHeapMemoryValue(OBJECT_HEAD_SIZE+getRegisterValue(r1)+immediateNumber.getValue()*2) != NON_NULL_OBJECT_FLAG){
                throw new VirtualMachineException();
            }
            setRegisterValue(r0, heapMemory.getHeapMemoryValue(OBJECT_HEAD_SIZE+getRegisterValue(r1)+immediateNumber.getValue()*2+1));
        }else if(code == OpCode.BEQ){
            Register r0 = (Register)instruction.getOperand(0);
            //Register r1 = (Register)instruction.getOperand(1);
            Offset offset = (Offset)instruction.getOperand(2);
            if(getRegisterValue(r0) != BooleanEnum.TRUE.getValue()) {
                setRegisterValue(Register.PC, offset.getOffset() - 1);
            }
        }else if(code == OpCode.JUMP){
            Offset r0 = (Offset) instruction.getOperand(0);
            setRegisterValue(Register.PC, r0.getOffset() - 1);
        }else if(code == OpCode.JR){
            Offset r0 = (Offset) instruction.getOperand(0);
            setRegisterValue(Register.RA, getRegisterValue(Register.PC));
            setRegisterValue(Register.PC, r0.getOffset() - 1);
        }else if(code == OpCode.RETURN){
            int spValue = getRegisterValue(Register.SP);
            setRegisterValue(Register.PC, getMemoryValue(spValue));
        }else if(code == OpCode.PRINT_OBJECT){
            Register r0 = (Register) instruction.getOperand(0);
            int r0Value = getRegisterValue(r0);
            if(r0Value == 0){
                System.out.print("null");
            }else {
                System.out.print(r0Value);
            }
        }else if(code == OpCode.PRINT_INT){
            Register r0 = (Register) instruction.getOperand(0);
            System.out.print(getRegisterValue(r0));
        }else if(code == OpCode.PRINT_NULL){
            System.out.print("null");
        }else if(code == OpCode.PRINT_CHAR){
            Register r0 = (Register) instruction.getOperand(0);
            String r0Value = getRegisterStringValue(r0);
            System.out.print(r0Value);
        }else if(code == OpCode.PRINT_BOOLEAN){
            Register r0 = (Register) instruction.getOperand(0);
            int r0Value = getRegisterValue(r0);
            if(r0Value == BooleanEnum.TRUE.getValue()){
                System.out.print("true");
            }else {
                System.out.print("false");
            }
        }else if(code == OpCode.ASSERT){
            Register r0 = (Register) instruction.getOperand(0);
            Offset offset = (Offset) instruction.getOperand(1);
            int r0Value = getRegisterValue(r0);
            if(getMemoryValue(r0Value + offset.getOffset()) != BooleanEnum.TRUE.getValue()){
                throw new VirtualMachineException("assert failed");
            }
        }else{
            throw new VirtualMachineException();
        }
    }

    private void storeArrayIndex(int arrayBaseAddress,int index,String value) {
        if(index < 0 || heapMemory.getHeapMemoryValue(arrayBaseAddress) <= index){
            throw new VirtualMachineException();
        }
        heapMemory.setHeapMemoryValue(ARRAY_HEAD_SIZE+arrayBaseAddress+index, value);
    }

    private void storeObjectField(int objectBaseAddress, int objectFieldIndex, int objectValueAddress) {
        int objectFieldBaseAddress = OBJECT_HEAD_SIZE + objectBaseAddress + objectFieldIndex * 2;
        heapMemory.setHeapMemoryValue(objectFieldBaseAddress+0, NON_NULL_OBJECT_FLAG);
        heapMemory.setHeapMemoryValue(objectFieldBaseAddress+1, objectValueAddress);
    }

    private int newArray(int arrayLength) {
        int arrayMemorySize = ARRAY_HEAD_SIZE + arrayLength;
        int arrayMemoryAddress = heapMemory.memoryAllocation(arrayMemorySize);
        heapMemory.setHeapMemoryValue(arrayMemoryAddress, arrayLength);
        return arrayMemoryAddress;
    }

    private int newObject(int objectFieldCount) {
        int objectMemorySize = OBJECT_HEAD_SIZE + objectFieldCount * 2;
        int objectMemoryAddress = heapMemory.memoryAllocation(objectMemorySize);
        heapMemory.setHeapMemoryValue(objectMemoryAddress,objectMemorySize);
        return objectMemoryAddress;

    }

    public void run() throws VirtualMachineException {
        while(true){
            Instruction instruction = fetch();
            if(debug){
                System.out.println("execute" + "|" + String.format("%08d",getRegisterValue(Register.PC)) + "|" + instruction);
            }
            execute(instruction);
            setRegisterValue(Register.PC,getRegisterValue(Register.PC)+1);
            if(getRegisterValue(Register.PC) < 0 || getRegisterValue(Register.PC) >= opcodeMemory.length){
                break;
            }
        }
    }




    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Set<Integer> getHeapAddress() {
        return new HashSet<>();
    }


    private void setRegisterValue(Register register,String value){
        registers[register.getAddress()] = value;
    }
    private void setRegisterValue(Register register,int value){
        registers[register.getAddress()] = String.valueOf(value);
    }
    private int getRegisterValue(Register register){
        return Integer.valueOf(registers[register.getAddress()]);
    }
    private String getRegisterStringValue(Register register){
        return registers[register.getAddress()];
    }
    private void setMemoryValue(int address,String value){
        memory[address] = value;
    }
    private int getMemoryValue(int address){
        return Integer.valueOf(memory[address]);
    }
    private String getMemoryStringValue(int address){
        return memory[address];
    }
}
