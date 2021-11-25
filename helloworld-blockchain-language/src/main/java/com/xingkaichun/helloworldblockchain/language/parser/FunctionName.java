package com.xingkaichun.helloworldblockchain.language.parser;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class FunctionName extends ASTNode {
    public FunctionName(Token token) {
        super(ASTNodeTypes.FUNCTION_NAME);
        this.lexeme = token;
    }

    public static FunctionName parse(TokenReader reader) {
        return new FunctionName(reader.next());
    }
}
