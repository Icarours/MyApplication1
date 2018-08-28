package com.syl.myapplication1.fragment;

import android.view.View;
import android.widget.ImageView;

import com.syl.myapplication1.R;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 主菜单(底部菜单)对应的第三个按钮
 * @Called
 */

public class ContentFragment3 extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.img2);
        return imageView;
    }
}
