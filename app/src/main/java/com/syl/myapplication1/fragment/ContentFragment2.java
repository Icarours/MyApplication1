package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.ContentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 主菜单(底部菜单)对应的第二个按钮
 * @Called
 */

public class ContentFragment2 extends BaseFragment implements View.OnClickListener {
    private static final int BTN_LV_DEMO1 = 1;//ListView
    private static final int BTN_GLIDE = 2;//Glide框架
    private static final int BTN_PICASSO = 3;//picasso
    private static final int BTN_FRESCO = 4;//fresco
    private static final int BTN_URL_IMAGE_LOADER = 5;//url_image_loader
    private static final int BTN_ARC = 6;//自定义view,圆形统计图
    private static final int BTN_SHARE = 7;//mob sharedSDK 分享
    private static final int BTN_JPUSH = 8;//极光推送
    private static final int BTN_RV = 9;//recycleView
    private static final int BTN_RV2 = 10;//recycleView2
    private static final int BTN_RX_EASY_HTTP = 11;//RxEasyHttp
    private static final int BTN_RV3 = 12;//recycleView3
    private static final int BTN_VP_TAB_FM = 13;//ViewPager+tabLayout+Fragment
    private static final int BTN_RECORDER = 14;//录音
    private static final int BTN_RECORDER2 = 15;//录音2
    @Bind(R.id.btn_lv_demo1)
    Button mBtnLvDemo1;
    @Bind(R.id.btn_glide)
    Button mBtnGlide;
    @Bind(R.id.btn_picasso)
    Button mBtnPicasso;
    @Bind(R.id.btn_fresco)
    Button mBtnFresco;
    @Bind(R.id.btn_url_image_loader)
    Button mBtnUrlImageLoader;
    @Bind(R.id.btn_arc)
    Button mBtnArc;
    @Bind(R.id.btn_share)
    Button mBtnShare;
    @Bind(R.id.btn_jpush)
    Button mBtnJpush;
    @Bind(R.id.btn_rv)
    Button mBtnRv;
    @Bind(R.id.btn_rv2)
    Button mBtnRv2;
    @Bind(R.id.btn_rx_easy_http)
    Button mBtnRxEasyHttp;
    @Bind(R.id.btn_rv3)
    Button mBtnRv3;
    @Bind(R.id.btn_vp_tab_fm)
    Button mBtnVpTabFm;
    @Bind(R.id.btn_recorder)
    Button mBtnRecorder;
    @Bind(R.id.btn_recorder2)
    Button mBtnRecorder2;
    private View mRootView;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
    }

    @Override
    public View initView() {
        mRootView = View.inflate(getActivity(), R.layout.fragment_content2, null);
        ButterKnife.bind(this, mRootView);
        mBtnLvDemo1.setOnClickListener(this);
        mBtnGlide.setOnClickListener(this);
        mBtnPicasso.setOnClickListener(this);
        mBtnFresco.setOnClickListener(this);
        mBtnUrlImageLoader.setOnClickListener(this);
        mBtnArc.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnJpush.setOnClickListener(this);
        mBtnRv.setOnClickListener(this);
        mBtnRv2.setOnClickListener(this);
        mBtnRxEasyHttp.setOnClickListener(this);
        mBtnRv3.setOnClickListener(this);
        mBtnVpTabFm.setOnClickListener(this);
        mBtnRecorder.setOnClickListener(this);
        mBtnRecorder2.setOnClickListener(this);
        return mRootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        switch (v.getId()) {
            case R.id.btn_lv_demo1:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_LV_DEMO1);
                break;
            case R.id.btn_glide:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_GLIDE);
                break;
            case R.id.btn_picasso:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_PICASSO);
                break;
            case R.id.btn_fresco:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_FRESCO);
                break;
            case R.id.btn_url_image_loader:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_URL_IMAGE_LOADER);
                break;
            case R.id.btn_arc:
                intent.putExtra("btn_code", BTN_ARC);
                break;
            case R.id.btn_share:
                intent.putExtra("btn_code", BTN_SHARE);
                break;
            case R.id.btn_jpush:
                intent.putExtra("btn_code", BTN_JPUSH);
                break;
            case R.id.btn_rv:
                intent.putExtra("btn_code", BTN_RV);
                break;
            case R.id.btn_rv2:
                intent.putExtra("btn_code", BTN_RV2);
                break;
            case R.id.btn_rx_easy_http:
                intent.putExtra("btn_code", BTN_RX_EASY_HTTP);
                break;
            case R.id.btn_rv3:
                intent.putExtra("btn_code", BTN_RV3);
                break;
            case R.id.btn_vp_tab_fm:
                intent.putExtra("btn_code", BTN_VP_TAB_FM);
                break;
            case R.id.btn_recorder:
                intent.putExtra("btn_code", BTN_RECORDER);
                break;
            case R.id.btn_recorder2:
                intent.putExtra("btn_code", BTN_RECORDER2);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
