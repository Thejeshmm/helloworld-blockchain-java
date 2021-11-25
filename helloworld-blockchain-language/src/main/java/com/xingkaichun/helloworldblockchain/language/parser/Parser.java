package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class Parser {

    public static ASTNode parse(TokenReader reader) {
        return Contract.parse(reader);
    }
}
