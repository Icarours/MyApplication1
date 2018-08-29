package com.syl.coolwater.config;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}

