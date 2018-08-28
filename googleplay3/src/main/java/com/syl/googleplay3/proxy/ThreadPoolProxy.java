package com.syl.googleplay3.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 线程池代理类
 * 代理设计模式,代理模式就是多一个代理类出来，替原对象进行一些操作
 *
 * 代理模式的应用场景：
 * 如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
 * 1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。
 * 2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。
 * 使用代理模式，可以将功能划分的更加清晰，有助于后期维护！
 * @Called
 */

public class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心池
    private int mMaximumPoolSize;//最大线程数

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    /**
     * 双重检查加锁,保证只有在第一次初始化的时候启用同步机制,提高了性能
     */
    private void initTreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolExecutor.class) {
                //在方法上添加synchronized关键字,每次调用该方法都会加锁,消耗资源,建议在方法内部代码块添加synchronized
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }

    /**
     * 1.提交任务
     * submit会自己处理异常,不会出现crash,最好使用submit提交任何
     *
     * @param task
     */
    public Future<?> submit(Runnable task) {
        initTreadPoolExecutor();
        Future<?> submit = mExecutor.submit(task);
        return submit;
    }

    /**
     * 2.执行任务
     * execute有可能出现阻塞,异常,可能会出现crash
     *
     * @param task
     */
    public void execute(Runnable task) {
        initTreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 3.移除任务
     *
     * @param task
     */
    public void remove(Runnable task) {
        initTreadPoolExecutor();
        mExecutor.remove(task);
    }
}
