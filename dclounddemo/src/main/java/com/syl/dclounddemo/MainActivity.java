package com.syl.dclounddemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;

import com.syl.dclounddemo.webview.WebViewModeListener;

import io.dcloud.EntryProxy;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.feature.internal.sdk.SDK;
/**
 * author   Bright
 * date     2018/10/14 0:59
 * desc
 * 3 启动5+ 内核
 * 4 传递Activity事件给5+ SDK
 */
public class MainActivity extends AppCompatActivity {

    private EntryProxy mEntryProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        if(mEntryProxy == null){
            FrameLayout rootView = new FrameLayout(this);
            // 创建5+内核运行事件监听
            WebViewModeListener wm = new WebViewModeListener(this,rootView);
            // 初始化5+内核
            mEntryProxy = EntryProxy.init(this, wm);
            // 启动5+内核，并指定内核启动类型
            mEntryProxy.onCreate(savedInstanceState, SDK.IntegratedMode.WEBVIEW,null);
            setContentView(rootView);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return mEntryProxy.onActivityExecute(this, ISysEventListener.SysEventType.onCreateOptionMenu, menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        mEntryProxy.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mEntryProxy.onResume(this);
    }

    @SuppressLint("WrongConstant")
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getFlags() != 0x10600000) {
            // 非点击icon调用activity时才调用newintent事件
            mEntryProxy.onNewIntent(this, intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEntryProxy.onStop(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean _ret = mEntryProxy.onActivityExecute(this, ISysEventListener.SysEventType.onKeyDown, new Object[] { keyCode, event });
        return _ret ? _ret : super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean _ret = mEntryProxy.onActivityExecute(this, ISysEventListener.SysEventType.onKeyUp, new Object[] { keyCode, event });
        return _ret ? _ret : super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        boolean _ret = mEntryProxy.onActivityExecute(this, ISysEventListener.SysEventType.onKeyLongPress, new Object[] { keyCode, event });
        return _ret ? _ret : super.onKeyLongPress(keyCode, event);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        try {
            int temp = this.getResources().getConfiguration().orientation;
            if (mEntryProxy != null) {
                mEntryProxy.onConfigurationChanged(this, temp);
            }
            super.onConfigurationChanged(newConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mEntryProxy.onActivityExecute(this, ISysEventListener.SysEventType.onActivityResult, new Object[] { requestCode, resultCode, data });
    }
}
