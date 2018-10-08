package com.syl.myapplication1.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.view.RoundedCornerImageView;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe 自定义控件,圆角矩形
 * @Called
 */
public class RoundedCornerFragment extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RoundedCornerImageView iv = new RoundedCornerImageView(getActivity());
        Bitmap source = BitmapFactory.decodeResource(getResources(), R.mipmap.img2);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImage(source);
        return iv;
    }
}
