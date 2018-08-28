package com.syl.myapplication1.activity;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.utils.IntentUtil;
import com.syl.myapplication1aid.aidl.IPayNeixian;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 支付,客户端
 */
public class AliPayActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_bind_ali)
    Button mBtnBindAli;
    @Bind(R.id.btn_zhifu)
    Button mBtnZhifu;
    @Bind(R.id.btn_unbind_ali)
    Button mBtnUnbindAli;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private IPayNeixian mPayNeixian;
    private AliPayConnection mConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay);
        ButterKnife.bind(this);
        mBtnBindAli.setOnClickListener(this);
        mBtnZhifu.setOnClickListener(this);
        mBtnUnbindAli.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("AIDL,支付客户端;服务端在另一个APP:com.syl.myapplication1aid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_ali:
                bindAli();
                break;
            case R.id.btn_zhifu:
                zhifu();
                break;
            case R.id.btn_unbind_ali:
                unBindAli();
                break;
            default:
                break;
        }
    }

    private void unBindAli() {
        unbindService(mConn);
        mConn = null;
    }

    private void zhifu() {
        try {//远程支付,可能会出现异常
            mPayNeixian.callPay("Jack", 100);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindAli() {
        Intent intent1 = new Intent();
        intent1.setAction("com.alipay.PAYSERVICE");
        //API 21之后,service的Intent必须使用显式声明.
        Intent intent = IntentUtil.createExplicitFromImplicitIntent(this, intent1);
        mConn = new AliPayConnection();
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    class AliPayConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPayNeixian = IPayNeixian.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPayNeixian = null;
        }
    }
}
