package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.utils.StringTool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文本阅读器
 */
public class TxtActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = 2;
    private static final String TAG = TxtActivity.class.getSimpleName();
    private TextView mEtTxt;
    private TextView mTvTxt;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS:
                    String value = (String) msg.obj;
                    mTvTxt.setText(value);
                    Log.d(TAG,"文本加载成功..");
                    break;
                case CODE_ERROR:
                    Toast.makeText(TxtActivity.this, "文本加载失败..", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"文本加载失败..");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt);

        mEtTxt = findViewById(R.id.et_txt);
        mTvTxt = findViewById(R.id.tv_txt);
        mTvTxt.setMovementMethod(new ScrollingMovementMethod());//TextView自由滚动
        findViewById(R.id.btn_load_txt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String path = mEtTxt.getText().toString();//使用的是tomcat服务器下的文本文件
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "文本加载地址不能为空...", Toast.LENGTH_SHORT).show();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5 * 1000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String value = StringTool.decode(inputStream);
                        Message msg = Message.obtain();
                        msg.what = CODE_SUCCESS;
                        msg.obj = value;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = CODE_ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
}
