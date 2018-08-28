package com.syl.myapplication1aid;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.syl.myapplication1aid.observer.BankObserver;
import com.syl.myapplication1aid.receiver.NongminReceiver;
import com.syl.myapplication1aid.receiver.ShengReceiver;
import com.syl.myapplication1aid.receiver.ShiReceiver;
import com.syl.myapplication1aid.receiver.XianReceiver;

public class MainActivity extends AppCompatActivity {

    private ShengReceiver mShengReceiver;
    private ShiReceiver mShiReceiver;
    private XianReceiver mXianReceiver;
    private NongminReceiver mNmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mShengReceiver = new ShengReceiver();
//        registerReceiver(mShengReceiver, new IntentFilter("RECEIVER_ORDER"));
//        mShiReceiver = new ShiReceiver();
//        registerReceiver(mShiReceiver, new IntentFilter("RECEIVER_ORDER"));
//        mXianReceiver = new XianReceiver();
//        registerReceiver(mXianReceiver, new IntentFilter("RECEIVER_ORDER"));
//        mNmReceiver = new NongminReceiver();
//        registerReceiver(mNmReceiver, new IntentFilter("RECEIVER_ORDER"));
        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        //注册一个观察者  content://com.itheima.bank/account/7/2/34

        /**
         * 参数一： 要关观察哪个路径
         * 参数二： true 只要观察的uri匹配了通知的uri前面部分， 那么就能收到通知 false 那么要求所有的字符全匹配
         */
        getContentResolver().registerContentObserver(uri,true,new BankObserver(new Handler()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mShengReceiver);
        unregisterReceiver(mShiReceiver);
        unregisterReceiver(mXianReceiver);
        unregisterReceiver(mNmReceiver);
    }
}
