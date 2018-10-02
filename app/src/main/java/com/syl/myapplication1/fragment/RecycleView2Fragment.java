package com.syl.myapplication1.fragment;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.QinEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe BaseQuickAdapter的应用
 * GridView网格, 一行有多个.RecyclerView上拉加载更多
 * @Called
 */
public class RecycleView2Fragment extends BaseFragment {
    @Bind(R.id.rv)
    RecyclerView mRv;

    private Handler mHandler = new Handler();
    //delayMillis
    private static final int DELAY_MILLIS = 1500;

    private int mShowType = 0;
    private RvAdapter mAdapter;

    @Override
    protected void init() {
        getData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_rv2, null);
        ButterKnife.bind(this, rootView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRv.setLayoutManager(gridLayoutManager);
        mAdapter = new RvAdapter(R.layout.list_item_image2, getData());
        mRv.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mShowType++;
                if (mShowType == 2) {//根据加载状态判断,显示加载那种情况
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadMoreFail();
                        }
                    }, DELAY_MILLIS);
                } else if (mShowType >= 4) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadMoreEnd();//没有加载更多
                        }
                    });
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addData(addDatas());
                            mAdapter.loadMoreComplete();//加载更多,加载完成
                        }
                    }, DELAY_MILLIS);
                }
            }
        });
        return rootView;
    }

    public List<QinEntity> addDatas() {
        List<QinEntity> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new QinEntity(new Random().nextInt(100),"add----"));
        }
        return list;
    }

    public List<QinEntity> getData() {
        List<QinEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new QinEntity(i, "李沁最美" + i));
        }
        return list;
    }

    class RvAdapter extends BaseQuickAdapter<QinEntity, BaseViewHolder> {

        public RvAdapter(int layoutResId, @Nullable List<QinEntity> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder holder, QinEntity item) {
            holder.setText(R.id.tv, item.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
