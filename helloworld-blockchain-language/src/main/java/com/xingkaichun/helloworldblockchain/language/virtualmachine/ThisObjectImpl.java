package com.xingkaichun.helloworldblockchain.language.virtualmachine;

public class ThisObjectImpl extends ThisObject{

    private String fromAddress;
    private String toAddress;

    public ThisObjectImpl(VirtualMachine virtualMachine, String fromAddress, String toAddress) {
        this.virtualMachine = virtualMachine;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public int newThisAddress(){
        int thisAddress = virtualMachine.newObject(1);
        int transactionAddress = virtualMachine.newObject(2);
        virtualMachine.storeObjectField(thisAddress,0,transactionAddress);
        int intFromAddress = virtualMachine.newString(fromAddress);
        int intToAddress = virtualMachine.newString(toAddress);
        virtualMachine.storeObjectField(transactionAddress,0,intFromAddress);
        virtualMachine.storeObjectField(transactionAddress,1,intToAddress);
        return thisAddress;
    }
}
