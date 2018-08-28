package com.syl.myapplication1.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.observer.BankObserver;
import com.syl.myapplication1.observer.SmsObserver;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ObserverActivity extends AppCompatActivity {

    @Bind(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        ButterKnife.bind(this);
        mTvTips.setText("内容观察者,类似于广播的作用,被观察的特定动作发生后,就会给观察者发送信息..");
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());

        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        //第二个参数notifyForDescendants,true表示通知uri下的所有,false表示通知特定路径的uri
        getContentResolver().registerContentObserver(uri, true, new BankObserver(new Handler()));

        Uri uri1 = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri1, true, new SmsObserver(new Handler()));
    }

}
