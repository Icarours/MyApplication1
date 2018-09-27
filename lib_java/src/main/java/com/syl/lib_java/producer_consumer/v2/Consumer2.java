package com.syl.lib_java.producer_consumer.v2;


import java.util.List;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class Consumer2 implements Runnable {
    private List<PCData> queue;

    public Consumer2(List<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                PCData data ;
                synchronized (queue) {
                    if (queue.size() == 0) {
                        queue.wait();//让queue中运行的线程等待
                        queue.notifyAll();////唤醒queue中等待的线程
                    }
                    data = queue.remove(0);
                }
                System.out.println("consumer Thread id==="+
                        Thread.currentThread().getId() + " 消费了:" + data.get() + " result:" + (data.toString()));
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
