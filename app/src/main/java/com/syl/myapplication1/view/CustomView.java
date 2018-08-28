package com.syl.myapplication1.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bright on 2018/7/16.
 *
 * @Describe 自定义图形
 * @Called
 */

public class CustomView extends View {
    private Bitmap mBitmap;
    private Matrix matrix;
    public CustomView(Context context) {
        super(context);
        matrix = new Matrix();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        matrix = new Matrix();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        invalidate();
    }
    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, matrix, null);
        }
    }

    //旋转
    public void rotate(float degree) {
        if (mBitmap != null) {
            matrix.preRotate(degree, mBitmap.getWidth() / 2,
                    mBitmap.getHeight() / 2);
            invalidate();
        }

    }
    //平移
    public void translate(float dx, float dy) {
        if (mBitmap != null) {
            matrix.postTranslate(dx, dy);
            invalidate();
        }
    }
    //缩放
    public void scale(float sx, float sy) {
        if (mBitmap != null) {
            matrix.postScale(sx, sy);
            invalidate();
        }
    }
    //镜像（相当于是照镜子里的自己）
    public void mirror() {
        if (mBitmap != null) {
            matrix.postScale(-1, 1);
            matrix.postTranslate(mBitmap.getWidth(), 0);
            invalidate();
        }
    }
    //倒影
    public void shadow() {
        if (mBitmap != null) {
            matrix.postScale(1, -1);
            matrix.postTranslate(0, mBitmap.getHeight());
            invalidate();
        }
    }
    //倾斜
    public void skew(float kx, float ky){
        if (mBitmap != null) {
            matrix.postSkew(kx, ky);
            invalidate();
        }
    }
}
