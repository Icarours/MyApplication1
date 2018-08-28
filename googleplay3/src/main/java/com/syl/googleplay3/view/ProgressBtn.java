package com.syl.googleplay3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 创建者     伍碧林
 * 创建时间   2016/3/31 08:36
 * 描述	      带有进度效果的一个Button
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-03-31 14:35:19 +0800 (星期四, 31 三月 2016) $
 * 更新描述   ${TODO}
 */
public class ProgressBtn extends Button {
    private long mMax = 100;
    private long mProgress;
    private boolean isProgressEnable = true;
    private Drawable mDrawable;

    /**
     * 设置进度的最大值
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置进度的当前值
     */
    public void setProgress(long progress) {
        mProgress = progress;
        //重新绘制drawable
        invalidate();
    }

    /**
     * 设置是否允许进度
     */
    public void setIsProgressEnable(boolean isProgressEnable) {
        this.isProgressEnable = isProgressEnable;
    }

    public ProgressBtn(Context context) {
        super(context);
    }

    public ProgressBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //        canvas.drawText("gaga", 20, 20, getPaint());
        if (isProgressEnable) {
            if (mDrawable == null) {
                mDrawable = new ColorDrawable(Color.BLUE);
            }
            int left = 0;
            int top = 0;
            int right = (int) (mProgress * 1.0f / mMax * getMeasuredWidth() + .5f);//动态计算
            int bottom = getBottom();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }

        super.onDraw(canvas);
    }
}
