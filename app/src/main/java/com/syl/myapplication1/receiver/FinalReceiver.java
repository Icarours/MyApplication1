package com.syl.myapplication1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FinalReceiver extends BroadcastReceiver {

    private static final String TAG = FinalReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"最终的广播接收者 : " + getResultData());
    }
}
