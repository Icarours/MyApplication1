package com.syl.googleplay3.factory;

import com.syl.googleplay3.proxy.ThreadPoolProxy;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * 1.使用方便
 * 2.共用一个线程池
 * 3.对线程池代理创建封装
 * @Called
 */

public class ThreadPoolProxyFactory {
    private static ThreadPoolProxy mNormalThreadPoolProxy;
    private static ThreadPoolProxy mDownloadThreadPoolProxy;

    /**
     * 普通用线程池工厂
     * @return
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mNormalThreadPoolProxy==null){
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5,5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 下载用线程池工厂
     * @return
     */
    public static ThreadPoolProxy getDownloadThreadPoolProxy() {
        if (mDownloadThreadPoolProxy==null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mDownloadThreadPoolProxy==null){
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3,3);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
