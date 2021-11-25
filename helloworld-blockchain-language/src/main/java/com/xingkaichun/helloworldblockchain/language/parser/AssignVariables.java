package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.List;

public class AssignVariables extends ASTNode{

    public AssignVariables() {
        super(ASTNodeTypes.ASSIGN_VARIABLES);
    }


    public static AssignVariables parse(TokenReader reader) throws ParseException {
        AssignVariables assignVariables = new AssignVariables();

        ASTNode assignVariable = AssignVariable.parse(reader);
        assignVariables.addChild(assignVariable);

        while (reader.nextIs(",")){
            reader.next(",");
            assignVariable = AssignVariable.parse(reader);
            assignVariables.addChild(assignVariable);
        }

        return assignVariables;
    }

    public List<ASTNode> getAssignVariable(){
        return this.children;
    }
}
