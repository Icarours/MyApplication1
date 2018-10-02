package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.syl.myapplication1.R;
import com.syl.myapplication1.utils.MPChartHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/1.
 *
 * @Describe 折线图(单),
 * @Called
 */
public class LineFragment extends BaseFragment {
    @Bind(R.id.line_chart)
    LineChart mLineChart;
    private List<String> xAxisValues = new ArrayList<>();
    private List<Float> yAxisValues = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        titles.add("折线图(单)");
        for (int i = 0; i < 8; i++) {
            xAxisValues.add(String.valueOf(i));
            yAxisValues.add((float) (Math.random() * 1000 + 20));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_chart, null);
        ButterKnife.bind(this, rootView);

        MPChartHelper.setLineChart(mLineChart, xAxisValues, yAxisValues, titles.get(0), true);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
