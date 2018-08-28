package com.syl.googleplay3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syl.googleplay3.R;


/**
 * Created by j3767 on 2016/12/11.
 *
 * @Describe
 * @Called
 */

public class ProgressView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView mTvNote;
    private long mMax = 100;
    private long mProgress;
    private boolean isProgressEnable = true;

    /**
     * 最大进度
     *
     * @param max
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(long progress) {
        mProgress = progress;
        //重绘
        invalidate();
    }

    /**
     * 是否允许进度
     *
     * @param isProgressEnable
     */
    public void setIsProgressEnable(boolean isProgressEnable) {
        this.isProgressEnable = isProgressEnable;
    }

    /**
     * 设置图标
     *
     * @param resId
     */
    public void setIvIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置文本内容
     *
     * @param content
     */
    public void setTvNote(String content) {
        mTvNote.setText(content);
    }

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //挂载对应的xml文件
        View view = View.inflate(context, R.layout.inflate_progressview, this);
        mIvIcon = (ImageView) view.findViewById(R.id.progress_iv_icon);
        mTvNote = (TextView) view.findViewById(R.id.progress_tv_note);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isProgressEnable) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);//绘制边框
            paint.setStrokeWidth(3);
            paint.setAntiAlias(true);//消除锯齿
            paint.setColor(Color.BLUE);
//        canvas.drawText("text", 20, 20, paint);
            float left = mIvIcon.getLeft();
            float top = mIvIcon.getTop();
            float right = mIvIcon.getRight();
            float bottom = mIvIcon.getBottom();
            RectF oval = new RectF(left, top, right, bottom);//限制弧形绘制范围
            float startAngle = -90;//开始角度
            float sweepAngle = mProgress * 1.0f / mMax * 360;//扫过的角度-->动态计算
            boolean useCenter = false;//是否使用圆心
            canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
        }
        super.dispatchDraw(canvas);//绘制图标文本
    }
}
