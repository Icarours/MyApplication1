package com.syl.myapplication1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe 屏幕宽高
 * @Called
 */
public class DisplayMetricFragment extends BaseFragment {
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_display1)
    TextView mTvDisplay1;
    @Bind(R.id.tv_display2)
    TextView mTvDisplay2;
    @Bind(R.id.tv_display3)
    TextView mTvDisplay3;
    @Bind(R.id.tv_display4)
    TextView mTvDisplay4;

    private int height;
    private int width;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_metric, null);
        ButterKnife.bind(this, rootView);

        mTvContent.setText("获取屏幕宽高");

        getDisplay1();
        getDisplay2();
        getDisplay3();
        getDisplay4();

        return rootView;
    }

    private void getDisplay4() {
        //4、通过类直接取
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.xdpi;
        float height = dm.ydpi;
        mTvDisplay4.setText("通过类直接取 DisplayMetrics width=="+width+"--height=="+height);
    }

    private void getDisplay3() {
        //3、获取屏幕的默认分辨率
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        mTvDisplay3.setText("获取屏幕的默认分辨率 Display width=="+width+"--height=="+height);
    }

    @NonNull
    private void getDisplay2() {
        //2、通过Resources获取
        DisplayMetrics dm = getResources().getDisplayMetrics();
        height = dm.heightPixels;
        width = dm.widthPixels;
        mTvDisplay2.setText("通过Resources获取 DisplayMetrics width=="+width+"--height=="+height);
    }

    private void getDisplay1() {
        //1、通过WindowManager获取
        WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        mTvDisplay1.setText("通过WindowManager获取 DisplayMetrics width=="+width+"--height=="+height);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
