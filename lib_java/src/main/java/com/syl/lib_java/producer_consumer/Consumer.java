package com.syl.lib_java.producer_consumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Bright on 2018/9/16.
 *
 * @Describe
 * @Called
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> mQueue;
    private static final int SLEEPTIME = 1000;
    public Consumer(BlockingQueue<PCData> queue) {
        mQueue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id --"+Thread.currentThread().getId());
        Random r = new Random();
        try{
            while(true){
                PCData data = mQueue.take();
                if(data != null)
                {
                    int re = data.getData() * data.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
