package com.xingkaichun.helloworldblockchain.core.tool;

import com.xingkaichun.helloworldblockchain.util.ByteUtil;
import com.xingkaichun.helloworldblockchain.util.FileUtil;

public class ContractGeneratorTool {


    public static void main(String[] args) {

        String contractPath = "E:\\workspace\\JavaProjects\\helloworld-language\\src\\test\\resources\\nft.helloworld";
        System.out.println(ByteUtil.bytesToHexString(ByteUtil.stringToUtf8Bytes(FileUtil.read(contractPath))));
    }
}
