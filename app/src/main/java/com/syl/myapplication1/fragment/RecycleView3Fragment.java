package com.syl.myapplication1.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.library.BaseQuickAdapter;
import com.github.library.BaseViewHolder;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/6.
 *
 * @Describe
 * @Called
 */
public class RecycleView3Fragment extends BaseFragment {
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_rv3, null);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item_image, getDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv,item);
            }
        };
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mAdapter.addData(getDatas().size(),addDatas());
                mAdapter.loadMoreComplete();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public List<String> addDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add("苹果超薄游戏本" + i);
        }
        return datas;
    }

    private List<String> getDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("苹果超薄笔记本电脑.." + i);
        }
        return list;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
