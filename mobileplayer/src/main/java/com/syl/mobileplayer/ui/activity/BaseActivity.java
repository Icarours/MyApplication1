package com.syl.mobileplayer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.util.LogUtil;


/**
 * Created by ThinkPad on 2016/4/11.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
        regcommBtn();
    }

    private void regcommBtn() {
        View back = findViewById(R.id.back);
        if(back!=null){
            back.setOnClickListener(this);
        }
    }

    //view值初始化
    protected abstract void initData();

    //初始化listener  adapter
    protected abstract void initListener();

    //初始化view
    protected abstract void initView();

    //获取布局id
    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        //back按钮
        if(v.getId()==R.id.back){
            finish();
        }else {
            //其他点击事件
            processClick(v);
        }
    }
    //处理除了back按钮之外的点击事件
    protected abstract void processClick(View v);
    //打印log
    public void logE(String msg){
        LogUtil.logE(this.getClass().getName(),msg);
    }
}
