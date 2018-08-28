package com.syl.myapplication1.fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.syl.myapplication1.R;
import com.syl.myapplication1.TextAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe
 * listView+Banner(轮播图框架)
 * @Called
 */

public class LvDemoFragment extends BaseFragment implements OnBannerListener {
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private View mRootView;
    private ListView mLv;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 200; i++) {
            mList.add("test---"+i);
        }
    }

    @Override
    public View initView() {
        mRootView = View.inflate(getActivity(), R.layout.fragment_lv_demo, null);
        mLv = mRootView.findViewById(R.id.lv);
        TextAdapter adapter = new TextAdapter(mList, getActivity());
        mLv.setAdapter(adapter);
//        FrameLayout frameLayout = new FrameLayout(getActivity());
        banner = (Banner) mRootView.findViewById(R.id.banner);
//        banner= new Banner(getActivity());
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        this.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        this.banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        this.banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        this.banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        this.banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        this.banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        this.banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        this.banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
//        frameLayout.addView(banner);
        return mRootView;
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第" + position + "张轮播图");
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
