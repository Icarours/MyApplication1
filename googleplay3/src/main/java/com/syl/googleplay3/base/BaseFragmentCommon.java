package com.syl.googleplay3.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bright on 2018/7/2.
 *
 * @Describe 抽取基类(常规):
 * 1.可以抽取共有的属性和方法
 * 2.不用再关心Fragment生命周期相关的方法
 * 3.控制哪些方法是必须实现,哪些方法是选择性实现
 * @Called
 */

public abstract class BaseFragmentCommon extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract View initView();

    protected void initListener() {

    }
    protected abstract void initData();
    protected void init(){}
}
