package com.xingkaichun.helloworldblockchain.language.virtualmachine;

public abstract class VirtualMachineThisFactory {

    protected VirtualMachine virtualMachine;

    public abstract int getThisAddress() ;

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }
}
