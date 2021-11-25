package com.xingkaichun.helloworldblockchain.language.lexer;


import static com.xingkaichun.helloworldblockchain.language.lexer.TokenKind.*;

public enum TokenType {
    EOF                  (SPECIAL,  null),
    EOL                  (SPECIAL,  null),

    LPAREN         (BRACKET, "("),
    RPAREN         (BRACKET, ")"),
    LBRACE         (BRACKET,  "{"),
    RBRACE         (BRACKET, "}"),
    LBRACKET       (BRACKET, "["),
    RBRACKET       (BRACKET, "]"),
    PERIOD         (BRACKET, "."),
    SEMICOLON      (BINARY,  ";"),
    COLON          (BINARY,  ":"),
    COMMA          (BINARY,  ","),

    NOT            (UNARY,   "!"),
    NE             (BINARY,  "!="),
    MOD            (BINARY,  "%"),
    ASSIGN_MOD     (BINARY,  "%="),
    BIT_AND        (BINARY,  "&"),
    AND            (BINARY,  "&&"),
    ASSIGN_BIT_AND (BINARY,  "&="),
    MUL            (BINARY,  "*"),
    ASSIGN_MUL     (BINARY,  "*="),
    POS            (UNARY,   "+"),
    ADD            (BINARY,  "+"),
    INCPREFIX      (UNARY,   "++"),
    ASSIGN_ADD     (BINARY,  "+="),
    NEG            (UNARY,   "-"),
    SUB            (BINARY,  "-"),
    DECPREFIX      (UNARY,   "--"),
    ASSIGN_SUB     (BINARY,  "-="),
    DIV            (BINARY,  "/"),
    ASSIGN_DIV     (BINARY,  "/="),
    LT             (BINARY,  "<"),
    SHL            (BINARY,  "<<"),
    LE             (BINARY,  "<="),
    ASSIGN         (BINARY,  "="),
    EQ             (BINARY,  "=="),
    ARROW          (BINARY,  "=>"),
    GT             (BINARY,  ">"),
    GE             (BINARY,  ">="),
    SAR            (BINARY,  ">>"),
    TERNARY        (BINARY,  "?"),
    BIT_XOR        (BINARY,  "^"),
    ASSIGN_BIT_XOR (BINARY,  "^="),
    BIT_OR         (BINARY,  "|"),
    ASSIGN_BIT_OR  (BINARY,  "|="),
    OR             (BINARY,  "||"),
    BIT_NOT        (UNARY,   "~"),

    DECIMAL        (LITERAL,  null),
    HEXADECIMAL    (LITERAL,  null),
    OCTAL          (LITERAL,  null),
    BINARY_NUMBER  (LITERAL,  null),
    FLOATING       (LITERAL,  null),
    STRING         (LITERAL,  "string"),

    BOOLEAN        (LITERAL,   "boolean"),
    //  DOUBLE         (FUTURE,   "double"),
    //  FLOAT          (FUTURE,   "float"),
    INT            (LITERAL,   "int"),
    //  LONG           (FUTURE,   "long"),
    //  BYTE           (FUTURE,   "byte"),
    CHAR           (LITERAL,   "char"),
    NEW            (KEYWORD,    "new"),
    STRUCT          (KEYWORD,   "struct"),
    CONTRACT          (KEYWORD,   "contract"),
    FUNCTION       (KEYWORD,  "function"),
    RETURN         (KEYWORD,  "return"),
    IF             (KEYWORD,   "if"),
    ELSE           (KEYWORD,  "else"),
    FOR            (KEYWORD,  "for"),
    BREAK          (KEYWORD,  "break"),
    CONTINUE       (KEYWORD,  "continue"),
    NULL           (KEYWORD,  "null"),

    FALSE          (LITERAL,  "false"),
    TRUE           (LITERAL,  "true"),


    VARIABLE           (TokenKind.VARIABLE,  null);


    private TokenKind kind;
    private String name;
    TokenType(final TokenKind kind, final String name) {
        this.kind         = kind;
        this.name         = name;
    }

    public TokenKind getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public static boolean isKeyword(String keyword) {
        for (TokenType tokenType:values()){
            if(TokenKind.KEYWORD == tokenType.kind){
                if(keyword.equals(tokenType.getName())){
                    return true;
                }
            }
        }
        return false;
    }
    public static TokenType getKeyword(String keyword) {
        for (TokenType tokenType:values()){
            if(TokenKind.KEYWORD == tokenType.kind){
                if(keyword.equals(tokenType.getName())){
                    return tokenType;
                }
            }
        }
        return null;
    }

    public static boolean isBoolean(String word) {
        return TokenType.TRUE.getName().equals(word) ||
               TokenType.FALSE.getName().equals(word);
    }

    public static boolean isInt(String word) {
        return TokenType.INT.getName().equals(word);
    }

    public static boolean isNew(String word) {
        return TokenType.NEW.getName().equals(word);
    }

    public static boolean isVariable(String word) {
        return true;
    }
}
