package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe 渐变的ListView行
 * @Called
 */
public class RvDrawableFragment extends BaseFragment {
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.rv_test_drawable)
    RecyclerView mRvTestDrawable;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 60; i++) {
            mList.add("test title ------------" + i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rv_drawable, null);
        ButterKnife.bind(this, rootView);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText("渐变的ListView行");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTestDrawable.setLayoutManager(layoutManager);
        TestDrawableAdapter adapter = new TestDrawableAdapter(R.layout.list_item_single_text_gradient, mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), position+"------------was clicked..", Toast.LENGTH_SHORT).show();
            }
        });
        mRvTestDrawable.setAdapter(adapter);
        return rootView;
    }

    class TestDrawableAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public TestDrawableAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
