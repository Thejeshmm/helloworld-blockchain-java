package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class DeclareVariables extends ASTNode{

    public DeclareVariables() {
        super(ASTNodeTypes.DECLARE_VARIABLES);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        DeclareVariables declareVariables = new DeclareVariables();

        ASTNode declareVariable = DeclareVariable.parse(reader);
        declareVariables.addChild(declareVariable);

        while (reader.nextIs(",")){
            reader.next(",");
            declareVariable = DeclareVariable.parse(reader);
            declareVariables.addChild(declareVariable);
        }

        return declareVariables;
    }

    public List<DeclareVariable> getDeclareVariables(){
        List<DeclareVariable> declareVariables = new ArrayList<>();
        if(this.children != null){
            for(ASTNode astNode:this.children){
                declareVariables.add((DeclareVariable) astNode);
            }
        }
        return declareVariables;
    }

}
