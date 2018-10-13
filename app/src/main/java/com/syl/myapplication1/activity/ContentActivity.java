package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.fragment.ArcFragment;
import com.syl.myapplication1.fragment.DisplayMetricFragment;
import com.syl.myapplication1.fragment.DrawableFragment;
import com.syl.myapplication1.fragment.FrescoFragment;
import com.syl.myapplication1.fragment.GlideFragment;
import com.syl.myapplication1.fragment.JpushFragment;
import com.syl.myapplication1.fragment.LvDemoFragment;
import com.syl.myapplication1.fragment.MPAndroidChartFragment;
import com.syl.myapplication1.fragment.MobileFragment;
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
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
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
    private static final int BTN_MP_ANDROID_CHART = 17;//强大的统计图,自定义控件
    private static final int BTN_DRAWABLE = 18;//Drawable,图形和绘图
    private static final int BTN_GET_DISPLAY_METRIC = 19;//屏幕宽高
    private static final int BTN_MOBILE = 20;//手机参数
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mCurrentIndex = getIntent().getIntExtra("btn_code", 1);
        mTitle = getIntent().getStringExtra("title");
        ButterKnife.bind(this);
        initToolBar();
        initView();
    }

    private void initToolBar() {
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(ContentActivity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(ContentActivity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(ContentActivity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(ContentActivity.this, "action_item_two", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
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
            case BTN_MP_ANDROID_CHART:
                transaction.replace(R.id.fl_content, new MPAndroidChartFragment());
                break;
            case BTN_DRAWABLE:
                transaction.replace(R.id.fl_content, new DrawableFragment());
                break;
            case BTN_GET_DISPLAY_METRIC:
                transaction.replace(R.id.fl_content, new DisplayMetricFragment());
                break;
            case BTN_MOBILE:
                transaction.replace(R.id.fl_content, new MobileFragment());
                break;
            default:
                break;
        }
        transaction.commit();

    }
}
