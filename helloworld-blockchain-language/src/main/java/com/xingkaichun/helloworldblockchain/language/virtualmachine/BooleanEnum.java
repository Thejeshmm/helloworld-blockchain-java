package com.xingkaichun.helloworldblockchain.language.virtualmachine;

/**
 * @author xingkaichun@ceair.com
 */
public enum  BooleanEnum {

    FALSE("false",0),
    TRUE("true",1);

    private String name;
    private int value;

    BooleanEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
