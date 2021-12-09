package com.xingkaichun.helloworldblockchain.netcore.dto;


import java.io.Serializable;
import java.util.List;

/**
 * 交易
 * 属性含义参考
 * @see com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction
 *
 * @author 邢开春 409060350@qq.com
 */
public class TransactionDto implements Serializable {

    //交易输入
    private List<TransactionInputDto> inputs;
    //交易输出
    private List<TransactionOutputDto> outputs;

    //部署的合约
    private String deployContract;

    //执行合约
    private String executeContract;


    //region get set
    public List<TransactionInputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInputDto> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutputDto> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TransactionOutputDto> outputs) {
        this.outputs = outputs;
    }

    public String getDeployContract() {
        return deployContract;
    }

    public void setDeployContract(String deployContract) {
        this.deployContract = deployContract;
    }

    public String getExecuteContract() {
        return executeContract;
    }

    public void setExecuteContract(String executeContract) {
        this.executeContract = executeContract;
    }
    //endregion
}