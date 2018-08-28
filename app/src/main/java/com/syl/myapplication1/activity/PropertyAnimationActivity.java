package com.syl.myapplication1.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 简单的属性动画,AnimatorSet,ValueAnimator,ObjectAnimator,动画集合,ObjectAnimator 是ValueAnimator的子类
 */
public class PropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PropertyAnimationActivity.class.getSimpleName();
    @Bind(R.id.btn_translate)
    Button mBtnTranslate;
    @Bind(R.id.btn_rotate)
    Button mBtnRotate;
    @Bind(R.id.btn_scale_x)
    Button mBtnScale;
    @Bind(R.id.btn_alpha)
    Button mBtnAlpha;
    @Bind(R.id.btn_target)
    Button mBtnTarget;//动画作用的目标对象
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.btn_scale_y)
    Button mBtnScaleY;
    @Bind(R.id.btn_property_set)
    Button mBtnPropertySet;
    @Bind(R.id.btn_property_set2)
    Button mBtnPropertySet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animationtivity);
        ButterKnife.bind(this);
        mBtnTranslate.setOnClickListener(this);
        mBtnRotate.setOnClickListener(this);
        mBtnScale.setOnClickListener(this);
        mBtnAlpha.setOnClickListener(this);
        mBtnScaleY.setOnClickListener(this);
        mBtnPropertySet.setOnClickListener(this);
        mBtnPropertySet2.setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("简单的属性动画,AnimatorSet,ValueAnimator,ObjectAnimator,动画集合,ObjectAnimator 是ValueAnimator的子类");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate:
                btnTranslate();
                break;
            case R.id.btn_rotate:
                btnRotate();
                break;
            case R.id.btn_scale_x:
                btnScaleX();
                break;
            case R.id.btn_alpha:
                btnAlpha();
                break;
            case R.id.btn_scale_y:
                btnScaleY();
                break;
            case R.id.btn_property_set:
                btnPropertySet();
                break;
            case R.id.btn_property_set2:
                btnPropertySet2();
                break;
            default:
                break;
        }
    }

    //从xml文件中加载动画集合
    private void btnPropertySet2() {
        // 创建组合动画对象  &  加载XML动画
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_animation);
        // 设置动画作用对象
        animator.setTarget(mBtnTarget);
        // 启动动画
        animator.start();
    }

    private void btnPropertySet() {
        // 步骤1：设置需要组合的动画效果
        ObjectAnimator translation = ObjectAnimator.ofFloat(mBtnTarget, "translationX", mBtnTarget.getTranslationX(), 300, mBtnTarget.getTranslationX());
        // 平移动画
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mBtnTarget, "rotation", 0f, 360f);
        // 旋转动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mBtnTarget, "alpha", 1f, 0f, 1f);
        // 透明度动画

        // 步骤2：创建组合动画的对象
        AnimatorSet animSet = new AnimatorSet();

        // 步骤3：根据需求组合动画
        animSet.play(translation).with(rotate).before(alpha);
        /*ArrayList<Animator> list = new ArrayList<>();
        list.add(translation);
        list.add(rotate);
        list.add(alpha);
        animSet.playSequentially(list);
        animSet.setDuration(5000);*/

        // 步骤4：启动动画
        animSet.start();
    }

    private void btnScaleY() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtnTarget, "scaleY", 1f, 3f, 1f)
                .setDuration(2000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void btnTranslate() {
        // ValueAnimator.ofFloat(values...)里面是一系列的int值,默认第一个和最后一个分别是初始值和最终值,中间可以有大量的过渡值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mBtnTarget.getLayoutParams().width, 400, mBtnTarget.getLayoutParams().width);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(2000);//持续时间
        valueAnimator.setStartDelay(500);//延迟启动
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mBtnTarget.getLayoutParams();//获得mBtnTarget的参数
                layoutParams.width = currentValue;//对mBtnTarget长度进行赋值
                System.out.println("currentValue----------" + currentValue);
                Log.d(TAG, "currentValue==" + currentValue);
                //刷新视图
                mBtnTarget.requestLayout();
            }
        });
        valueAnimator.start();//开启动画
    }

    private void btnRotate() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtnTarget, "rotation", 0f, 360f)
                .setDuration(2000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void btnScaleX() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtnTarget, "scaleX", 1f, 3f, 1f)
                .setDuration(2000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void btnAlpha() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtnTarget, "alpha", 0.2f, 1f, 0.2f, 1f)
                .setDuration(2000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }
}
