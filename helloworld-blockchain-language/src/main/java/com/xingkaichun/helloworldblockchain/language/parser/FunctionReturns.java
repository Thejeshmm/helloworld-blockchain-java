package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class FunctionReturns extends ASTNode {

    public FunctionReturns() {
        super(ASTNodeTypes.FUNCTION_RETURNS);
    }


    public static FunctionReturns parse(TokenReader reader) throws ParseException {

        FunctionReturns args = new FunctionReturns();
        while(reader.peek().valueIsType()) {
            FunctionReturn variable = FunctionReturn.parse(reader);
            args.addChild(variable);

            if(!reader.nextIs("{")) {
                reader.next(",");
            }
        }
        return args;
    }

    public List<FunctionReturn> getFunctionReturns(){
        List<FunctionReturn> functionReturns = new ArrayList<>();
        for(ASTNode astNode:this.children){
            functionReturns.add((FunctionReturn) astNode);
        }
        return functionReturns;
    }

}
