package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class FunctionParameters extends ASTNode {

    public FunctionParameters() {
        super(ASTNodeTypes.FUNCTION_PARAMETERS);
    }


    public static FunctionParameters parse(TokenReader reader) throws ParseException {

        FunctionParameters args = new FunctionParameters();
        while(reader.peek().valueIsType()) {
            DeclareVariable declareVariable = DeclareVariable.parse(reader);
            args.addChild(declareVariable);

            if(!reader.nextIs(")")) {
                reader.next(",");
            }
        }
        return args;
    }



    public List<DeclareVariable> getFunctionParameters() {
        List<DeclareVariable> parameters = new ArrayList<>();
        for(ASTNode child:this.children){
            parameters.add((DeclareVariable)child);
        }
        return parameters;
    }

}
