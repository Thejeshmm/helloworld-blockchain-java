package com.xingkaichun.helloworldblockchain.language.generator;


import com.xingkaichun.helloworldblockchain.language.generator.operand.ImmediateNumber;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Label;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Offset;
import com.xingkaichun.helloworldblockchain.language.generator.operand.Register;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.translator.TAInstruction;
import com.xingkaichun.helloworldblockchain.language.translator.TAProgram;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.*;
import com.xingkaichun.helloworldblockchain.language.translator.tainstruction.*;
import com.xingkaichun.helloworldblockchain.language.util.GeneratorException;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Generator {

    public Program generator(TAProgram taProgram){
        Program program = new Program();

        List<TAInstruction> taInstructions = taProgram.getInstructions();

        Map<String, Integer> labelPosition = new Hashtable<>();

        for(TAInstruction ta : taInstructions) {
            program.addComment(ta.toString());
            if(ta instanceof AssignmentTAInstruction){
                generatorAssignment(program, ta);
            }else if(ta instanceof GotoTAInstruction){
                generatorGoto(program, ta);
            }else if(ta instanceof CallTAInstruction){
                generatorCall(program, ta);
            }else if(ta instanceof ParameterTAInstruction){
                generatorParameter(program, ta);
            }else if(ta instanceof SpTAInstruction){
                generatorSp(program, ta);
            }else if(ta instanceof LabelTAInstruction){
                generatorLabel(program, ta, labelPosition);
            }else if(ta instanceof ReturnValueTAInstruction){
                generatorReturnValue(program, ta);
            }else if(ta instanceof ReturnTAInstruction){
                generatorReturn(program, ta);
            }else if(ta instanceof FunctionBeginTAInstruction){
                generatorFunctionBegin(program, ta);
            }else if(ta instanceof IfTAInstruction){
                generatorIf(program, ta);
            }else if(ta instanceof PrintTAInstruction){
                generatorPrint(program, ta);
            }else if(ta instanceof AssertTAInstruction){
                generatorAssert(program, ta);
            }else if(ta instanceof PutDataTAInstruction){
                generatorPutData(program, ta);
            }else if(ta instanceof GetDataTAInstruction){
                generatorGetData(program, ta);
            }else {
                throw new GeneratorException();
            }
        }

        this.relabel(program, labelPosition);

        return program;
    }

    private void generatorLabel(Program program, TAInstruction taInstruction, Map<String, Integer> labelPosition) {
        LabelTAInstruction labelTAInstruction = (LabelTAInstruction)taInstruction;
        labelPosition.put(labelTAInstruction.getLabelName(), program.getInstructions().size());
    }

    private void generatorPrint(Program program, TAInstruction taInstruction) {
        PrintTAInstruction printTAInstruction =(PrintTAInstruction)taInstruction;
        Symbol object = printTAInstruction.getObject();
        if(object instanceof StaticIntSymbol) {
            program.add(Instruction.loadToRegister(Register.S0,object));
            program.add(Instructions.printIntInstruction(Register.S0));
        }else if(object instanceof StaticCharSymbol) {
            program.add(Instruction.loadToRegister(Register.S0,object));
            program.add(Instructions.printCharInstruction(Register.S0));
        }else if(object instanceof StaticBooleanSymbol) {
            program.add(Instruction.loadToRegister(Register.S0,object));
            program.add(Instructions.printBooleanInstruction(Register.S0));
        }else if(object instanceof IntSymbol) {
            program.add(Instruction.loadToRegister(Register.S0, object));
            program.add(Instructions.printIntInstruction(Register.S0));
        }else if(object instanceof CharSymbol) {
            program.add(Instruction.loadToRegister(Register.S0, object));
            program.add(Instructions.printCharInstruction(Register.S0));
        }else if(object instanceof BooleanSymbol) {
            program.add(Instruction.loadToRegister(Register.S0, object));
            program.add(Instructions.printBooleanInstruction(Register.S0));
        }else if(object instanceof NullSymbol) {
            program.add(Instructions.printNullInstruction());
        }else if(object instanceof HeapObjectFieldSymbol) {
            program.add(Instruction.loadToRegister(Register.S0,object));
            program.add(Instructions.printObjectInstruction(Register.S0));
        }else if(object instanceof HeapObjectSymbol) {
            program.add(Instruction.loadToRegister(Register.S0, object));
            program.add(Instructions.printObjectInstruction(Register.S0));
        }else if(object instanceof HeapArraySymbol) {
            if(object instanceof HeapArrayIndexSymbol) {
                if(TokenType.INT.getName().equals(((HeapArrayIndexSymbol) object).getStructTypeName())){
                    program.add(Instruction.loadToRegister(Register.S0, object));
                    program.add(Instructions.printIntInstruction(Register.S0));
                }else if(TokenType.CHAR.getName().equals(((HeapArrayIndexSymbol) object).getStructTypeName())){
                    program.add(Instruction.loadToRegister(Register.S0, object));
                    program.add(Instructions.printCharInstruction(Register.S0));
                }else if(TokenType.BOOLEAN.getName().equals(((HeapArrayIndexSymbol) object).getStructTypeName())){
                    program.add(Instruction.loadToRegister(Register.S0, object));
                    program.add(Instructions.printBooleanInstruction(Register.S0));
                }else {
                    program.add(Instruction.loadToRegister(Register.S0, object));
                    program.add(Instructions.printObjectInstruction(Register.S0));
                }
            }else if(object instanceof HeapArrayLengthSymbol) {
                program.add(Instruction.loadToRegister(Register.S0, object));
                program.add(Instructions.printIntInstruction(Register.S0));
            }else {
                program.add(Instruction.loadToRegister(Register.S0, object));
                program.add(Instructions.printObjectInstruction(Register.S0));
            }
        }else if(object instanceof AddressSymbol) {
            program.add(Instruction.loadToRegister(Register.S0,object));
            program.add(Instructions.printObjectInstruction(Register.S0));
        }else {
            throw new GeneratorException();
        }
    }

    private void generatorAssert(Program program, TAInstruction taInstruction) {
        AssertTAInstruction assertTAInstruction = (AssertTAInstruction)taInstruction;
        Symbol object = assertTAInstruction.getObject();
        if(object instanceof AddressSymbol) {
            AddressSymbol addressSymbol = (AddressSymbol)object;
            program.add(Instructions.assertInstruction(Register.SP,  new Offset(-addressSymbol.getOffset())));
        } else if(object instanceof StaticIntSymbol) {
            StaticIntSymbol staticIntSymbol = (StaticIntSymbol)object;
            program.add(Instructions.assertInstruction(Register.ZERO, new Offset(staticIntSymbol.getOffset())));
        } else if(object instanceof BooleanSymbol) {
            BooleanSymbol booleanSymbol = (BooleanSymbol)object;
            program.add(Instructions.assertInstruction(Register.SP,  new Offset(-booleanSymbol.getOffset())));
        } else {
            throw new GeneratorException();
        }
    }

    private void generatorGetData(Program program, TAInstruction ta) {
        GetDataTAInstruction getDataTAInstruction = (GetDataTAInstruction)ta;
        program.add(Instruction.loadToRegister(Register.S2, getDataTAInstruction.getResult()));
        program.add(Instruction.loadToRegister(Register.S1, getDataTAInstruction.getKey()));
        program.add(Instructions.getData(Register.S2, Register.S1));
        program.add(Instruction.storeToMemory(Register.S2, getDataTAInstruction.getResult()));
    }

    private void generatorPutData(Program program, TAInstruction ta) {
        PutDataTAInstruction putDataTAInstruction = (PutDataTAInstruction)ta;
        program.add(Instruction.loadToRegister(Register.S0, putDataTAInstruction.getKey()));
        program.add(Instruction.loadToRegister(Register.S1, putDataTAInstruction.getValue()));
        program.add(Instructions.putData(Register.S0, Register.S1));
    }

    private void generatorIf(Program program, TAInstruction taInstruction) {
        IfTAInstruction ifTAInstruction = (IfTAInstruction)taInstruction;
        Symbol conditionSymbol = ifTAInstruction.getConditionSymbol();
        String labelName = ifTAInstruction.getLabelName();

        program.add(Instruction.loadToRegister(Register.S0, conditionSymbol));
        program.add(Instructions.beq(Register.S0, Register.ZERO, labelName));
    }

    private void generatorReturnValue(Program program, TAInstruction taInstruction) {
        ReturnValueTAInstruction returnValueTAInstruction = (ReturnValueTAInstruction)taInstruction;
        int offset = returnValueTAInstruction.getOffset();
        Symbol value = returnValueTAInstruction.getValue();
        program.add(Instruction.loadToRegister(Register.S0, value));
        program.add(Instructions.storeInstruction(Register.S0, Register.SP, new Offset(offset)));
    }

    private void generatorReturn(Program program, TAInstruction taInstruction) {
        Instruction i = new Instruction(OpCode.RETURN);
        program.add(i);
    }

    private void relabel(Program program, Map<String, Integer> labelPosition){
        program.getInstructions().forEach(instruction -> {
            if(instruction.getOpCode() == OpCode.JUMP || instruction.getOpCode() == OpCode.JR || instruction.getOpCode() == OpCode.BEQ) {
                int idx = instruction.getOpCode()==OpCode.BEQ?2 : 0;
                Label labelOperand = (Label)instruction.getOperands().get(idx);
                String label = labelOperand.getLabel();
                Integer offset = labelPosition.get(label);
                labelOperand.setOffset(offset);
            }
        });
    }

    private void generatorSp(Program program, TAInstruction taInstruction) {
        SpTAInstruction spTAInstruction = (SpTAInstruction) taInstruction;
        int offset = spTAInstruction.getOffset();
        program.add(Instructions.immediate(OpCode.ADDI, Register.SP, new ImmediateNumber(offset)));
    }

    private void generatorParameter(Program program, TAInstruction taInstruction) {
        ParameterTAInstruction parameterTAInstruction = (ParameterTAInstruction)taInstruction;
        int offset = parameterTAInstruction.getOffset();
        Symbol value = parameterTAInstruction.getValue();
        program.add(Instruction.loadToRegister(Register.S0, value));
        program.add(Instructions.storeInstruction(Register.S0, Register.SP, new Offset(offset)));
    }

    void generatorFunctionBegin(Program program, TAInstruction taInstruction) {
        Instruction i = Instructions.storeInstruction(Register.RA, Register.SP, new Offset(0));
        program.add(i);
    }

    void generatorCall(Program program, TAInstruction taInstruction){
        CallTAInstruction callTAInstruction = (CallTAInstruction)taInstruction;
        LabelSymbol label = callTAInstruction.getFunctionLabelSymbol();
        Instruction i = new Instruction(OpCode.JR);
        i.getOperands().add(new Label(label.getLabel()));
        program.add(i);
    }

    void generatorGoto(Program program, TAInstruction taInstruction) {
        GotoTAInstruction gotoTAInstruction = (GotoTAInstruction)taInstruction;
        Instruction i = new Instruction(OpCode.JUMP);
        i.getOperands().add(new Label(gotoTAInstruction.getLabelName()));
        program.add(i);
    }

    void generatorAssignment(Program program, TAInstruction taInstruction) {
        AssignmentTAInstruction assignmentTAInstruction = (AssignmentTAInstruction)taInstruction;
        Symbol result = assignmentTAInstruction.getResult();
        String op = assignmentTAInstruction.getOp();
        Symbol arg1 = assignmentTAInstruction.getArg1();
        Symbol arg2 = assignmentTAInstruction.getArg2();
        if(arg2 == null) {
            if ("!".equals(op)) {
                program.add(Instruction.loadToRegister(Register.S0, arg1));
                program.add(Instructions.register(OpCode.NOT, Register.S2, Register.S0, null));
                program.add(Instruction.storeToMemory(Register.S2, result));
            }else {
                program.add(Instruction.loadToRegister(Register.S2, arg1));
                program.add(Instruction.storeToMemory(Register.S2, result));
            }
        } else {
            program.add(Instruction.loadToRegister(Register.S0, arg1));
            program.add(Instruction.loadToRegister(Register.S1, arg2));

            switch (op) {
                case "+":
                    program.add(Instructions.register(OpCode.ADD, Register.S2, Register.S0, Register.S1));
                    break;
                case "-":
                    program.add(Instructions.register(OpCode.SUB, Register.S2, Register.S0, Register.S1));
                    break;
                case "*":
                    program.add(Instructions.register(OpCode.MUL, Register.S2, Register.S0,Register.S1));
                    break;
                case "/":
                    program.add(Instructions.register(OpCode.DIV, Register.S2, Register.S0,Register.S1));
                    break;
                case "%":
                    program.add(Instructions.register(OpCode.MOD, Register.S2, Register.S0,Register.S1));
                    break;
                case "==" :
                    program.add(Instructions.register(OpCode.EQ, Register.S2, Register.S0, Register.S1));
                    break;
                case "<" :
                    program.add(Instructions.register(OpCode.LT, Register.S2, Register.S0, Register.S1));
                    break;
                case "<=" :
                    program.add(Instructions.register(OpCode.LE, Register.S2, Register.S0, Register.S1));
                    break;
                case ">" :
                    program.add(Instructions.register(OpCode.GT, Register.S2, Register.S0, Register.S1));
                    break;
                case ">=" :
                    program.add(Instructions.register(OpCode.GE, Register.S2, Register.S0, Register.S1));
                    break;
                case "!=" :
                    program.add(Instructions.register(OpCode.NE, Register.S2, Register.S0, Register.S1));
                    break;
            }
            program.add(Instruction.storeToMemory(Register.S2, result));
        }
    }
}
