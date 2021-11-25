package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class DeclareVariable extends ASTNode{

    public DeclareVariable() {
        super(ASTNodeTypes.DECLARE_VARIABLE);
    }


    public static DeclareVariable parse(TokenReader reader) throws ParseException {
        DeclareVariable declareVariable = new DeclareVariable();
        DeclareVariableType declareVariableType = DeclareVariableType.parse(reader);
        declareVariable.addChild(declareVariableType);
        DeclareVariableName declareVariableName = DeclareVariableName.parse(reader);
        declareVariable.addChild(declareVariableName);
        return declareVariable;
    }

    public DeclareVariableType getDeclareVariableType() {
        return (DeclareVariableType) this.children.get(0);
    }
    public DeclareVariableName getDeclareVariableName() {
        return (DeclareVariableName) this.children.get(1);
    }

}
