package com.syl.myapplication1aid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class XianReceiver extends BroadcastReceiver {
    private final String TAG = XianReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("县政府收到大米..."+getResultData());
        //abortBroadcast();//终止广播的发送
        setResultData("每人发 2斤 大米");
        Log.d(TAG,"县政府收到大米..."+getResultData());
    }
}
