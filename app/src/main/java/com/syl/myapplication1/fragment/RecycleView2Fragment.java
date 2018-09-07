package com.syl.myapplication1.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.library.BaseQuickAdapter;
import com.github.library.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.QinEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe 网格, 一行有多个
 * @Called
 */
public class RecycleView2Fragment extends BaseFragment {
    @Bind(R.id.rv)
    RecyclerView mRv;
    private List<QinEntity> mList;

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
        RvAdapter rvAdapter = new RvAdapter();
        mRv.setAdapter(rvAdapter);
        return rootView;
    }

    public List<QinEntity> getData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add(new QinEntity(i, "李沁最美" + i));
        }
        return mList;
    }

    class RvAdapter extends BaseQuickAdapter<QinEntity, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.list_item_image2, mList);
        }

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
