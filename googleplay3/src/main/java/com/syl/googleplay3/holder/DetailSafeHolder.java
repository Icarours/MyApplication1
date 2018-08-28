package com.syl.googleplay3.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by j3767 on 2016/12/10.
 *
 * @Describe holder:1.提供视图,2.获取数据,3.视图和数据的绑定
 * 属性动画,控件尺寸的重新测量
 * @Called
 */
public class DetailSafeHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {

    @Bind(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;
    @Bind(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;
    @Bind(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;
    public View mHolderView;
    private boolean isOpen = true;

    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
        ButterKnife.bind(this, mHolderView);
        mHolderView.setOnClickListener(this);
        return mHolderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        List<ItemInfoBean.SafeBean> safeBeans = data.getSafe();
        for (int i = 0; i < safeBeans.size(); i++) {
            ItemInfoBean.SafeBean safeBean = safeBeans.get(i);
            String safeDes = safeBean.getSafeDes();
            int safeDesColor = safeBean.getSafeDesColor();
            String safeDesUrl = safeBean.getSafeDesUrl();
            String safeUrl = safeBean.getSafeUrl();

            //往mAppDetailSafePicContainer添加具体的图标
            ImageView ivIcon = new ImageView(UIUtils.getContext());
            int width = UIUtils.dp2px(45);
            int height = UIUtils.dp2px(20);
            Glide.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + safeUrl).fitCenter().override(width, height).into(ivIcon);
            mAppDetailSafePicContainer.addView(ivIcon);

            //往mAppDetailSafeDesContainer中加入具体内容
            LinearLayout line = new LinearLayout(UIUtils.getContext());

            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            TextView tvDes = new TextView(UIUtils.getContext());

            //设置数据
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + safeDesUrl).into(ivDesIcon);
            tvDes.setText(safeDes);
            if (safeDesColor == 0) {
                //从资源文件中取出颜色值
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }

            int padding = UIUtils.dp2px(4);
            line.setPadding(padding, padding, padding, padding);
            line.addView(ivDesIcon);
            line.addView(tvDes);

            mAppDetailSafeDesContainer.addView(line);
        }
        //初始化的时候,修改高度不需要带动画效果
        changeSafeDesContainerHeight(false);
    }

    @Override
    public void onClick(View v) {
        //点击的时候需要带动画效果
        changeSafeDesContainerHeight(true);
    }

    /**
     * @param isAnimation 是否带动画效果
     * @des 改变SafeDesContainer的控件高度
     */
    private void changeSafeDesContainerHeight(boolean isAnimation) {
        //折叠和展开两种情况,默认折叠
        if (isOpen) {//如果展开,就折叠
            //mAppDetailSafeDesContainer的高度变化,原有高度-->0
            /*------------------ 属性动画start -----------------*/
            int start = mAppDetailSafeDesContainer.getMeasuredHeight();//应有的高度
            int end = 0;//折叠后的高度
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = end;
                mAppDetailSafeDesContainer.setLayoutParams(layoutParams);
            }
        } else {//如果折叠,就展开
            //mAppDetailSafeDesContainer的高度变化,0-->应有高度
            int start = 0;
            /*int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mAppDetailSafeDesContainer.measure(widthMeasureSpec, heightMeasureSpec);*/
            mAppDetailSafeDesContainer.measure(0, 0);
            //getMeasuredHeight()和getHeight()的区别
            int end = mAppDetailSafeDesContainer.getMeasuredHeight();//应有的高度
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = end;
                mAppDetailSafeDesContainer.setLayoutParams(layoutParams);
            }
        }
        isOpen = !isOpen;
        /*------------------ 属性动画end -----------------*/
    }

    /**
     * 属性动画,控制SafeDesContainer的折叠展开和指示箭头的翻转
     *
     * @param start 初始高度
     * @param end   动画结束时的高度
     */
    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = height;
                mAppDetailSafeDesContainer.setLayoutParams(layoutParams);
            }
        });
        //箭头跟着转
        if (isOpen) {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180).start();
        }
    }
}
