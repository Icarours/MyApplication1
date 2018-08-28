package com.syl.googleplay3.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.factory.ThreadPoolProxyFactory;
import com.syl.googleplay3.holder.LoadMoreHolder;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 简化getView方法
 * listView 多种条目的视图,需要重写这两个方法getViewTypeCount(),getItemViewType,然后根据ItemViewType的不同,在getView()方法中返回不同的convertView条目视图
 * @Called
 */

public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter implements AdapterView.OnItemClickListener {
    public static final int VIEWTYPE_LOADMORE = 0;//加载更多的类型
    public static final int VIEWTYPE_NORMAL = 1;//加载普通条目的类型
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreDataTask mLoadMoreDataTask;
    private AbsListView mAbsListView;
    private int mState;
    private BaseProtocol mBaseProtocol;

    public SuperBaseAdapter(AbsListView absListView, List<ITEMBEANTYPE> dataSet, BaseProtocol protocol) {
        super(dataSet);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);
        mBaseProtocol = protocol;
    }

    public SuperBaseAdapter(AbsListView absListView, List<ITEMBEANTYPE> dataSet) {
        super(dataSet);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //决定根视图
        BaseHolder holder;
        if (convertView == null) {
            /**
             * 如果是加载更多的情况
             */
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
//                holder加载更多是baseHolder的子类,加载更多是一个静态视图
                holder = getLoadMoreHolder();
//                Toast.makeText(UIUtils.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            } else {//如果是正常加载item的情况,getView()方法
                holder = getSpecialHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        View holderView = holder.mHolderView;

        //绑定数据
        if (VIEWTYPE_LOADMORE == getItemViewType(position)) {
            if (hasLoadMore()) {//有加载更多
                //刷新UI
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);
                //触发加载数据
                triggerLoadMoreData();
            } else {
                //刷新UI
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        } else {//没有加载更多
            holder.setDataAndRefreshHolderView(mDataSet.get(position));
        }
        return holderView;//View
    }

    //触发加载更多
    private void triggerLoadMoreData() {
        //重置状态,刷新UI
        mState = LoadMoreHolder.LOADMORE_LOADING;
        mLoadMoreHolder.setDataAndRefreshHolderView(mState);
        if (mLoadMoreDataTask == null) {
            mLoadMoreDataTask = new LoadMoreDataTask();
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(new LoadMoreDataTask());
        }
    }

    /**
     * author   Bright
     * date     2018/8/1 18:24
     * desc
     * 加载更多
     */
    class LoadMoreDataTask implements Runnable {
        private static final int PAGESIZE = 20;

        @Override
        public void run() {
            List<ITEMBEANTYPE> loadMoreList = null;
            try {
                loadMoreList = onLoadMoreData();//返回加载跟多的数据集合
                if (loadMoreList == null) {
                    mState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                } else {
                    if (loadMoreList.size() == PAGESIZE) {
                        mState = LoadMoreHolder.LOADMORE_LOADING;//可能有加载更多
                    } else {
                        mState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.LOADMORE_ERROR;//加载失败
            }
            //刷新ui-->adapter-->更新对应的list集合-->
            //刷新UI-->LoadMoreHolder-->最新的int型state
            //现在是在子线程中,刷新UI必须在主线程中
            //定义临时变量
            final List<ITEMBEANTYPE> finalLoadMoreList = loadMoreList;
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->adapter-->更新对应的list集合-->
                    if (finalLoadMoreList != null && finalLoadMoreList.size() != 0) {
                        mDataSet.addAll(finalLoadMoreList);
                        notifyDataSetChanged();//刷新数据集
                    }
                    //刷新UI-->LoadMoreHolder-->最新的int型state
                    mLoadMoreHolder.setDataAndRefreshHolderView(mState);
                }
            });
            mLoadMoreDataTask = null;//加载更多任务结束之后,将其置空
        }
    }

    /**
     * 子类可能有加载更多,交个子类选择性实现,加载更多是否有异常只有子类知道,所以必须抛出一个异常
     * 在子线程中真正加载数据
     *
     * @return
     */
    public List<ITEMBEANTYPE> onLoadMoreData() throws Exception {
        return null;
    }

    /**
     * 有的页面有加载更多,有的没有,交给子类选择性实现
     *
     * @return
     */
    public boolean hasLoadMore() {
        return false;
    }

    /**
     * 返回加载更多时的视图
     *
     * @return
     */
    private BaseHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (getCount() - 1 == position) {
            return VIEWTYPE_LOADMORE;
        } else {
            return getNormalItemViewType(position);
        }
    }

    /**
     * 得到普通条目的类型
     * 普通条目的类型可能会更多,可以交给子类选择性的去复写
     *
     * @param position
     * @return
     */
    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;//默认情况,普通条目就只有一种类型
    }

    /**
     * listView item的条目样式数量,默认是1,可以复写
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    //指定对应的Holder
    public abstract BaseHolder getSpecialHolder(int position);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAbsListView instanceof ListView) {
            position = position - ((ListView) mAbsListView).getHeaderViewsCount();
        }
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {//如果条目是VIEWTYPE_LOADMORE类型,触发加载更多
            if (mState == LoadMoreHolder.LOADMORE_ERROR) {//如果加载更多失败,再次触发加载更多
                triggerLoadMoreData();
            }
        } else {//普通条目
            onNormalItemClick(parent, view, position, id);
        }
    }

    /**
     * 在本类中不知道普通条目的点击事件如何处理,交给子类处理,而且必须实现
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
