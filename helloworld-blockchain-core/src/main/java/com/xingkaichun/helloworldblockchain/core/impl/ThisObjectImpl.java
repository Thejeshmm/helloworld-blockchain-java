package com.xingkaichun.helloworldblockchain.core.impl;

import com.xingkaichun.helloworldblockchain.core.model.transaction.Transaction;
import com.xingkaichun.helloworldblockchain.crypto.AccountUtil;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.ThisObject;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachine;

public class ThisObjectImpl extends ThisObject {

    private Transaction transaction;

    public ThisObjectImpl(VirtualMachine virtualMachine, Transaction transaction) {
        this.virtualMachine = virtualMachine;
        this.transaction = transaction;
    }

    public int newThisAddress(){
        int thisAddress = virtualMachine.newObject(1);
        int transactionAddress = virtualMachine.newObject(2);
        virtualMachine.storeObjectField(thisAddress,0,transactionAddress);
        String address = null;
        //TODO
        if(transaction.getInputs() == null || transaction.getInputs().isEmpty()){
            address = AccountUtil.addressFromPublicKeyHash("00");
        }else {
            String publicKeyHash = transaction.getInputs().get(0).getInputScript().get(3);
            address = AccountUtil.addressFromPublicKeyHash(publicKeyHash);
        }
        int intFromAddress = virtualMachine.newString(address);
        int intToAddress = virtualMachine.newString(transaction.getOutputs().get(0).getAddress());
        virtualMachine.storeObjectField(transactionAddress,0,intFromAddress);
        virtualMachine.storeObjectField(transactionAddress,1,intToAddress);
        return thisAddress;
    }
}
