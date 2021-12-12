package com.xingkaichun.helloworldblockchain.application.vo.wallet;

import com.xingkaichun.helloworldblockchain.core.model.wallet.Payee;
import com.xingkaichun.helloworldblockchain.core.model.wallet.Payer;

import java.util.List;

/**
 * 构建交易请求
 *
 * @author 邢开春 409060350@qq.com
 */
public class AutomaticBuildTransactionRequest {

    //付款方,如果付款方为空，则自动查找付款方。
    private List<PayerVo> payers;
    //[找零]收款方
    private String changePayeeAddress;
    //交易手续费
    private long fee;

    //[非找零]收款方
    private List<PayeeVo> nonChangePayees;


    //region get set

    public List<PayerVo> getPayers() {
        return payers;
    }

    public void setPayers(List<PayerVo> payers) {
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

    public List<PayeeVo> getNonChangePayees() {
        return nonChangePayees;
    }
    public void setNonChangePayees(List<PayeeVo> nonChangePayees) {
        this.nonChangePayees = nonChangePayees;
    }
    //endregion
}
