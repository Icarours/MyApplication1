package com.syl.dclounddemo.config;

import io.dcloud.application.DCloudApplication;

/**
 * Created by Bright on 2018/10/14.
 *
 * @Describe
 * @Called
 */
public class MyApplication extends DCloudApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化极光推送
//        JPushInterface.setDebugMode(true);//打开日志
//        JPushInterface.init(this);
    }
}
