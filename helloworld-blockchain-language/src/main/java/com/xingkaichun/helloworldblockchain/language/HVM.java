package com.xingkaichun.helloworldblockchain.language;

import com.xingkaichun.helloworldblockchain.language.generator.Generator;
import com.xingkaichun.helloworldblockchain.language.generator.Instruction;
import com.xingkaichun.helloworldblockchain.language.generator.Program;
import com.xingkaichun.helloworldblockchain.language.lexer.Lexer;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.Parser;
import com.xingkaichun.helloworldblockchain.language.translator.TAProgram;
import com.xingkaichun.helloworldblockchain.language.translator.Translator;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.BaseData;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachine;

import java.util.List;

public class HVM {
    public VirtualMachine virtualMachine;
    public String execute(BaseData baseData,String fileName, String[] args) {
        TokenReader reader = Lexer.fromFile(fileName);

        ASTNode astNode = Parser.parse(reader);
        System.out.println(astNode);
        Translator translator = new Translator();
        TAProgram taProgram = translator.translate(astNode);
        Generator generator = new Generator();
        Program program = generator.generator(taProgram);
        List<String> statics = program.getStaticArea(taProgram);
        Integer entry = program.getEntry();
        List<Instruction> instructions = program.getInstructions();

        virtualMachine = new VirtualMachine(statics,instructions,entry);
        virtualMachine.baseData = baseData;
        if(args != null && args.length != 0){
            int address = virtualMachine.newStrings(args);
            virtualMachine.getMemory()[virtualMachine.getMemory().length-3] = String.valueOf(address);
        }
        virtualMachine.setDebug(false);
        virtualMachine.run();
        return virtualMachine.getVmResult();
    }
}
