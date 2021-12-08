package com.xingkaichun.helloworldblockchain.core.tool;

import com.xingkaichun.helloworldblockchain.util.ByteUtil;
import com.xingkaichun.helloworldblockchain.util.FileUtil;

public class ContractGeneratorTool {


    public static void main(String[] args) {

        String contractPath = "";
        System.out.println(ByteUtil.bytesToHexString(ByteUtil.stringToUtf8Bytes(FileUtil.read(contractPath))));
    }
}
