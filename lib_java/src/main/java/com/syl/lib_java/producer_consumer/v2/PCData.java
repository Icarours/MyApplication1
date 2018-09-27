package com.syl.lib_java.producer_consumer.v2;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class PCData {
    private long value;

    @Override
    public String toString() {
        return "PCData{" +
                "value=" + value +
                '}';
    }

    public void set(long value){
        this.value = value;

    }
    public long get(){
        return value;
    }
}
