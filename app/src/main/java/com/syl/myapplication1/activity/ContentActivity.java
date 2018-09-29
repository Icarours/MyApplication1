package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.syl.myapplication1.R;
import com.syl.myapplication1.fragment.ArcFragment;
import com.syl.myapplication1.fragment.FrescoFragment;
import com.syl.myapplication1.fragment.GlideFragment;
import com.syl.myapplication1.fragment.JpushFragment;
import com.syl.myapplication1.fragment.LvDemoFragment;
import com.syl.myapplication1.fragment.PicassoFragment;
import com.syl.myapplication1.fragment.PopFragment;
import com.syl.myapplication1.fragment.Recorder2Fragment;
import com.syl.myapplication1.fragment.RecorderFragment;
import com.syl.myapplication1.fragment.RecycleView2Fragment;
import com.syl.myapplication1.fragment.RecycleView3Fragment;
import com.syl.myapplication1.fragment.RecycleViewFragment;
import com.syl.myapplication1.fragment.RxEasyHttpFragment;
import com.syl.myapplication1.fragment.ShareFragment;
import com.syl.myapplication1.fragment.UrlImageLoaderFragment;
import com.syl.myapplication1.fragment.VpTabFmFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/8/2 13:13
 * desc
 * ContentFragment2对应的界面的具体内容
 */
public class ContentActivity extends AppCompatActivity {

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;
    private int mCurrentIndex;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mCurrentIndex = getIntent().getIntExtra("btn_code", 1);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (mCurrentIndex) {
            case BTN_LV_DEMO1:
                transaction.replace(R.id.fl_content, new LvDemoFragment());
                break;
            case BTN_GLIDE:
                transaction.replace(R.id.fl_content, new GlideFragment());
                break;
            case BTN_PICASSO:
                transaction.replace(R.id.fl_content, new PicassoFragment());
                break;
            case BTN_FRESCO:
                transaction.replace(R.id.fl_content, new FrescoFragment());
                break;
            case BTN_URL_IMAGE_LOADER:
                transaction.replace(R.id.fl_content, new UrlImageLoaderFragment());
                break;
            case BTN_ARC:
                transaction.replace(R.id.fl_content, new ArcFragment());
                break;
            case BTN_SHARE:
                transaction.replace(R.id.fl_content, new ShareFragment());
                break;
            case BTN_JPUSH:
                transaction.replace(R.id.fl_content, new JpushFragment());
                break;
            case BTN_RV:
                transaction.replace(R.id.fl_content, new RecycleViewFragment());
                break;
            case BTN_RV2:
                transaction.replace(R.id.fl_content, new RecycleView2Fragment());
                break;
            case BTN_RX_EASY_HTTP:
                transaction.replace(R.id.fl_content, new RxEasyHttpFragment());
                break;
            case BTN_RV3:
                transaction.replace(R.id.fl_content, new RecycleView3Fragment());
                break;
            case BTN_VP_TAB_FM:
                transaction.replace(R.id.fl_content, new VpTabFmFragment());
                break;
            case BTN_RECORDER:
                transaction.replace(R.id.fl_content, new RecorderFragment());
                break;
            case BTN_RECORDER2:
                transaction.replace(R.id.fl_content, new Recorder2Fragment());
                break;
            case BTN_POP:
                transaction.replace(R.id.fl_content, new PopFragment());
                break;
            default:
                break;
        }
        transaction.commit();

    }
}
