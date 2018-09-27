package com.syl.lib_java.producer_consumer;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class PCData {
    private  int data;

    @Override
    public String toString() {
        return "PCData{" +
                "data=" + data +
                '}';
    }

    public PCData(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
