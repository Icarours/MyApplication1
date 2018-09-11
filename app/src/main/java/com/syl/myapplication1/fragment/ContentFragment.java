package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.view.View;

import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.AidlActivity;
import com.syl.myapplication1.activity.AliPayActivity;
import com.syl.myapplication1.activity.BaiduPlayerActivity;
import com.syl.myapplication1.activity.BanZhengActivity;
import com.syl.myapplication1.activity.BankActivity;
import com.syl.myapplication1.activity.ContactsActivity;
import com.syl.myapplication1.activity.FileActivity;
import com.syl.myapplication1.activity.FrameAnimationActivity;
import com.syl.myapplication1.activity.HintIntentActivity;
import com.syl.myapplication1.activity.ImgActivity;
import com.syl.myapplication1.activity.ImgAnimationActivity;
import com.syl.myapplication1.activity.ImgAnimationActivity2;
import com.syl.myapplication1.activity.MusicActivity;
import com.syl.myapplication1.activity.NewsActivity;
import com.syl.myapplication1.activity.NotificationActivity;
import com.syl.myapplication1.activity.ObserverActivity;
import com.syl.myapplication1.activity.Photo2Activity;
import com.syl.myapplication1.activity.PhotoActivity;
import com.syl.myapplication1.activity.PhotoVideoActivity;
import com.syl.myapplication1.activity.PickViewActivity;
import com.syl.myapplication1.activity.PropertyAnimationActivity;
import com.syl.myapplication1.activity.QQActivity;
import com.syl.myapplication1.activity.RV2Activity;
import com.syl.myapplication1.activity.RV3Activity;
import com.syl.myapplication1.activity.RVActivity;
import com.syl.myapplication1.activity.ReceiverActivity;
import com.syl.myapplication1.activity.ServiceActivity;
import com.syl.myapplication1.activity.SmsActivity;
import com.syl.myapplication1.activity.SqlTransactionActivity;
import com.syl.myapplication1.activity.StudentActivity;
import com.syl.myapplication1.activity.TuyaActivity;
import com.syl.myapplication1.activity.TxtActivity;
import com.syl.myapplication1.activity.UserXmlActivity;
import com.syl.myapplication1.activity.VitamioActivity;
import com.syl.myapplication1.activity.WeatherForecastActivity;

/**
 * Created by Bright on 2018/7/2.
 *
 * @Describe 展示主题内容的布局, 主菜单(底部菜单)对应的第一个按钮
 * @Called
 */

public class ContentFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        //这儿的Context必须使用getActivity(),不能使用父类的mContext,否则会造成空指针异常
        View contentRootView = View.inflate(getActivity(), R.layout.fragment_content, null);
        contentRootView.findViewById(R.id.btn_qq).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_save_xml).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_student).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_sql).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_load_img_net).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_txt).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_weather_forecast).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_news).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_file).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_hint_intent).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_receiver).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_service).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_ban_zheng).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_alipay).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_aidl).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_bank).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_sms).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_notification).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_observer).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_contacts).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_img_animation).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_img_animation2).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_vitamio).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_tuya).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_music).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_property_animation).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_frame_animation).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_baidu_player).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_rv).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_rv2).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_rv3).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_photo).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_photo2).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_photo_video).setOnClickListener(this);
        contentRootView.findViewById(R.id.btn_pick_view).setOnClickListener(this);
        return contentRootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_qq:
                intent.setClass(getActivity(), QQActivity.class);
                break;
            case R.id.btn_save_xml:
                intent.setClass(getActivity(), UserXmlActivity.class);
                break;
            case R.id.btn_student:
                intent.setClass(getActivity(), StudentActivity.class);
                break;
            case R.id.btn_sql:
                intent.setClass(getActivity(), SqlTransactionActivity.class);
                break;
            case R.id.btn_load_img_net:
                intent.setClass(getActivity(), ImgActivity.class);
                break;
            case R.id.btn_txt:
                intent.setClass(getActivity(), TxtActivity.class);
                break;
            case R.id.btn_weather_forecast:
                intent.setClass(getActivity(), WeatherForecastActivity.class);
                break;
            case R.id.btn_news:
                intent.setClass(getActivity(), NewsActivity.class);
                break;
            case R.id.btn_file:
                intent.setClass(getActivity(), FileActivity.class);
                break;
            case R.id.btn_hint_intent:
                intent.setClass(getActivity(), HintIntentActivity.class);
                break;
            case R.id.btn_receiver:
                intent.setClass(getActivity(), ReceiverActivity.class);
                break;
            case R.id.btn_service:
                intent.setClass(getActivity(), ServiceActivity.class);
                break;
            case R.id.btn_ban_zheng:
                intent.setClass(getActivity(), BanZhengActivity.class);
                break;
            case R.id.btn_alipay:
                intent.setClass(getActivity(), AliPayActivity.class);
                break;
            case R.id.btn_aidl:
                intent.setClass(getActivity(), AidlActivity.class);
                break;
            case R.id.btn_bank:
                intent.setClass(getActivity(), BankActivity.class);
                break;
            case R.id.btn_sms:
                intent.setClass(getActivity(), SmsActivity.class);
                break;
            case R.id.btn_notification:
                intent.setClass(getActivity(), NotificationActivity.class);
                break;
            case R.id.btn_observer:
                intent.setClass(getActivity(), ObserverActivity.class);
                break;
            case R.id.btn_contacts:
                intent.setClass(getActivity(), ContactsActivity.class);
                break;
            case R.id.btn_img_animation:
                intent.setClass(getActivity(), ImgAnimationActivity.class);
                break;
            case R.id.btn_img_animation2:
                intent.setClass(getActivity(), ImgAnimationActivity2.class);
                break;
            case R.id.btn_vitamio:
                intent.setClass(getActivity(), VitamioActivity.class);
                break;
            case R.id.btn_tuya:
                intent.setClass(getActivity(), TuyaActivity.class);
                break;
            case R.id.btn_music:
                intent.setClass(getActivity(), MusicActivity.class);
                break;
            case R.id.btn_property_animation:
                intent.setClass(getActivity(), PropertyAnimationActivity.class);
                break;
            case R.id.btn_frame_animation:
                intent.setClass(getActivity(), FrameAnimationActivity.class);
                break;
            case R.id.btn_baidu_player:
                intent.setClass(getActivity(), BaiduPlayerActivity.class);
                break;
            case R.id.btn_rv:
                intent.setClass(getActivity(), RVActivity.class);
                break;
            case R.id.btn_rv2:
                intent.setClass(getActivity(), RV2Activity.class);
                break;
            case R.id.btn_rv3:
                intent.setClass(getActivity(), RV3Activity.class);
                break;
            case R.id.btn_photo:
                intent.setClass(getActivity(), PhotoActivity.class);
                break;
            case R.id.btn_photo2:
                intent.setClass(getActivity(), Photo2Activity.class);
                break;
            case R.id.btn_photo_video:
                intent.setClass(getActivity(), PhotoVideoActivity.class);
                break;
            case R.id.btn_pick_view:
                intent.setClass(getActivity(), PickViewActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
