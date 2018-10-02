package com.syl.myapplication1.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.QinEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/3.
 *
 * @Describe BaseQuickAdapter的应用
 * @Called
 */
public class RecycleViewFragment extends BaseFragment {
    private List<QinEntity> mListData = new ArrayList<>();
    @Bind(R.id.rv)
    RecyclerView mRv;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 50; i++) {
            mListData.add(new QinEntity(i,"李沁最美"));
        }
    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_rv, null);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(new RvAdapter());
        return rootView;
    }

    class RvAdapter extends BaseQuickAdapter<QinEntity, BaseViewHolder> {
        /**
         *
         * @param layoutResId 条目布局
         * @param data 数据集合
         */
        public RvAdapter(int layoutResId, @Nullable List<QinEntity> data) {
            super(layoutResId, data);
        }

        public RvAdapter(@Nullable List<QinEntity> data) {
            super(data);
        }


        public RvAdapter() {
            //第一个参数是条目布局,第二个参数是数据集合
            super(R.layout.list_item_image, mListData);
        }

        @Override
        protected void convert(BaseViewHolder holder, QinEntity qinEntity) {
            TextView tv = holder.getView(R.id.tv);
            tv.setText(qinEntity.toString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
