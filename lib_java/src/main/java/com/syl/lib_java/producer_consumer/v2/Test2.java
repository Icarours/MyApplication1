package com.syl.lib_java.producer_consumer.v2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class Test2 {
    public static void main(String[] args) {
        List<PCData> list = new ArrayList<>();
        int length =10;
        Producer2 p1 = new Producer2(list,length);
        Producer2 p2 = new Producer2(list,length);
        Producer2 p3 = new Producer2(list,length);

        Consumer2 c1 = new Consumer2(list);
        Consumer2 c2 = new Consumer2(list);
        Consumer2 c3 = new Consumer2(list);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
    }
}
