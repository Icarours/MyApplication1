package com.syl.myapplication1.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.receiver.FinalReceiver;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 广播.无序广播和有序广播
 */
public class ReceiverActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String RECEIVER_UN_ORDER = "RECEIVER_UN_ORDER";
    private static final String RECEIVER_ORDER = "RECEIVER_ORDER";
    private static final String TAG = ReceiverActivity.class.getSimpleName();
    @Bind(R.id.btn_unorder_receiver)
    Button mBtnUnorderReceiver;
    @Bind(R.id.btn_order_receiver)
    Button mBtnOrderReceiver;
    @Bind(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        ButterKnife.bind(this);
        mBtnUnorderReceiver.setOnClickListener(this);
        mBtnOrderReceiver.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("广播接收者.有序广播和无序广播,发送通知..");
        Log.d(TAG, "onCreate()..");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unorder_receiver:
                sendReceiverUnOrder();
                break;
            case R.id.btn_order_receiver:
                sendReceiverOrder();
                break;
            default:
                break;
        }
    }

    private void sendReceiverUnOrder() {
        Log.d(TAG, "发送无序广播");
        Intent intent = new Intent();
        intent.setAction(RECEIVER_UN_ORDER);
        sendBroadcast(intent);
    }

    private void sendReceiverOrder() {
        Log.d(TAG, "发送有序广播");
        // intent : 意图 对象
        // receiverPermission: 接收者需要的权限
        // resultReceiver: 目标接收者-- 最终接收者
        // scheduler: 调度器 , 一般写 null
        // initialCode: 初始码
        // initialData: 初始数据
        // initialExtras: 初始时额外的数据
        Intent intent = new Intent();
        intent.setAction(RECEIVER_ORDER);

        FinalReceiver finalReceiver = new FinalReceiver();
        registerReceiver(finalReceiver, new IntentFilter(RECEIVER_ORDER));
        //发了一个有序的广播
        sendOrderedBroadcast(intent, null, finalReceiver, null, 0, "过年发福利,每人发10斤大米", null);
    }
}
