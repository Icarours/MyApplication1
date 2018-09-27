package com.syl.lib_java.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class Test1 {
    public static void main(String[] args) {
        v1();

    }

    private static void v1() {
        BlockingQueue<PCData> queue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            service.execute(p1);
            service.execute(p2);
            service.execute(p3);
            service.execute(c1);
            service.execute(c2);
            service.execute(c3);
            Thread.sleep(10*1000);
            p1.stopAction();
            p2.stopAction();
            p3.stopAction();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
