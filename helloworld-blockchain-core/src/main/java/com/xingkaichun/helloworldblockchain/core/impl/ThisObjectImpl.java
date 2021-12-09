package com.xingkaichun.helloworldblockchain.core.impl;

import com.xingkaichun.helloworldblockchain.core.model.transaction.TransactionOutput;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.ThisObject;
import com.xingkaichun.helloworldblockchain.language.virtualmachine.VirtualMachine;

public class ThisObjectImpl extends ThisObject {

    private TransactionOutput transactionOutput;

    public ThisObjectImpl(VirtualMachine virtualMachine, TransactionOutput transactionOutput) {
        this.virtualMachine = virtualMachine;
        this.transactionOutput = transactionOutput;
    }

    public int newThisAddress(){
        int thisAddress = virtualMachine.newObject(1);
        int transactionAddress = virtualMachine.newObject(2);
        virtualMachine.storeObjectField(thisAddress,0,transactionAddress);
        int intFromAddress = virtualMachine.newString(transactionOutput.getAddress());
        int intToAddress = virtualMachine.newString(transactionOutput.getAddress());
        virtualMachine.storeObjectField(transactionAddress,0,intFromAddress);
        virtualMachine.storeObjectField(transactionAddress,1,intToAddress);
        return thisAddress;
    }
}
