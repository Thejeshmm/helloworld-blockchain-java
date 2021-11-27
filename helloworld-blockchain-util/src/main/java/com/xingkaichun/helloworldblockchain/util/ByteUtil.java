package com.xingkaichun.helloworldblockchain.util;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 邢开春 409060350@qq.com
 */
public class ByteUtil {

    public static final int BYTE8_BYTE_COUNT = 8;

    /**
     * byte数组转十六进制字符串(十六进制字符串小写，仅包含字符0123456789abcdef)。
     * 不允许省略十六进制字符串前面的零，因此十六进制字符串的长度是字节数量的2倍。
     */
    public static String bytesToHexString(byte[] bytes) {
        return Hex.toHexString(bytes);
    }
    /**
     * 16进制字符串转byte数组(十六进制字符串小写，仅包含字符0123456789abcdef)
     * @param hexString 16进制字符串，该属性值的长度一定是2的整数倍
     */
    public static byte[] hexStringToBytes(String hexString) {
        return Hex.decode(hexString);
    }



    /**
     * 无符号整数转换为(大端模式)8个字节的字节数组。
     */
    public static byte[] uint64ToBytes(long number) {
        return Longs.toByteArray(number);
    }
    /**
     * (大端模式)8个字节的字节数组转换为无符号整数。
     */
    public static long bytesToUint64(byte[] bytes) {
        return Longs.fromByteArray(bytes);
    }



    /**
     * 字符串转换为UTF8字节数组
     */
    public static byte[] stringToUtf8Bytes(String stringValue) {
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }
    /**
     * UTF8字节数组转换为字符串
     */
    public static String utf8BytesToString(byte[] bytesValue) {
        return new String(bytesValue,StandardCharsets.UTF_8);
    }



    /**
     * 布尔转换为UTF8字节数组
     */
    public static byte[] booleanToUtf8Bytes(boolean booleanValue) {
        return String.valueOf(booleanValue).getBytes(StandardCharsets.UTF_8);
    }
    /**
     * UTF8字节数组转换为布尔
     */
    public static boolean utf8BytesToBoolean(byte[] bytesValue) {
        return Boolean.valueOf(new String(bytesValue,StandardCharsets.UTF_8));
    }



    /**
     * 拼接数组(数组数量为2个)。
     */
    public static byte[] concatenate(byte[] bytes1,byte[] bytes2) {
        return Bytes.concat(bytes1,bytes2);
    }
    /**
     * 拼接数组(数组数量为3个)。
     */
    public static byte[] concatenate3(byte[] bytes1,byte[] bytes2,byte[] bytes3) {
        return Bytes.concat(bytes1,bytes2,bytes3);
    }
    /**
     * 拼接数组(数组数量为4个)。
     */
    public static byte[] concatenate4(byte[] bytes1,byte[] bytes2,byte[] bytes3,byte[] bytes4) {
        return Bytes.concat(bytes1,bytes2,bytes3,bytes4);
    }

    /**
     * 拼接长度。
     * 计算[传入字节数组]的长度，然后将长度转为8个字节的字节数组(大端)，然后将长度字节数组拼接在[传入字节数组]前，然后返回。
     */
    public static byte[] concatenateLength(byte[] value) {
        return concatenate(uint64ToBytes(value.length),value);
    }

    /**
     * 碾平字节数组列表为字节数组。
     */
    public static byte[] flat(List<byte[]> values) {
        byte[] concatBytes = new byte[0];
        for(byte[] value:values){
            concatBytes = concatenate(concatBytes,value);
        }
        return concatBytes;
    }
    /**
     * 碾平字节数组列表为新的字节数组，然后拼接长度并返回。
     */
    public static byte[] flatAndConcatenateLength(List<byte[]> values) {
        byte[] flatBytes = flat(values);
        return concatenateLength(flatBytes);
    }


    public static byte[] copy(byte[] sourceBytes, int startPosition, int length) {
        byte[] destinationBytes = new byte[length];
        System.arraycopy(sourceBytes,startPosition,destinationBytes,0,length);
        return destinationBytes;
    }
    public static void copyTo(byte[] sourceBytes, int sourceStartPosition, int length, byte[] destinationBytes, int destinationStartPosition){
        System.arraycopy(sourceBytes,sourceStartPosition,destinationBytes,destinationStartPosition,length);
    }


    public static boolean equals(byte[] bytes1, byte[] bytes2) {
        return Arrays.equals(bytes1,bytes2);
    }

    public static byte[] random32Bytes(){
        byte[] randomBytes = new byte[32];
        Random RANDOM = new Random();
        RANDOM.nextBytes(randomBytes);
        return randomBytes;
    }

    public static void main(String[] args) {
        String contract = "contract main\n" +
                "{\n" +
                "    struct string\n" +
                "    {\n" +
                "        char[] chars\n" +
                "    }\n" +
                "    function PrintString(string string)\n" +
                "    {\n" +
                "        for(int i=0; i<string.chars.length; i=i+1){\n" +
                "            print(string.chars[i])\n" +
                "        }\n" +
                "        print('\\n')\n" +
                "    }\n" +
                "    function main(string[] args) string\n" +
                "    {\n" +
                "        PrintString(args[0])\n" +
                "        print('\\n')\n" +
                "        PrintString(args[1])\n" +
                "        print('\\n')\n" +
                "        PrintString(args[2])\n" +
                "        print('\\n')\n" +
                "        return \"xyz\"\n" +
                "    }\n" +
                "}" ;
        //System.out.println(bytesToHexString(stringToUtf8Bytes(contract)));
        String hex = "636f6e7472616374206d61696e0a7b0a2020202073747275637420737472696e670a202020207b0a2020202020202020636861725b5d2063686172730a202020207d0a2020202066756e6374696f6e205072696e74537472696e6728737472696e6720737472696e67290a202020207b0a2020202020202020666f7228696e7420693d303b20693c737472696e672e63686172732e6c656e6774683b20693d692b31297b0a2020202020202020202020207072696e7428737472696e672e63686172735b695d290a20202020202020207d0a20202020202020207072696e7428275c6e27290a202020207d0a2020202066756e6374696f6e206d61696e28737472696e675b5d20617267732920737472696e670a202020207b0a20202020202020205072696e74537472696e6728617267735b305d290a20202020202020207072696e7428275c6e27290a20202020202020205072696e74537472696e6728617267735b315d290a20202020202020207072696e7428275c6e27290a20202020202020205072696e74537472696e6728617267735b325d290a20202020202020207072696e7428275c6e27290a202020202020202072657475726e202278797a220a202020207d0a7d";
        System.out.println(utf8BytesToString(hexStringToBytes(hex)));
    }
}