package com.xingkaichun.helloworldblockchain.core.impl;

import com.xingkaichun.helloworldblockchain.language.virtualmachine.BaseData;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.MapData;
import com.xingkaichun.helloworldblockchain.util.ByteUtil;
import com.xingkaichun.helloworldblockchain.util.KvDbUtil;

import java.util.HashMap;
import java.util.Map;

public class BaseDataDefaultImpl extends BaseData {

    private Map<String, String> tempData = new HashMap<>();
    private String dataPath;
    private String keyPrefix;
    private KvDbUtil.KvWriteBatch kvWriteBatch;

    public BaseDataDefaultImpl(KvDbUtil.KvWriteBatch kvWriteBatch, String dataPath) {
        this.dataPath = dataPath;
        this.kvWriteBatch = kvWriteBatch;
    }


    @Override
    public String getBaseData(String key0) {
        String key = getKey(key0);
        String value = tempData.get(key);
        if(value != null){
            return value;
        }
        byte[] bytesValue = KvDbUtil.get(dataPath,ByteUtil.stringToUtf8Bytes(key));
        if(bytesValue == null){
            return null;
        }
        return ByteUtil.utf8BytesToString(bytesValue);
    }

    @Override
    public void addData(Map<String, String> data) {
        if(data == null || data.size()==0){
            return;
        }
        for(Map.Entry<String,String> entry:data.entrySet()){
            String key = getKey(entry.getKey());
            String value = entry.getValue();
            tempData.put(key,value);
            kvWriteBatch.put(ByteUtil.stringToUtf8Bytes(key),ByteUtil.stringToUtf8Bytes(value));
        }
    }

    @Override
    public MapData getOldData() {
        MapData mapData = new MapData();
        for(Map.Entry<String,String> entry:tempData.entrySet()){
            String key = entry.getKey();
            byte[] bytesValue = KvDbUtil.get(dataPath,ByteUtil.stringToUtf8Bytes(key));
            if(bytesValue == null || bytesValue.length == 0){
                mapData.put(key,"");
            }else {
                mapData.put(key,ByteUtil.utf8BytesToString(bytesValue));
            }
        }
        return mapData;
    }

    private String getKey(String key){
        return keyPrefix + key;
    }
    public void resetKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
