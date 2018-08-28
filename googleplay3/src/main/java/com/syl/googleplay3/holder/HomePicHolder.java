package com.syl.googleplay3.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public class HomePicHolder extends BaseHolder<List<String>> {
    @Bind(R.id.item_home_picture_pager)
    ViewPager mItemHomePicturePager;
    @Bind(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPictures;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(List<String> data) {
        //保存数据到成员变量
        mPictures = data;
        //view+data
        mItemHomePicturePager.setAdapter(new HomePageAdapter());

        //设置指示器
        for (int i = 0; i < mPictures.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            ivIndicator.setImageResource(R.drawable.indicator_normal);//设置未被选中时的图片
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);//设置选中时的图片
            }
            int width = UIUtils.dp2px(6);
            int height = UIUtils.dp2px(6);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            mItemHomePictureContainerIndicator.addView(ivIndicator, params);
        }
        //设置ViewPager的监听
        mItemHomePicturePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 选中ViewPager中图片时的操作,如果图片被选中,那么图片对应的索引指示器也应该相应的更新
             * @param position 图片的索引
             */
            @Override
            public void onPageSelected(int position) {
                position = position % mPictures.size();
                for (int i = 0; i < mPictures.size(); i++) {
                    //找出指示器容器中所有的孩子,并将其背景图片设置为未选中时的状态
                    ImageView indicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
                    indicator.setImageResource(R.drawable.indicator_normal);
                    if (i == position) {//被选中
                        indicator.setImageResource(R.drawable.indicator_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //左右无限轮播
        //初始化ViewPager首次选中,是在第一个ivIndicator和第一张图片
        int diff = Integer.MAX_VALUE % mPictures.size();//余数
        int index = Integer.MAX_VALUE / 2 - diff;
        mItemHomePicturePager.setCurrentItem(index);

        //自动轮播
        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.start();

        //按下去的时候,停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        autoScrollTask.start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    /**
     * author   Bright
     * date     2018/8/2 13:59
     * desc
     * 无限轮播
     */
    private class AutoScrollTask implements Runnable {
        //开始滚动
        public void start() {
            MyApplication.getMainThreadHandler().postDelayed(this, 3000);
        }

        //结束滚动
        public void stop() {
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }

        /**
         * ViewPager自动轮播
         */
        @Override
        public void run() {
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem);
            start();
        }
    }
    /**
     * author   Bright
     * date     2018/8/2 13:36
     * desc
     * 数据绑定
     * ViewPagerAdapter(HomePagerAdapter)的标准写法
     * 继承PagerAdapter
     * 复写四个方法
     * getCount()
     * isViewFromObject()
     * instantiateItem()
     * destroyItem()
     * 无限轮播,将getCount()中的返回值改为Integer.MAX_VALUE
     */
    class HomePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPictures != null) {//要设置无线轮播,所以getCount()的返回值不能使mPictures集合中元素的数量
//                return mPictures.size();
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            position = position % mPictures.size();
            ImageView iv = new ImageView(UIUtils.getContext());
            //图片的设置
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //图片的加载
            Glide.with(UIUtils.getContext())
                    .load(Constants.URLS.IMAGEBASEURL + mPictures.get(position))
                    .into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
