package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class Contract extends ASTNode{

    private String contractName;

    public Contract() {
        super(ASTNodeTypes.CONTRACT);
    }


    public static Contract parse(TokenReader reader) throws ParseException {
        Contract contract = new Contract();

        reader.next(TokenType.CONTRACT);
        contract.contractName = reader.next().getValue();

        reader.next(TokenType.LBRACE);
        while (reader.hasNext() && reader.nextIsNot("}")){
            if(reader.nextIs(TokenType.FUNCTION)) {
                FunctionDeclareStatement functionDeclareStatement = FunctionDeclareStatement.parse(reader);
                contract.addChild(functionDeclareStatement);
                continue;
            } else if(reader.nextIs(TokenType.STRUCT)) {
                Struct struct = Struct.parse(reader);
                contract.addChild(struct);
                continue;
            } else if(reader.nextIsType()) {
                //类型包含自定义类型
                DeclareStatement declareStatement = DeclareStatement.parse(reader);
                contract.addChild(declareStatement);
                continue;
            }
            throw new ParseException();
        }
        reader.next("}");
        return contract;
    }


    public String getContractName(){
        return this.contractName;
    }
}
