package com.syl.myapplication1.observer;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Bright on 2018/7/15.
 *
 * @Describe 短信观察者
 * @Called
 */

public class SmsObserver extends ContentObserver {
    private static final String TAG = SmsObserver.class.getSimpleName();

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d(TAG,"收到一条心短信........");
    }
}
