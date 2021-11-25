package com.xingkaichun.helloworldblockchain.language.lexer;

/**
 * Classification of token types.
 */
public enum TokenKind {
    /** Error, EOF, EOL...*/
    SPECIAL,
    /** Unary operators. */
    UNARY,
    /** Binary operators. */
    BINARY,
    /** [] () {} */
    BRACKET,
    /** String recognized as a keyword. */
    KEYWORD,
    /** Literal constant. */
    LITERAL,
    VARIABLE
}
