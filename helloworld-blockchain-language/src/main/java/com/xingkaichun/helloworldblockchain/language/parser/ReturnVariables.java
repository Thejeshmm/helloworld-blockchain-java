package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.List;

public class ReturnVariables extends ASTNode{

    public ReturnVariables() {
        super(ASTNodeTypes.RETURN_VARIABLES);
    }


    public static ReturnVariables parse(TokenReader reader) throws ParseException {
        ReturnVariables returnVariables = new ReturnVariables();

        ASTNode returnVariable = ReturnVariable.parse(reader);
        if(returnVariable != null){
            returnVariables.addChild(returnVariable);
        }

        while (reader.nextIs(",")){
            reader.next(",");
            returnVariable = ReturnVariable.parse(reader);
            returnVariables.addChild(returnVariable);
        }

        return returnVariables;
    }

    public List<ASTNode> getReturnVariables(){
        return this.children;
    }

}
