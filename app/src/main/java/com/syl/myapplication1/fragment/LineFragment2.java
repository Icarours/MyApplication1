package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/2.
 *
 * @Describe
 * @Called
 */
public class LineFragment2 extends BaseFragment {
    @Bind(R.id.line_chart2)
    LineChart mLineChart2;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_chart2, null);
        ButterKnife.bind(this, rootView);

        mLineChart2.setDrawBorders(true);//设置边界

        //设置数据
        List<Entry> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Entry(i, (float) (Math.random()*60+40)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(list,"温度");
        LineData lineData =new LineData(lineDataSet);
        mLineChart2.setData(lineData);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
