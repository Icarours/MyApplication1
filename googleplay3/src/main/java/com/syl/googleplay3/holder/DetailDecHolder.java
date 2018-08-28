package com.syl.googleplay3.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by j3767 on 2016/12/10.
 *
 * @Describe
 * @Called
 */
public class DetailDecHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {

    @Bind(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;
    @Bind(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;
    @Bind(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;
    private boolean isOpen = true;
    private ItemInfoBean mItemInfoBean;
    private int mInitHeight;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        ButterKnife.bind(this, holderView);
        holderView.setOnClickListener(this);
        return holderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        mItemInfoBean = data;
        mAppDetailDesTvDes.setText(data.getDes());
        mAppDetailDesTvAuthor.setText(data.getAuthor());
        //View的绘制需要时间,只有当view绘制结束之后才能对其进行操作
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //得到mAppDetailDesTvDes应有的高度
                //默认折叠
                changeTvDesHeight(false);
                //用过一次之后移除
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        changeTvDesHeight(true);
    }

    /**
     * @param isAnimation
     * @des 属性动画, 控制mAppDetailDesTvDes的折叠展开
     */
    private void changeTvDesHeight(boolean isAnimation) {
        if (isOpen) {
            //折叠    属性动画,应有文本的高度-->7行文本的高度
            int start = mAppDetailDesTvDes.getMeasuredHeight();
            mInitHeight = start;
//            Toast.makeText(UIUtils.getContext(), "start---" + start, Toast.LENGTH_SHORT).show();
            int end = getShortHeight(7, mItemInfoBean.getDes());

            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mAppDetailDesTvDes.setHeight(end);
            }
        } else {
            //展开    属性动画,7行文本的高度-->应有文本的高度
            int start = getShortHeight(7, mItemInfoBean.getDes());
            int end = mInitHeight;
//            Toast.makeText(UIUtils.getContext(), "start==" + start, Toast.LENGTH_SHORT).show();

            doAnimation(start, end);
        }
        isOpen = !isOpen;
    }

    /**
     * @param start
     * @param end
     * @des 属性动画
     */
    private void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        animator.start();
        //箭头跟着旋转
        if (isOpen) {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180).start();
        }
        //监听动画执行完成
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //找到外层的ScrollView
                ViewParent parent = mAppDetailDesTvDes.getParent();//父容器
                while (true) {
                    parent = parent.getParent();
                    if (parent instanceof ScrollView) {
                      ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                    if (parent == null) {
                        break;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * @param lineNum 行数
     * @param content TextView中的内容
     * @return
     * @des 获取TextView中指定行数的高度
     */
    public int getShortHeight(int lineNum, String content) {
        TextView tempTv = new TextView(UIUtils.getContext());
        tempTv.setLines(lineNum);
        tempTv.setText(content);

        tempTv.measure(0, 0);
        int measuredHeight = tempTv.getMeasuredHeight();
        return measuredHeight;
    }
}
