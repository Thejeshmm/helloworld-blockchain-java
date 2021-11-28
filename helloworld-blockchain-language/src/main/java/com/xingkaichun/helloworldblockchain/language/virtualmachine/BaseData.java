package com.xingkaichun.helloworldblockchain.language.virtualmachine;

import java.util.Map;

public abstract class BaseData {

    public abstract String getBaseData(String key);

    public abstract void addData(Map<String, String> data);

    public abstract MapData getOldData();

    public abstract void resetKeyPrefix(String keyPrefix);
}
