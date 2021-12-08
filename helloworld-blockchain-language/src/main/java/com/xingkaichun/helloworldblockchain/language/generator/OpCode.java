package com.xingkaichun.helloworldblockchain.language.generator;


public class OpCode {

    private String name;

    private OpCode(String name){
        this.name = name;
    }


    public static final OpCode ADD = new OpCode("ADD");
    public static final OpCode SUB = new OpCode("SUB");
    public static final OpCode MUL = new OpCode("MUL");
    public static final OpCode DIV = new OpCode("DIV");
    public static final OpCode MOD = new OpCode("MOD");

    public static final OpCode ADDI = new OpCode("ADDI");
    public static final OpCode SUBI = new OpCode( "SUBI");

    public static final OpCode EQ = new OpCode("EQ");
    public static final OpCode LT = new OpCode("LT");
    public static final OpCode LE = new OpCode("LE");
    public static final OpCode GT = new OpCode("GT");
    public static final OpCode GE = new OpCode("GE");
    public static final OpCode NOT = new OpCode("NOT");
    public static final OpCode NE = new OpCode("NE");
    public static final OpCode OR = new OpCode("OR");
    public static final OpCode AND = new OpCode("AND");

    public static final OpCode LOAD = new OpCode("LOAD");
    public static final OpCode LOAD_OBJECT_FIELD = new OpCode("LOAD_OBJECT_FIELD");
    public static final OpCode LOAD_ARRAY_INDEX = new OpCode("LOAD_ARRAY_INDEX");
    public static final OpCode LOAD_ARRAY_LENGTH = new OpCode("LOAD_ARRAY_LENGTH");
    public static final OpCode LOAD_IMMEDIATE_NUMBER = new OpCode("LOAD_IMMEDIATE_NUMBER");
    public static final OpCode LOAD_THIS = new OpCode("LOAD_THIS");

    public static final OpCode STORE = new OpCode( "STORE");
    public static final OpCode STORE_OBJECT_FIELD = new OpCode("STORE_OBJECT_FIELD");
    public static final OpCode STORE_ARRAY_INDEX = new OpCode("STORE_ARRAY_INDEX");

    public static final OpCode NEW_STRING = new OpCode("NEW_STRING");
    public static final OpCode NEW_OBJECT = new OpCode("NEW_OBJECT");
    public static final OpCode NEW_ARRAY = new OpCode("NEW_ARRAY");

    public static final OpCode BEQ = new OpCode("BEQ");
    public static final OpCode JUMP = new OpCode("JUMP");
    public static final OpCode JR = new OpCode("JR");
    public static final OpCode RETURN = new OpCode("RETURN");

    public static final OpCode PRINT_NULL = new OpCode("PRINT_NULL");
    public static final OpCode PRINT_OBJECT = new OpCode("PRINT_OBJECT");
    public static final OpCode PRINT_INT = new OpCode("PRINT_INT");
    public static final OpCode PRINT_CHAR = new OpCode("PRINT_CHAR");
    public static final OpCode PRINT_BOOLEAN = new OpCode("PRINT_BOOLEAN");
    public static final OpCode ASSERT = new OpCode("ASSERT");
    public static final OpCode PUT_DATA = new OpCode("PUT_DATA");
    public static final OpCode GET_DATA = new OpCode("GET_DATA");


    @Override
    public String toString() {
        return name;
    }
}
