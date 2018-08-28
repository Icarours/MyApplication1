package com.syl.myapplication1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.service.BanzhengService;
import com.syl.myapplication1.service.IInnerService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Service 服务举例
 */
public class BanZhengActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_bind_bz)
    Button mBtnBindBz;
    @Bind(R.id.btn_bz)
    Button mBtnBz;
    @Bind(R.id.btn_unbind_bz)
    Button mBtnUnbindBz;
    @Bind(R.id.btn_stop_bz)
    Button mBtnStopBz;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private IInnerService mXiaoMi;
    private BanZhengConnection mConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_zheng);
        ButterKnife.bind(this);
        mBtnBindBz.setOnClickListener(this);
        mBtnBz.setOnClickListener(this);
        mBtnUnbindBz.setOnClickListener(this);
        mBtnStopBz.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("Service Demo,服务运行在后台,没有界面.服务运行在主线程中,如果要进行耗时操作" +
                ",依然要创建子线程.服务中的任务结束之后将相应的结果返回给Activity处理");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_bz:
                bindBZ();
                break;
            case R.id.btn_bz:
                bz();
                break;
            case R.id.btn_unbind_bz:
                unbindBZ();
                break;
            case R.id.btn_stop_bz:
                stopBZ();
                break;
            default:
                break;
        }
    }

    private void stopBZ() {
        Intent intent = new Intent(this, BanzhengService.class);
        stopService(intent);
    }

    class BanZhengConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mXiaoMi = (IInnerService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mXiaoMi = null;
        }
    }

    private void unbindBZ() {
        unbindService(mConn);
        mConn = null;
    }

    private void bz() {
        mXiaoMi.banZheng("tom", 160);
    }

    private void bindBZ() {
        Intent intent = new Intent(this, BanzhengService.class);
        mConn = new BanZhengConnection();
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
