package com.syl.lib_java.producer_consumer.v2;


import java.util.List;
import java.util.Random;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class Producer2 implements Runnable {
    private List<PCData> queue;
    private int length;

    public Producer2(List<PCData> queue, int length) {
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        try {
            while (true) {

                if (Thread.currentThread().isInterrupted())
                    break;
                Random r = new Random();
                long temp = r.nextInt(100);
                System.out.println("producer Thread id==="+Thread.currentThread().getId() + " 生产了：" + temp);
                PCData data = new PCData();
                data.set(temp);
                synchronized (queue) {
                    if (queue.size() >= length) {
                        queue.notifyAll();//唤醒queue中等待的线程
                        queue.wait();//让queue中运行的线程等待
                    } else
                        queue.add(data);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
