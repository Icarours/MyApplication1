package com.syl.myapplication1.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe 自定义圆角矩形图片
 * @Called
 */
public class RoundedCornerImageView extends AppCompatImageView {

    private Bitmap mImage;
    private float mRadius = 25f;
    private Paint mBitmapPaint;
    private RectF mBounds;

    public RoundedCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundedCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //创建画笔
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建最为绘图边界的矩形
        mBounds = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        int width = 0;
        int imageHeight;//图片内容的大小
        int imageWidth;
        if (mImage == null) {
            imageHeight = imageWidth = 0;
        } else {
            imageHeight = mImage.getHeight();
            imageWidth = mImage.getWidth();
        }
        //获得最佳测量值,并在视图上设置该值
        width = getMeasurement(widthMeasureSpec, imageWidth);
        height = getMeasurement(heightMeasureSpec, imageHeight);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度和高度的辅助方法
     *
     * @param measureSpec
     * @param contentSize
     * @return
     */
    private int getMeasurement(int measureSpec, int contentSize) {
        int spaceSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {//根据测量模式,返回测量结果
            case MeasureSpec.AT_MOST://最大
                return Math.min(spaceSize, contentSize);
            case MeasureSpec.EXACTLY://精确
                return spaceSize;
            case MeasureSpec.UNSPECIFIED://未指定
                return contentSize;
            default:
                return 0;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            //我们要使图片居中,因此在视图改变大小时偏移值
            int imageWidth;
            int imageHeight;
            if (mImage == null) {
                imageHeight = imageWidth = 0;
            } else {
                imageWidth = mImage.getWidth();
                imageHeight = mImage.getHeight();
            }
//            int left = (w - imageWidth) / 7;
//            int top = (h - imageHeight) / 7;
            int left = 0;
            int top = 0;

            //设置边界,以偏移圆角矩形
            mBounds.set(left, top, left + imageWidth, top + imageHeight);
            //偏移着色器,以在矩形内部绘制位图.如果没有此步骤,位图将在视图中的(0,0)处
            if (mBitmapPaint != null) {
                Matrix m = new Matrix();
                m.setTranslate(left, top);
                mBitmapPaint.getShader().getLocalMatrix(m);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //让视图绘制背景等对象
        super.onDraw(canvas);
        //使用计算得出的值绘制图片
        if (mBitmapPaint != null) {
            canvas.drawRoundRect(mBounds, mRadius, mRadius, mBitmapPaint);
        }
    }

    public void setImage(Bitmap bitmap) {
        if (mImage != bitmap) {
            mImage = bitmap;
            if (mImage != null) {
                BitmapShader shader = new BitmapShader(mImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mBitmapPaint.setShader(shader);
            } else {
                mBitmapPaint.setShader(null);
            }
            requestLayout();
        }
    }
}
