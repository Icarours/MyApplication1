package com.syl.googleplay3.base;

import android.view.View;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 1.提供视图,2,提供数据,3.视图和数据的绑定
 * @Called
 */

public abstract class BaseHolder<HOLDERBEANTYPE> {
    public View mHolderView;
    private HOLDERBEANTYPE mData;

    public BaseHolder() {
        //初始化根视图
        mHolderView = initHolderView();
        //绑定tag
        mHolderView.setTag(this);
    }

    //提供视图
    public abstract View initHolderView();


    public void setDataAndRefreshHolderView(HOLDERBEANTYPE s) {
        //保存数据到成员变量
        mData = s;
        //刷新holderView
        refreshHolderView(s);
    }

    //绑定视图
    public abstract void refreshHolderView(HOLDERBEANTYPE data);
}
