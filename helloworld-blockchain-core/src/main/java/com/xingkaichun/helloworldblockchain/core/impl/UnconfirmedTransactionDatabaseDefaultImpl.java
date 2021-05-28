package com.xingkaichun.helloworldblockchain.core.impl;

import com.xingkaichun.helloworldblockchain.core.CoreConfiguration;
import com.xingkaichun.helloworldblockchain.core.UnconfirmedTransactionDatabase;
import com.xingkaichun.helloworldblockchain.core.tools.EncodeDecodeTool;
import com.xingkaichun.helloworldblockchain.core.tools.TransactionTool;
import com.xingkaichun.helloworldblockchain.crypto.ByteUtil;
import com.xingkaichun.helloworldblockchain.netcore.transport.dto.TransactionDto;
import com.xingkaichun.helloworldblockchain.util.FileUtil;
import com.xingkaichun.helloworldblockchain.util.KvDBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认实现
 *
 * @author 邢开春 409060350@qq.com
 */
public class UnconfirmedTransactionDatabaseDefaultImpl extends UnconfirmedTransactionDatabase {

    private static final String UNCONFIRMED_TRANSACTION_DATABASE_NAME = "UnconfirmedTransactionDatabase";
    private final String unconfirmedTransactionDatabasePath;

    public UnconfirmedTransactionDatabaseDefaultImpl(CoreConfiguration coreConfiguration) {
        this.unconfirmedTransactionDatabasePath = FileUtil.newPath(coreConfiguration.getCorePath(), UNCONFIRMED_TRANSACTION_DATABASE_NAME);
    }

    @Override
    public void insertTransaction(TransactionDto transactionDTO) {
        //交易已经持久化进交易池数据库 丢弃交易
        synchronized (UnconfirmedTransactionDatabaseDefaultImpl.class){
            String transactionHash = TransactionTool.calculateTransactionHash(transactionDTO);
            KvDBUtil.put(unconfirmedTransactionDatabasePath, getKey(transactionHash), EncodeDecodeTool.encode(transactionDTO));
        }
    }

    @Override
    public List<TransactionDto> selectTransactionList(long from, long size) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        List<byte[]> bytesTransactionDTOList = KvDBUtil.get(unconfirmedTransactionDatabasePath,from,size);
        if(bytesTransactionDTOList != null){
            for(byte[] bytesTransactionDTO:bytesTransactionDTOList){
                TransactionDto transactionDTO = EncodeDecodeTool.decodeToTransactionDTO(bytesTransactionDTO);
                transactionDtoList.add(transactionDTO);
            }
        }
        return transactionDtoList;
    }

    @Override
    public void deleteByTransactionHash(String transactionHash) {
        KvDBUtil.delete(unconfirmedTransactionDatabasePath, getKey(transactionHash));
    }

    @Override
    public TransactionDto selectTransactionByTransactionHash(String transactionHash) {
        byte[] byteTransactionDTO = KvDBUtil.get(unconfirmedTransactionDatabasePath, getKey(transactionHash));
        if(byteTransactionDTO == null){
            return null;
        }
        return EncodeDecodeTool.decodeToTransactionDTO(byteTransactionDTO);
    }

    private byte[] getKey(String transactionHash){
        return ByteUtil.encode(transactionHash);
    }
}