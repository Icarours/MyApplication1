package com.syl.myapplication1aid;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.syl.myapplication1aid.bean.HomeBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.button).setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String resJsonStr = (String) msg.obj;
                        parseJson(resJsonStr);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void parseJson(String resJsonStr) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(resJsonStr, HomeBean.class);
        System.out.println("homeBean=="+homeBean);
    }

    @Override
    public void onClick(View v) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://192.168.31.89:80/GooglePlayServer/home?index=0";
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resJsonStr = response.body().string();
                System.out.println("resJsonStr=="+resJsonStr);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = resJsonStr;
                mHandler.sendMessage(msg);
            }
        });
    }
}
