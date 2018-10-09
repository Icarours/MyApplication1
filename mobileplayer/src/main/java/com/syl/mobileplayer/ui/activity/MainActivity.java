package com.syl.mobileplayer.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.syl.mobileplayer.R;
import com.syl.mobileplayer.adapter.MainViewPagerAdapter;
import com.syl.mobileplayer.ui.fragment.AudioFragment;
import com.syl.mobileplayer.ui.fragment.BaseFragment;
import com.syl.mobileplayer.ui.fragment.VideoFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private TextView main_video;
    private TextView main_audio;
    private View main_mark;
    private ViewPager main_viewpager;
    private ArrayList<BaseFragment> fragments;

    @Override
    protected void initData() {
        switchTitle(0);
        //指示器宽度初始化
        int screenW  =getWindowManager().getDefaultDisplay().getWidth();
        main_mark.getLayoutParams().width = screenW/2;
        //view刷新
        main_mark.requestLayout();
    }

    @Override
    protected void initListener() {
        fragments =new ArrayList<>();
        fragments.add(new VideoFragment());
        fragments.add(new AudioFragment());
        //adapter
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        main_viewpager.setAdapter(adapter);
        main_viewpager.setOnPageChangeListener(new MainOnpageChangeListener());
    }

    @Override
    protected void initView() {
        main_video = (TextView) findViewById(R.id.main_video);
        main_audio = (TextView) findViewById(R.id.main_audio);
        main_mark = findViewById(R.id.main_mark);
        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processClick(View v) {

    }
    class MainOnpageChangeListener implements ViewPager.OnPageChangeListener{
        /**
         *
         * @param position  当前viewpager位置
         * @param positionOffset  viewpager滑动占屏幕比值
         * @param positionOffsetPixels  viewpager滑动的像素点
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            变化x = viewpager滑动占屏幕比例*指示器宽度
            float offsetX = (int) (positionOffset*main_mark.getWidth());
//            当前x = position*指示器宽度
            float currentX = position*main_mark.getWidth();
//            指示器位置=当前位置x+变化x
            float endX = currentX+offsetX;
            ViewHelper.setTranslationX(main_mark,endX);
        }
        //viewpager页面选中状态改变
        @Override
        public void onPageSelected(int position) {
            switchTitle(position);
        }
        //页面滑动状态改变执行
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //随着viewpager选中状态改变  title相应改变
    private void switchTitle(int position) {
        int green  = getResources().getColor(R.color.green);
        int halfWhite  = getResources().getColor(R.color.halfwhite);
        if(position==0) {
            //当前position 0 视频  大  绿
            main_video.setTextColor(green);
            main_audio.setTextColor(halfWhite);
            ViewPropertyAnimator.animate(main_video).scaleX(1.2f).scaleY(1.2f);
            ViewPropertyAnimator.animate(main_audio).scaleX(1.0f).scaleY(1.0f);
        }else {
            //当前position 1  音乐 大  绿
            main_video.setTextColor(halfWhite);
            main_audio.setTextColor(green);
            ViewPropertyAnimator.animate(main_audio).scaleX(1.2f).scaleY(1.2f);
            ViewPropertyAnimator.animate(main_video).scaleX(1.0f).scaleY(1.0f);
        }
    }
}
