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
    private static final int BTN_POP = 16;//弹出式菜单
    private static final int BTN_MP_ANDROID_CHART = 17;//强大的统计图,自定义控件
    private static final int BTN_DRAWABLE = 18;//Drawable,图形和绘图
    private static final int BTN_GET_DISPLAY_METRIC = 19;//屏幕宽高
    private static final int BTN_MOBILE = 20;//手机参数
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
    @Bind(R.id.btn_pop)
    Button mBtnPop;
    @Bind(R.id.btn_mp_android_chart)
    Button mBtnMpAndroidChart;
    @Bind(R.id.btn_drawable)
    Button mBtnDrawable;
    @Bind(R.id.btn_get_display_metric)
    Button mBtnGetDisplayMetric;
    @Bind(R.id.btn_mobile)
    Button mBtnMobile;
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
        mBtnRecorder.setVisibility(View.GONE);
        mBtnRecorder2.setOnClickListener(this);
        mBtnRecorder2.setVisibility(View.GONE);
        mBtnPop.setOnClickListener(this);
        mBtnMpAndroidChart.setOnClickListener(this);
        mBtnDrawable.setOnClickListener(this);
        mBtnGetDisplayMetric.setOnClickListener(this);
        mBtnMobile.setOnClickListener(this);
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
                intent.putExtra("title", "ListView");
                break;
            case R.id.btn_glide:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_GLIDE);
                intent.putExtra("title", "Glide框架");
                break;
            case R.id.btn_picasso:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_PICASSO);
                intent.putExtra("title", "picasso");
                break;
            case R.id.btn_fresco:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_FRESCO);
                intent.putExtra("title", "fresco");
                break;
            case R.id.btn_url_image_loader:
                //带过去一个变量,用来确定究竟应该加载那个Fragment
                intent.putExtra("btn_code", BTN_URL_IMAGE_LOADER);
                intent.putExtra("title", "url_image_loader");
                break;
            case R.id.btn_arc:
                intent.putExtra("btn_code", BTN_ARC);
                intent.putExtra("title", "自定义view,圆形统计图");
                break;
            case R.id.btn_share:
                intent.putExtra("btn_code", BTN_SHARE);
                intent.putExtra("title", "mob sharedSDK 分享");
                break;
            case R.id.btn_jpush:
                intent.putExtra("btn_code", BTN_JPUSH);
                intent.putExtra("title", "极光推送");
                break;
            case R.id.btn_rv:
                intent.putExtra("btn_code", BTN_RV);
                intent.putExtra("title", "recycleView");
                break;
            case R.id.btn_rv2:
                intent.putExtra("btn_code", BTN_RV2);
                intent.putExtra("title", "recycleView2");
                break;
            case R.id.btn_rx_easy_http:
                intent.putExtra("btn_code", BTN_RX_EASY_HTTP);
                intent.putExtra("title", "RxEasyHttp");
                break;
            case R.id.btn_rv3:
                intent.putExtra("btn_code", BTN_RV3);
                intent.putExtra("title", "recycleView3");
                break;
            case R.id.btn_vp_tab_fm:
                intent.putExtra("btn_code", BTN_VP_TAB_FM);
                intent.putExtra("title", "ViewPager+tabLayout+Fragment");
                break;
            case R.id.btn_recorder://功能没有完成,隐藏该按钮
                intent.putExtra("btn_code", BTN_RECORDER);
                intent.putExtra("title", "录音");
                break;
            case R.id.btn_recorder2://功能没有完成,隐藏该按钮
                intent.putExtra("btn_code", BTN_RECORDER2);
                intent.putExtra("title", "录音2");
                break;
            case R.id.btn_pop:
                intent.putExtra("btn_code", BTN_POP);
                intent.putExtra("title", "弹出式菜单");
                break;
            case R.id.btn_mp_android_chart:
                intent.putExtra("btn_code", BTN_MP_ANDROID_CHART);
                intent.putExtra("title", "强大的统计图,自定义控件");
                break;
            case R.id.btn_drawable:
                intent.putExtra("btn_code", BTN_DRAWABLE);
                intent.putExtra("title", "Drawable,图形和绘图");
                break;
            case R.id.btn_get_display_metric:
                intent.putExtra("btn_code", BTN_GET_DISPLAY_METRIC);
                intent.putExtra("title", "屏幕宽高");
                break;
            case R.id.btn_mobile:
                intent.putExtra("btn_code", BTN_MOBILE);
                intent.putExtra("title", "手机参数");
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
