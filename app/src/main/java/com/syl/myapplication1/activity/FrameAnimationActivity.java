package com.syl.myapplication1.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_frame1)
    Button mBtnFrame1;
    @Bind(R.id.btn_frame2)
    Button mBtnFrame2;
    @Bind(R.id.btn_frame3)
    Button mBtnFrame3;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        ButterKnife.bind(this);
        mBtnFrame1.setOnClickListener(this);
        mBtnFrame2.setOnClickListener(this);
        mBtnFrame3.setOnClickListener(this);
        mTvTips.setText("逐帧动画...从xml动画文件中加载,或者使用Java代码实现");
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_frame1:
                btnFrame1();
                break;
            case R.id.btn_frame2:
                btnFrame2();
                break;
            case R.id.btn_frame3:
                btnFrame3();
                break;
            default:
                break;
        }
    }

    private void btnFrame3() {
        //加载xml文件,设置为ImageView的背景
        mIv.setBackgroundResource(R.drawable.frame_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) mIv.getBackground();
        // 设置为循环播放
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void btnFrame2() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img00), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img01), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img02), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img03), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img04), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img05), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img06), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img07), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img08), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img09), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img10), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img11), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img12), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img13), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img14), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img15), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img16), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img17), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img18), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img19), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img20), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img21), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img22), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img23), 50);
        animationDrawable.addFrame(
                getResources().getDrawable(R.drawable.img24), 50);
        // 设置为循环播放
        animationDrawable.setOneShot(false);
        mIv.setBackground(animationDrawable);
        animationDrawable.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void btnFrame1() {
        //从xml文件中加载资源,设置为ImageView的背景
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.frame_anim);
        mIv.setBackground(animationDrawable);
        // 设置为循环播放
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }
}
