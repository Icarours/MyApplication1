package com.syl.myapplication1aid.observer;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Bright on 2018/7/15.
 *
 * @Describe
 * @Called
 */

public class BankObserver extends ContentObserver {
    private static final String TAG = BankObserver.class.getSimpleName();

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public BankObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d(TAG,"银行账户信息发生变化..");
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}
