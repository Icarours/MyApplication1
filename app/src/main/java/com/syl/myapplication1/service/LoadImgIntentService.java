package com.syl.myapplication1.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Bright on 2018/7/12.
 *
 * @Describe 使用IntentService必须提供一个空参的构造方法,否则manifest.xml文件中会报错
 * @Called
 */

public class LoadImgIntentService extends IntentService {
    public LoadImgIntentService() {
        super("LoadImgIntentService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LoadImgIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            String data = intent.getStringExtra("data");
            Thread.sleep(5000);
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName()+thread.getId()+"---"+data);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(this.getClass().getSimpleName()+"--onCreate..");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println(this.getClass().getSimpleName()+"--onStartCommand..");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println(this.getClass().getSimpleName()+"--onDestroy..");
        super.onDestroy();
    }
}
