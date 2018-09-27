package com.syl.lib_java.producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe 生产者-消费者模型:生产者
 * @Called
 */
public class Producer implements Runnable {
    private BlockingQueue<PCData> mQueue;//内存缓冲区
    private boolean isRunning = false;
    private AtomicInteger count = new AtomicInteger();//总原子操作数
    private static final int SLEEPTIME = 1000;


    public Producer(BlockingQueue<PCData> queue) {
        mQueue = queue;
    }

    @Override
    public void run() {
        PCData data ;
        Random random = new Random();
        System.out.println("Producer Thread--" + Thread.currentThread().getId());
        try {
            while (isRunning) {
                Thread.sleep(random.nextInt(SLEEPTIME));
                data = new PCData(count.incrementAndGet());
                System.out.println(data + "加入队列..");
                if (!mQueue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("加入队列失败..");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    public void stopAction(){
        isRunning = false;
    }
}
