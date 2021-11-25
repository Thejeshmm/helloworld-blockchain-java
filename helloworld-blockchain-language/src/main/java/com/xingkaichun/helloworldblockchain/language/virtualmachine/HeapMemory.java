package com.xingkaichun.helloworldblockchain.language.virtualmachine;

import com.xingkaichun.helloworldblockchain.language.util.VirtualMachineException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HeapMemory {

    private VirtualMachine virtualMachine;
    private String[] memory;
    private int start;
    private int end;
    private List<Segement> usedSegement;
    private int heapMemorySize;
    public static final int VirtualBaseAddress = 1000000000;

    public HeapMemory(VirtualMachine virtualMachine, String[] memory, int start, int end) {
        this.virtualMachine = virtualMachine;
        this.memory = memory;
        this.start = start;
        this.end = end;
        usedSegement = new ArrayList<>();
        heapMemorySize = end - start + 1;
    }


    public int memoryAllocation(int size){
        if(usedSegement.size()==0){
            if(size > heapMemorySize){
                //TODO 垃圾回收
                gc0();
                throw new VirtualMachineException();
            }
            Segement segement = new Segement(start+0,start+size-1,size);
            fill0(segement);
            usedSegement.add(segement);
            return segement.getStartVirtualAddress();
        }else {
            for (int i=0;i<usedSegement.size();i++) {
                if(i == usedSegement.size()-1){
                    Segement segment = usedSegement.get(i);
                    if(end - segment.end < size){
                        throw new VirtualMachineException();
                    }
                    Segement segement = new Segement(segment.end+1,segment.end+size,size);
                    fill0(segement);
                    usedSegement.add(segement);
                    return segement.getStartVirtualAddress();
                }else {
                    Segement segment1 = usedSegement.get(i);
                    Segement segment2 = usedSegement.get(i+1);
                    if(segment2.start - segment1.end -1 > size){
                        Segement segement = new Segement(segment1.end+1,segment1.end+size,size);
                        fill0(segement);
                        usedSegement.add(segement);
                        return segement.getStartVirtualAddress();
                    }
                }
            }
        }
        throw new VirtualMachineException();
    }

    private void gc0() {
        Set<Integer> set = this.virtualMachine.getHeapAddress();
    }

    private void fill0(Segement segement) {
        for(int i=segement.start;i<=segement.end;i++){
            memory[i] = "0";
        }
    }

    public void delete(int position){

    }


    class Segement{
        int start;
        int end;
        int size;
        public Segement(int start, int end, int size) {
            this.start = start;
            this.end = end;
            this.size = size;
        }

        public int getStartVirtualAddress(){
            return VirtualBaseAddress + start;
        }
    }


    public static void main(String[] args) {
/*        HeapMemory heapMemory = new HeapMemory(null,new int[20000],0,19999);
        heapMemory.memoryAllocation(10);
        heapMemory.memoryAllocation(77);
        heapMemory.memoryAllocation(127);
        heapMemory.memoryAllocation(3333);
        heapMemory.memoryAllocation(1);
        for(Segement segement:heapMemory.usedSegement){
            System.out.println(segement.getStartVirtualAddress()+" "+segement.start+" "+segement.end+" "+segement.size);
        }*/
    }

    public int getHeapMemoryValue(int address) {
        return Integer.valueOf(memory[address-VirtualBaseAddress]);
    }
    public String getHeapMemoryStringValue(int address) {
        return memory[address-VirtualBaseAddress];
    }
    public void setHeapMemoryValue(int address,int value) {
        memory[address-VirtualBaseAddress] = String.valueOf(value);
    }
    public void setHeapMemoryValue(int address,String value) {
        memory[address-VirtualBaseAddress] = value;
    }
}
