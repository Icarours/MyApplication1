package com.syl.myapplication1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.syl.myapplication1.R;
import com.syl.myapplication1.view.MyMarkerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/2.
 *
 * @Describe 折线图(多),x轴,y轴,legend,markerView,description等多项设置
 * @Called
 */
public class LineFragment3 extends BaseFragment {
    @Bind(R.id.line_chart3)
    LineChart mLineChart3;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private List<String> mTitles = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_chart3, null);
        ButterKnife.bind(this, rootView);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText("折线图(多),x轴,y轴,legend,markerView,description等多项设置");
        //设置绘制边界
        mLineChart3.setDrawBorders(false);
        //图表可以缩放
        mLineChart3.setPinchZoom(true);

        for (int i = 1; i <= 12; i++) {
            mTitles.add(i + "月");
        }
//        一.XAxis(X轴)
//        1.得到X轴：
        XAxis xAxis = mLineChart3.getXAxis();
//        2.设置X轴的位置（默认在上方）：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
//        3.设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        xAxis.setGranularity(1f);
//        4.设置X轴的刻度数量,第二个参数表示是否平均分配 如果为true则按比例分为12个点、如果为false则适配X刻度的值来分配点，可能没有12个点。
        xAxis.setLabelCount(12, false);
        //5.设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index > mTitles.size() - 1) {
                    return "";
                } else {
                    return mTitles.get(index);
                }
            }
        });

        //二.设置y轴
        //1.得到y轴
        YAxis axisLeft = mLineChart3.getAxisLeft();
        YAxis axisRight = mLineChart3.getAxisRight();
        //2.设置从Y轴值
        axisLeft.setAxisMinimum(10);
        axisLeft.setAxisMaximum(120);
        axisRight.setAxisMaximum(10);
        axisRight.setAxisMaximum(120);
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return (int) value + "%";
                return (int) value + "";
            }
        });

        //3.设置Y轴是否显示（效果如上右图）
        axisRight.setEnabled(false);

        //4.X轴和Y轴类似，都具有相同的属性方法
        axisLeft.setLabelCount(12, false);
        axisLeft.setGranularity(1f);
        axisLeft.setTextColor(Color.BLACK);
        axisLeft.setGridColor(Color.BLUE);
        axisLeft.setAxisLineColor(Color.RED);

        //5.限制线LimitLine
        LimitLine limitLine = new LimitLine(95, "高限制性95"); //得到限制线
        limitLine.setLineWidth(2f); //宽度
        limitLine.setTextSize(10f);
        limitLine.setTextColor(Color.RED);  //颜色
        limitLine.setLineColor(Color.BLUE);
        axisLeft.addLimitLine(limitLine); //Y轴添加限制线

        //三.设置标签
        Legend legend = mLineChart3.getLegend();
        legend.setTextColor(Color.BLACK);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        //标签是否换行,true换行;false,不换行
        legend.setWordWrapEnabled(true);
        //是否隐藏legend
        legend.setEnabled(true);

        //四.设置描述
//        Description description = mLineChart3.getDescription();
        Description description = new Description();
        description.setEnabled(true);//描述是否可见
        description.setText("description");
        description.setTextColor(Color.MAGENTA);
        mLineChart3.setDescription(description);

        // 五.MarkerView
        //设置显示MarkerView
        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        mLineChart3.setMarker(myMarkerView);

        //设置数据
        LineData lineData = initLineData();
        mLineChart3.setData(lineData);
        mLineChart3.animateX(1000);//添加动画
        return rootView;
    }

    /**
     * 设置多条折线,一个LineDataSet代表一个折线.关键在于向LineData中添加LineDataSet
     * @return
     */
    @NonNull
    private LineData initLineData() {
        List<Entry> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new Entry(i, (float) (Math.random() * 40 + 10)));
        }
        LineDataSet lineDataSet = new LineDataSet(list, "温度");
        lineDataSet.setDrawCircleHole(true);//设置圆环是空心还是实心.true,空心;false,实心
        lineDataSet.setValueTextSize(8f);//设置字体大小
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置折线为光滑的贝塞尔曲线,默认折线

        List<Entry> list2 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list2.add(new Entry(i, (float) (Math.random() * 40 + 40)));
        }
        LineDataSet lineDataSet2 = new LineDataSet(list2, "温度2");
        lineDataSet2.setDrawCircleHole(true);//设置圆环是空心还是实心.true,空心;false,实心
        lineDataSet2.setValueTextSize(8f);//设置字体大小
        lineDataSet2.setMode(LineDataSet.Mode.LINEAR);//设置折线为光滑的贝塞尔曲线,默认折线

        List<Entry> list3 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list3.add(new Entry(i, (float) (Math.random() * 40 + 80)));
        }
        LineDataSet lineDataSet3 = new LineDataSet(list3, "温度3");
        lineDataSet3.setDrawCircleHole(true);//设置圆环是空心还是实心.true,空心;false,实心
        lineDataSet3.setValueTextSize(8f);//设置字体大小
        lineDataSet3.setMode(LineDataSet.Mode.STEPPED);//设置折线为光滑的贝塞尔曲线,默认折线

        return new LineData(lineDataSet,lineDataSet2,lineDataSet3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
