package com.syl.myapplication1.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 打开Android系统的浏览器,发短信,打电话
 */
public class HintIntentActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = HintIntentActivity.class.getSimpleName();
    @Bind(R.id.btn_browser)
    Button mBtnBrowser;
    @Bind(R.id.btn_call1)
    Button mBtnCall1;
    @Bind(R.id.btn_sms1)
    Button mBtnSms1;
    @Bind(R.id.btn_call2)
    Button mBtnCall2;
    @Bind(R.id.btn_sms2)
    Button mBtnSms2;
    @Bind(R.id.btn_sms3)
    Button mBtnSms3;
    private final static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private final static String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private SMSBroadcastReceiver1 mSmsBr1;
    private SMSBroadcastReceiver2 mSmsBr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_intent);
        ButterKnife.bind(this);
        mBtnBrowser.setOnClickListener(this);
        mBtnSms1.setOnClickListener(this);
        mBtnSms2.setOnClickListener(this);
        mBtnSms3.setOnClickListener(this);
        mBtnCall1.setOnClickListener(this);
        mBtnCall2.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("隐式启动,调用系统程序:打电话,发短信");

        //注册广播,回调短信发出后的结果
        mSmsBr1 = new SMSBroadcastReceiver1();
        registerReceiver(mSmsBr1, new IntentFilter(SENT_SMS_ACTION));

        mSmsBr2 = new SMSBroadcastReceiver2();
        registerReceiver(mSmsBr2, new IntentFilter(DELIVERED_SMS_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mSmsBr1);
        unregisterReceiver(mSmsBr2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_browser:
                startBrowser("http://192.168.31.89:80");//打开浏览器
                break;
            case R.id.btn_sms1:
                startSms1("10086", "11");//发送短信1
                break;
            case R.id.btn_sms2:
                startSms2("10086", "12");//发送短信2
                break;
            case R.id.btn_sms3:
                startSms3("10086", "13");//发送短信3
                break;
            case R.id.btn_call1:
                call1("10086");//打电话1
                break;
            case R.id.btn_call2:
                call2("10086");//打电话2
                break;
            default:
                break;
        }
    }

    /**
     * 后台发送,不会打开短信界面
     *
     * @param tel     电话号码
     * @param content 短信内容
     */
    private void startSms1(String tel, String content) {
        Intent sendIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        for (String msg : divideMessage) {
            smsManager.sendTextMessage(tel, null, msg, sendPi, null);
        }
    }

    /**
     * 后台发送,不会打开短信界面
     *
     * @param tel     电话号码
     * @param content 短信内容
     */
    private void startSms2(String tel, String content) {
        Intent intent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliveryPI = PendingIntent.getBroadcast(this, 0, intent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        for (String msg : divideMessage) {
            smsManager.sendTextMessage(tel, null, msg, null, deliveryPI);
        }
    }

    /**
     * 这种方式会打开短信发送界面
     *
     * @param tel     电话号码
     * @param content 短信内容
     */
    private void startSms3(String tel, String content) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
            intent.putExtra("sms_body", content);
            startActivity(intent);
        }
    }

    private void call1(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);
    }

    private void call2(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        startActivity(intent);
    }


    private void startBrowser(String path) {
        //发一个隐 式 意图, 去激活 手机中的 browser (浏览器) 应用程序程序,并且去访问 tomcat服务器的首页
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(path));//uri地址一定要写完整,否则不能访问到.例如:www.baidu.com
        startActivity(intent);
    }

    private class SMSBroadcastReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Log.e(TAG, "SmsManager.RESULT_ERROR_GENERIC_FAILURE");
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Log.e(TAG, "SmsManager.RESULT_ERROR_RADIO_OFF");
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Log.e(TAG, "SmsManager.RESULT_ERROR_NULL_PDU");
                    break;
            }
        }
    }

    private class SMSBroadcastReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
        }
    }

}
