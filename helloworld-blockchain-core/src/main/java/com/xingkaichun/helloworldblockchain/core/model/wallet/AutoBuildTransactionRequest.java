package com.xingkaichun.helloworldblockchain.core.model.wallet;

import java.util.List;

/**
 * 构建交易请求
 *
 * @author 邢开春 409060350@qq.com
 */
//TODO 重命名
public class AutoBuildTransactionRequest {

    //付款方,如果付款方为空，则自动查找付款方。
    private List<Payer> payers;
    //[找零]收款方
    private String changePayeeAddress;
    //交易手续费
    private long fee;
    //[非找零]收款方
    private List<Payee> nonChangePayees;


    //region get set
    public List<Payer> getPayers() {
        return payers;
    }
    public void setPayers(List<Payer> payers) {
        this.payers = payers;
    }

    public String getChangePayeeAddress() {
        return changePayeeAddress;
    }

    public void setChangePayeeAddress(String changePayeeAddress) {
        this.changePayeeAddress = changePayeeAddress;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public List<Payee> getNonChangePayees() {
        return nonChangePayees;
    }
    public void setNonChangePayees(List<Payee> nonChangePayees) {
        this.nonChangePayees = nonChangePayees;
    }
    //endregion
}
