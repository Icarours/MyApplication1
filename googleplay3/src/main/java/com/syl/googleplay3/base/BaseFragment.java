package com.syl.googleplay3.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.googleplay3.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe
 *  抽取基类的好处:
 * 1.优化代码,放置共有的属性和方法
 * 2.不用关心Activity和Fragment相关的生命周期的方法
 * 3.控制哪些方法必须实现,哪些方法选择实现
 * @Called
 */

public abstract class BaseFragment extends Fragment {

    public LoadingPager mLoadingPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadingPager==null){
            /**
             * @return
             * @des 初始化加载成功视图
             * @des 1.决定成功视图长什么样子
             * @des 2.为成功视图绑定数据
             * @called 1.外面触发加载数据, 而且数据加载成功
             * @called 2.必须实现, 但不知道具体实现, 交给子类去实现, 定义为抽象方法
             */
            //将初始化成功视图的方法转移到BaseFragment类
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                /**
                 * @return
                 * @dec 在子线程中加载数据, 必须实现, 但是不知道具体实现, 交给子类实现, 将initData定义为抽象方法
                 * @called 外界需要加载数据时调用了triggerLoadData()方法
                 */
                @Override
                public LoadResult initData() {//将初始化数据的方法转移到BaseFragment类
                    return BaseFragment.this.initData();
                }
                /**
                 * @return
                 * @des 初始化加载成功视图
                 * @des 1.决定成功视图长什么样子
                 * @des 2.为成功视图绑定数据
                 * @called 1.外面触发加载数据, 而且数据加载成功
                 * @called 2.必须实现, 但不知道具体实现, 交给子类去实现, 定义为抽象方法
                 */
                @Override
                public View initSuccessView() {//将初始化成功视图的方法转移到BaseFragment类
                    return BaseFragment.this.initSuccessView();
                }
            };
        }
        //假设在此处触发加载,
        mLoadingPager.triggerLoadData();
        return mLoadingPager;
    }
    /**
     * @return
     * @des 初始化加载成功视图
     * @des 1.决定成功视图长什么样子
     * @des 2.为成功视图绑定数据
     * @called 1.外面触发加载数据, 而且数据加载成功
     * @called 2.必须实现, 但不知道具体实现, 交给子类去实现, 定义为抽象方法
     */
    public abstract View initSuccessView();

    /**
     * @return
     * @dec 在子线程中加载数据, 必须实现, 但是不知道具体实现, 交给子类实现, 将initData定义为抽象方法
     * @called 外界需要加载数据时调用了triggerLoadData()方法
     */
    public abstract LoadingPager.LoadResult initData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public LoadingPager.LoadResult checkResData(Object resObj){
        if (resObj==null){
            return LoadingPager.LoadResult.EMPTY;
        }
        //List
        if (resObj instanceof List){
            if (((List) resObj).size()==0){
                 return LoadingPager.LoadResult.EMPTY;
            }
        }
        //Map
        if (resObj instanceof Map){
            if (((Map) resObj).size()==0){
                return LoadingPager.LoadResult.EMPTY;
            }
        }
        return LoadingPager.LoadResult.SUCCESS;
    }
       /*
     页面分析(Fragment分析)
     视图展示一些特性
     1.视图情况有四种,而且同一时刻,只能是4种情况中的一种
        coni state_loading

        coni state_success
        coni state_error
        coni state_empty
        curState = state_loading
        时刻记录curState状态变化,然后更新ui

    2.加载中的视图,错误视图,空视图属于静态视图,一个应用基本就早已经固定好了,其中只需要关心成功视图视图的变化就可以
    3.针对成功视图分析
        0.至少状态首先应该切换到state_success
        1.决定成功视图长什么样子
        2.得到数据/加载数据
        3.为成功视图绑定数据
     */

    /*
    页面分析(Fragment分析)
    数据加载
        0.触发加载
            下拉刷新
            上滑加载更多
            点击重试
            一进入页面就里面进行加载
        1.异步加载
        2.得到数据,处理数据,刷新ui

     */
}
