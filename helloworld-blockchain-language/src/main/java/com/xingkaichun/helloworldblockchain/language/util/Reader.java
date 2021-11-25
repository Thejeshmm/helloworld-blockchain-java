package com.xingkaichun.helloworldblockchain.language.util;

/**
 * @author xingkaichun@ceair.com
 */
public class Reader<T> {

    private T[] values;
    private int position;

    public Reader(T[] values) {
        this.values = values;
        this.position = 0;
    }


    public boolean hasNext(){
        return position < values.length;
    }
    public boolean hasNextNext(){
        return position + 1 < values.length;
    }
    public boolean hasNextNextNext(){
        return position + 2 < values.length;
    }

    public T next(){
        return values[position++];
    }

    public T peek(){
        return values[position];
    }
    public T peekPeek(){
        return values[position+1];
    }
    public T peekPeekPeek(){
        return values[position+2];
    }

    public void back(){
        position = position-1;
    }
    public void backBack(){
        position = position-2;
    }
    public void backBackBack(){
        position = position-3;
    }

    public boolean hasBack(){
        return position-1>=0;
    }
    public T review(){
        return values[position-1];
    }
}
