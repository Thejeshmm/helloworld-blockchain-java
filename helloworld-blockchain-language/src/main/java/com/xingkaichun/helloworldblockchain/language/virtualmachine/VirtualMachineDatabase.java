package com.xingkaichun.helloworldblockchain.language.virtualmachine;

public abstract class VirtualMachineDatabase {

    public abstract String get(String key);

    public abstract void putCache(String key, String value);

    public abstract MapData getPersistentData();

    public abstract MapData getCacheData();
}
