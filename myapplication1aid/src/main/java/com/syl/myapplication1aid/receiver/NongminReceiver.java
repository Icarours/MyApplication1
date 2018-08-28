package com.syl.myapplication1aid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NongminReceiver extends BroadcastReceiver {
    private final String TAG = NongminReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("农民收到大米..."+getResultData());
        Log.d(TAG,"农民收到大米..."+getResultData()+",谢谢领导..");
    }
}
