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
import com.syl.myapplication1.fragment.ShareFragment;
import com.syl.myapplication1.fragment.UrlImageLoaderFragment;

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
            default:
                break;
        }
        transaction.commit();

    }
}
