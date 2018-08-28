package com.syl.googleplay3.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.syl.googleplay3.R;
import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.factory.ThreadPoolProxyFactory;
import com.syl.googleplay3.utils.LogUtils;
import com.syl.googleplay3.utils.UIUtils;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe
 * 1.提供视图-->加载中的视图,成功视图,错误视图,空视图4种中的一种
2.加载数据-->因为其中的成功视图需要数据
3.进行数据和视图的绑定-->需要把数据和成功视图进行绑定
 * @Called
 */

public abstract class LoadingPager extends FrameLayout {
    public static final int STATE_LOADING = 0;//应该显示 加载中的视图
    public static final int STATE_SUCCESS = 1;//应该显示 成功的视图
    public static final int STATE_ERROR = 2;//应该显示 错误的视图
    public static final int STATE_EMPTY = 3;//应该显示 空的视图
    private static final java.lang.String TAG = LoadingPager.class.getSimpleName();

    public int mCurState = STATE_LOADING;//默认是加载中的状态
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(@NonNull Context context) {
        super(context);//LoadingPager是一个容器,没有使用加载xml文件的构造方法,只能使用代码进行初始化
        //初始化静态视图
        initCommonView();
    }

    private void initCommonView() {
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mLoadingView);
        this.addView(mErrorView);
        this.addView(mEmptyView);
        //根据视图状态加载UI
        refreshUIByState();
    }

    /**
     * @des 根据当前状态刷新UI, 控制几种视图的显示隐藏
     * @called 1.LoadingPager创建的时候调用
     * @called 2.外界触发加载了数据, 数据真正开始加载之前
     * @called 3.外界触发加载了, 而且加载完成了
     */
    private void refreshUIByState() {
        mLoadingView.setVisibility(mCurState==STATE_LOADING?View.VISIBLE:View.GONE);
        mErrorView.setVisibility(mCurState==STATE_ERROR?View.VISIBLE:View.GONE);
        mEmptyView.setVisibility(mCurState==STATE_EMPTY?View.VISIBLE:View.GONE);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果数据加载失败,点击重新触发加载数据
                triggerLoadData();
            }
        });
        //如果视图加载状态为成功,且成功视图为空
        if (mCurState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();//创建成功视图
            this.addView(mSuccessView);//添加成功视图到LoadingPager自身
        }
        //控制成功视图的显隐
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //触发加载数据,外界需要加载数据时候调用:1.刚进入界面,界面初始化的时候,2.数据记载失败后,点击重新加载数据
    public void triggerLoadData(){
        //如果当前状态不是成功状态,且mLoadDataTask为null,就开始加载数据
        if (mCurState != STATE_SUCCESS && mLoadDataTask == null) {
            LogUtils.sf("triggerLoadData");
            //每次加载数据之前,重置加载状态
            mCurState = STATE_LOADING;
            refreshUIByState();//刷新UI
            //在子线程中加载数据
            mLoadDataTask = new LoadDataTask();
//            new Thread(mLoadDataTask).start();
            //使用线程池代理工厂类来处理任务
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);
        }
    }
    class LoadDataTask implements Runnable{

        @Override
        public void run() {
            //真正加载数据
            LoadResult loadResult = initData();
            //得到数据,处理数据,将加载结果赋值给mCurState,根据mCurState显示相应的视图
            mCurState = loadResult.getState();
            //刷新UI,现在是在子线程中,刷新UI必须在主线程中,将刷新UI的任务post到主线程中
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    refreshUIByState();
                }
            });
            //数据加载完成之后,将mLoadDataTask置空,否则mLoadDataTask永远不回为空
            mLoadDataTask=null;
        }
    }
    /**
     * @return
     * @dec 在子线程中加载数据, 必须实现, 但是不知道具体实现, 交给子类实现, 将initData定义为抽象方法
     * @called 外界需要加载数据时调用了triggerLoadData()方法
     */
    public abstract LoadResult initData();
    /**
     * @return
     * @des 初始化加载成功视图
     * @des 1.决定成功视图长什么样子
     * @des 2.为成功视图绑定数据
     * @called 1.外面触发加载数据, 而且数据加载成功
     * @called 2.必须实现, 但不知道具体实现, 交给子类去实现, 定义为抽象方法
     */
    public abstract View initSuccessView();
    public enum LoadResult{//枚举数据类型
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);
        int state;

        public int getState() {
            return state;
        }

        //必须提供一个构造方法,否则枚举类的第一行会报红
        LoadResult(int state) {
            this.state = state;
        }
    }
}
