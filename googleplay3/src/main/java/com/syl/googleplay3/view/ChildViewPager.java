package com.syl.googleplay3.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by j3767 on 2016/11/29.
 * 低版本的手机2.3.7会出现ViewPager嵌套滑动冲突的问题,本类ChildViewPager可以解决该问题
 *
 * @Describe ChildViewPager继承ViewPager
 * 构建两个构造方法
 * 复写dispatchTouchEvent()
 * onInterceptTouchEvent()
 * onTouchEvent()
 * @Called
 */

public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {//是否向下分发事件
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//是否拦截事件
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();

                int diffX = (int) (moveX - mDownX + .5f);
                int diffY = (int) (moveY - mDownY + .5f);

                if (Math.abs(diffX) > Math.abs(diffY)) {//如果水平滚动,ChildViewPager自己处理ACTION_MOVE
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {//如果竖直滚动,ParentViewPager处理ACTION_MOVE
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
