package com.xingkaichun.helloworldblockchain.language.generator.operand;


public class Register extends Operand {

    private int address;
    private String name;

    public Register(String name, int address) {
        this.name = name;
        this.address = address;
    }


    public static final Register ZERO = new Register("ZERO", 0);
    public static final Register PC = new Register("PC", 1);
    public static final Register SP = new Register("SP", 2);
    public static final Register RA = new Register("RA", 3);
    public static final Register S0 = new Register("S0", 4);
    public static final Register S1 = new Register("S1", 5);
    public static final Register S2 = new Register("S2", 6);
    public static final Register S3 = new Register("S3", 7);
    public static final Register S4 = new Register("S4", 8);
    public static final Register S5 = new Register("S5", 9);
    public static final Register S6 = new Register("S6", 10);
    public static final Register S7 = new Register("S7", 11);




    @Override
    public String toString() {
        return this.name;
    }

    public int getAddress() {
        return this.address;
    }

}
