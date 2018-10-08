package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.ChartActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/1.
 *
 * @Describe 强大的Android自定义统计图表控件
 * @Called
 */
public class MPAndroidChartFragment extends BaseFragment {
    @Bind(R.id.rv_chart)
    RecyclerView mRvChart;
    private List<String> mListTitle = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mp_android_chart, null);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvChart.setLayoutManager(manager);

        ChartAdapter chartAdapter = new ChartAdapter(R.layout.list_item_single_text, mListTitle);
        mRvChart.setAdapter(chartAdapter);
        chartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goToChartActivity(position);
            }
        });
        return rootView;
    }

    /**
     * 跳转到具体展示统计图的界面
     *
     * @param position
     */
    private void goToChartActivity(int position) {
        Intent intent = new Intent(getActivity(), ChartActivity.class);
        intent.putExtra("title", mListTitle.get(position));
        intent.putExtra("chart_code", position);
        startActivity(intent);
    }

    class ChartAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public ChartAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        mListTitle.add("折线图(单)");
        mListTitle.add("折线图(单)2");
        mListTitle.add("折线图(多)3");
        mListTitle.add("环形图");
        mListTitle.add("环形图(带引线)");
        mListTitle.add("环形图3");
        mListTitle.add("柱形图");
        mListTitle.add("柱形图(多条)");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
