package com.syl.coolwater.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe 线程管理类
 * @Called
 */
public class ThreadPoolManager {
    //1.把任务放到请求队列中
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue();

    //添加任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //2.把队列中的任务放到线程池中
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        Log.d("ThreadPoolManager","ThreadPoolManager was created..");
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), mRejectedExecutionHandler);
        //让程序运行起来
        mThreadPoolExecutor.execute(mRunnable);
    }

    //拒绝策略
    private RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        /**
         * 拒绝策略
         * @param r 超时策略
         * @param executor
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                mQueue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    //3.让他们开始工作起来
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                //从mQueue中拿到一个任务
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null) {
                    mThreadPoolExecutor.execute(runnable);
                }
            }
        }
    };
    //单例模式
    private static ThreadPoolManager ourInstance = new ThreadPoolManager();

    public static ThreadPoolManager getOurInstance() {
        if (ourInstance == null) {
            ourInstance = new ThreadPoolManager();
        }
        return ourInstance;
    }
}
