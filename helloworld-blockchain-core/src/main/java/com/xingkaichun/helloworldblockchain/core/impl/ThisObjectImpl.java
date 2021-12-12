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
        int transactionAddress = virtualMachine.newObject(1);
        virtualMachine.storeObjectField(thisAddress,0,transactionAddress);
        String publicKeyHash = transaction.getInputs().get(0).getInputScript().get(3);
        String address = AccountUtil.addressFromPublicKeyHash(publicKeyHash);
        int intFromAddress = virtualMachine.newString(address);
        virtualMachine.storeObjectField(transactionAddress,0,intFromAddress);
        return thisAddress;
    }
}
