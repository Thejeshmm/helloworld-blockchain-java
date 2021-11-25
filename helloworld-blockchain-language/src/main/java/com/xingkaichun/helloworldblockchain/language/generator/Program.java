package com.xingkaichun.helloworldblockchain.language.generator;

import com.xingkaichun.helloworldblockchain.language.translator.TAProgram;
import com.xingkaichun.helloworldblockchain.language.translator.symbol.*;
import com.xingkaichun.helloworldblockchain.language.util.GeneratorException;
import com.xingkaichun.helloworldblockchain.language.util.StringUtil;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.BooleanEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

    private Integer entry = null;
    private List<Instruction> instructions;
    private Map<Integer, String> comments;

    public Program() {
        instructions = new ArrayList<>();
        comments = new HashMap<>();
    }


    public void add(Instruction instruction) {
        this.instructions.add(instruction);
    }
    public void add(List<Instruction> instructions) {
        this.instructions.addAll(instructions);
    }

    public void addComment(String comment) {
        this.comments.put(this.instructions.size(), comment);
    }

    public List<String> getStaticArea(TAProgram taProgram) {
        List<String> list = new ArrayList<>();
        for(Symbol symbol : taProgram.getStaticSymbolTable().getSymbols()) {
            String value = symbol.getLexeme().getValue();
            if(symbol instanceof StaticIntSymbol){
                list.add(value);
            }else if(symbol instanceof StaticCharSymbol){
                list.add(value);
            }else if(symbol instanceof StaticBooleanSymbol){
                if(BooleanEnum.TRUE.getName().equals(value)){
                    list.add(String.valueOf(BooleanEnum.TRUE.getValue()));
                }else if(BooleanEnum.FALSE.getName().equals(value)){
                    list.add(String.valueOf(BooleanEnum.FALSE.getValue()));
                }else {
                    throw new GeneratorException();
                }
            }else if(symbol instanceof StaticStringSymbol){
                list.add(value);
            }
        }
        return list;
    }




    @Override
    public String toString() {
        ArrayList<String> prts = new ArrayList<>();
        for(int i = 0; i < instructions.size(); i++) {
            if(this.comments.containsKey(i)) {
                prts.add("#" + this.comments.get(i));
            }
            String str = instructions.get(i).toString();
            if(this.entry != null && i == this.entry) {
                str = "MAIN:" + str;
            }
            prts.add(str);
        }
        return StringUtil.join(prts, "\n");
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public Integer getEntry() {
        return this.entry;

    }

    public List<Instruction> getInstructions() {
        return instructions;
    }


}
