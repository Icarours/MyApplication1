package com.syl.myapplication1aid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShiReceiver extends BroadcastReceiver {
    private final String TAG = ShiReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("市政府收到大米..." + getResultData());
        //abortBroadcast();//终止广播的发送
        setResultData("每人发 3斤 大米");
        Log.d(TAG,"市政府收到大米..." + getResultData());
    }
}
