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
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachineDatabase;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachineThisFactory;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachine;

import java.util.List;

public class HVM {

    public VirtualMachine virtualMachine;
    public VirtualMachineDatabase virtualMachineDatabase;
    public VirtualMachineThisFactory virtualMachineThisFactory;

    public String execute(String filePath, String[] args) {

        TokenReader reader = Lexer.fromFile(filePath);
        ASTNode astNode = Parser.parse(reader);
        Translator translator = new Translator();
        TAProgram taProgram = translator.translate(astNode);
        Generator generator = new Generator();
        Program program = generator.generator(taProgram);
        List<String> statics = program.getStaticArea(taProgram);
        Integer entry = program.getEntry();
        List<Instruction> instructions = program.getInstructions();

        this.virtualMachine = new VirtualMachine(statics,instructions,entry);

        if(this.virtualMachineDatabase != null){
            this.virtualMachine.virtualMachineDatabase = this.virtualMachineDatabase;
        }

        if(args != null && args.length != 0){
            int argsAddress = this.virtualMachine.newStrings(args);
            this.virtualMachine.setArgsAddress(argsAddress);
        }

        if(virtualMachineThisFactory != null){
            this.virtualMachineThisFactory.setVirtualMachine(this.virtualMachine);
            this.virtualMachine.setThisAddress(this.virtualMachineThisFactory.getThisAddress());
        }

        this.virtualMachine.setDebug(false);
        this.virtualMachine.run();
        return this.virtualMachine.getVmResult();
    }
}
