package com.syl.googleplay3.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.syl.googleplay3.R;


/**
 * Created by j3767 on 2016/11/30.
 * 自定义控件
 *
 * @Describe 根据加载图片的大小, 动态的设置控件的宽高
 * <p>
 * 已知宽度, 动态计算高度
 * 图片的宽高比 = RatioLayout宽度/RatioLayout高度
 * @Called
 */

public class RatioLayout extends FrameLayout {
    //图片的宽高比
    public float mPicRatio = 1;
    public static final int RELATIVE_WIDTH = 0;
    public static final int RELATIVE_HEIGHT = 1;
    public int mRelative = RELATIVE_WIDTH;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 从属性文件中获取数据
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_pivRatio, 1);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        typedArray.recycle();
    }

    /**
     * getMode的三个返回值:
     * UNSPECIFIED不确定
     * AT_MOST最多
     * EXACTLY精确,确定
     * <p>
     * margin是控件相对于父控件的距离
     * padding是控件相对于孩子的距离
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int count = getChildCount();//获取自身孩子的数量
        //1.线判断mode的样式
        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //已知宽度
        if (selfWidthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH) {//如果selfWidthMode的返回值是确定的
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //2.根据公式,算出应有的高度 --> 图片的宽高比 = RatioLayout宽度/RatioLayout高度
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);

            //计算孩子的宽高
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();
            //测量孩子的宽度和高度
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            //测量孩子的宽高,measureChildren()请求所有的孩子测绘自身
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //3.保存测量的结果,包括孩子的测量结果
            setMeasuredDimension(selfWidth, selfHeight);

            //已知高度
        } else if (selfHeightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) {
            //1.得到自身的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            //2.根据公式计算自身的宽度 --> 图片的宽高比 = RatioLayout宽度/RatioLayout高度
            int selfWidth = (int) (mPicRatio * selfHeight + .5f);

            //计算孩子的宽高
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();
            //测量孩子的宽度和高度
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            //测量孩子的宽高,measureChildren()请求所有的孩子测绘自身
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            //3.保存测量的结果的宽高
            setMeasuredDimension(selfWidth, selfHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
