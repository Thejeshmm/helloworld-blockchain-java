package com.xingkaichun.helloworldblockchain.language.virtualmachine;

public abstract class ThisObject {

    protected VirtualMachine virtualMachine;

    public abstract int newThisAddress() ;

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }
}
