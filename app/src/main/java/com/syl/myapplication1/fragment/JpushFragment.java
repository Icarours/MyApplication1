package com.syl.myapplication1.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Bright on 2018/8/25.
 *
 * @Describe 极光推送
 * @Called
 */

public class JpushFragment extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        TextView tv = new TextView(getActivity());
        tv.setText("极光推送集成好了之后主要就是等后台推送消息了,目前我就只看了通知和自定义消息两种情况..");
        return tv;
    }
}
