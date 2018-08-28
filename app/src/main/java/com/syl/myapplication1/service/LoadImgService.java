package com.syl.myapplication1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Service demo
 */
public class LoadImgService extends Service {
    private static final String TAG = LoadImgService.class.getSimpleName();
    private static final int CODE_SUCCESS_DATA = 1;
    private MyHandler mHandler = new MyHandler();
    private String mJsonString;

    public LoadImgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LoadImgBinder();
    }

    boolean flag = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "LoadImgService--- onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("data");
        Log.d(TAG, data);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        flag = false;
        super.onDestroy();
        Log.d(TAG, "LoadImgService--- onDestroy...");
    }

    String jsonString;

    public class LoadImgBinder extends Binder {
        public String loadDataInService() {
            Log.d(TAG, Thread.currentThread().getName());
            loadData();
            stopSelf();
            return mJsonString;
        }
    }

    public void loadData() {
        String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "数据请求失败..");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "数据请求成功..");
                Log.d(TAG, "Thread===" + Thread.currentThread().getName());
                jsonString = response.body().string();
                Message msg = Message.obtain();
                msg.obj = jsonString;
                msg.what = CODE_SUCCESS_DATA;
                mHandler.sendMessage(msg);
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS_DATA:
                    mJsonString = (String) msg.obj;
                    break;
                default:
                    break;
            }
        }
    }
}
