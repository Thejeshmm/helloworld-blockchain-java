package com.xingkaichun.helloworldblockchain.language.util;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;

@FunctionalInterface
public interface ExpressionHOF {

    ASTNode hoc() throws ParseException;

}
