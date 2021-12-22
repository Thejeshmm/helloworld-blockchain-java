package com.xingkaichun.helloworldblockchain.core.language;

import com.xingkaichun.helloworldblockchain.language.virtualmachine.MapData;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachineDatabase;
import com.xingkaichun.helloworldblockchain.util.ByteUtil;
import com.xingkaichun.helloworldblockchain.util.KvDbUtil;

import java.util.HashMap;
import java.util.Map;

public class VirtualMachineDatabaseDefaultImpl extends VirtualMachineDatabase {

    private Map<String, String> cacheData;
    private String dataPath;
    private String keyPrefix;

    public VirtualMachineDatabaseDefaultImpl(String dataPath) {
        this.cacheData = new HashMap<>();
        this.dataPath = dataPath;
        this.keyPrefix = "";
    }


    @Override
    public String get(String key0) {
        String key = getKey(key0);
        String value = cacheData.get(key);
        if(value != null){
            return value;
        }
        byte[] bytesValue = KvDbUtil.get(dataPath, ByteUtil.stringToUtf8Bytes(key));
        if(bytesValue == null){
            return null;
        }
        return ByteUtil.utf8BytesToString(bytesValue);
    }

    @Override
    public void putCache(String key, String value) {
        cacheData.put(getKey(key),value);
    }

    public MapData getCurrentPersistentData() {
        MapData mapData = new MapData();
        for(Map.Entry<String, String> entry:cacheData.entrySet()){
            String key = entry.getKey();
            if(key.contains(this.keyPrefix)){
                byte[] bytesValue = KvDbUtil.get(dataPath, ByteUtil.stringToUtf8Bytes(key));
                if(bytesValue == null || bytesValue.length == 0){
                    mapData.put(key,"");
                }else {
                    mapData.put(key, ByteUtil.utf8BytesToString(bytesValue));
                }
            }
        }
        return mapData;
    }

    public MapData getPersistentData() {
        MapData mapData = new MapData();
        for(Map.Entry<String, String> entry:cacheData.entrySet()){
            String key = entry.getKey();
            byte[] bytesValue = KvDbUtil.get(dataPath, ByteUtil.stringToUtf8Bytes(key));
            if(bytesValue == null || bytesValue.length == 0){
                mapData.put(key,"");
            }else {
                mapData.put(key, ByteUtil.utf8BytesToString(bytesValue));
            }
        }
        return mapData;
    }

    @Override
    public MapData getCacheData() {
        MapData mapData = new MapData();
        for(Map.Entry<String, String> entry:cacheData.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            mapData.put(key,value);
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
