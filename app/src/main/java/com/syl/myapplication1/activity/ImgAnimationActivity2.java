package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * view动画
 */
public class ImgAnimationActivity2 extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_translate)
    Button mBtnTranslate;
    @Bind(R.id.btn_rotate)
    Button mBtnRotate;
    @Bind(R.id.btn_scale_x)
    Button mBtnScale;
    @Bind(R.id.btn_alpha)
    Button mBtnAlpha;
    @Bind(R.id.btn_set)
    Button mBtnSet;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.iv_img)
    ImageView mIvImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_animation2);
        ButterKnife.bind(this);
        mBtnTranslate.setOnClickListener(this);
        mBtnRotate.setOnClickListener(this);
        mBtnScale.setOnClickListener(this);
        mBtnAlpha.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("简单的view动画");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate:
                btnTranslate2();
                break;
            case R.id.btn_scale_x:
                btnScale();
                break;
            case R.id.btn_rotate:
                btnRotate();
                break;
            case R.id.btn_alpha:
                btnAlpha();
                break;
            case R.id.btn_set:
                btnSet();
                break;
            default:
                break;
        }
    }
    /**
     * 动画集合
     */
    private void btnSet() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, -1, 1, -1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);//持续时间
        scaleAnimation.setRepeatMode(Animation.REVERSE);//重复模式
        scaleAnimation.setRepeatCount(Animation.INFINITE);//重复次数

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0.5f);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);

        mIvImg.startAnimation(animationSet);//启动动画集合
    }

    /**
     * 平移动画
     */
    private void btnTranslate() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 0);
        translateAnimation.setDuration(2000);//持续时间
        translateAnimation.setRepeatCount(Animation.INFINITE);//重复次数
        translateAnimation.setRepeatMode(Animation.REVERSE);//重复模式
        mIvImg.startAnimation(translateAnimation);//启动动画
    }

    /**
     * 从xml文件中加载动画
     * xml文件中的动画属性as暂时没有提示,还是直接在代码中设置动画属性吧
     */
    private void btnTranslate2() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tr);
        mIvImg.startAnimation(animation);
    }

    /**
     * 缩放
     */
    private void btnScale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 50, 0, 50);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setDuration(2000);
        mIvImg.startAnimation(scaleAnimation);
    }

    /**
     * 旋转
     */
    private void btnRotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setDuration(2000);
        mIvImg.startAnimation(rotateAnimation);
    }

    /**
     * 透明度变化
     */
    private void btnAlpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.2f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(2000);
        mIvImg.startAnimation(alphaAnimation);
    }
}
