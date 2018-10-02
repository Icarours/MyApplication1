package com.syl.myapplication1.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.syl.myapplication1.R;

import java.text.DecimalFormat;

/**
 * Created by Bright on 2018/10/2.
 *
 * @Describe 自定义MarkerView
 * @Called
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private DecimalFormat format = new DecimalFormat("##0");

    public MyMarkerView(Context context) {
        super(context, R.layout.layout_markerview);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(format.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 10);
    }
}

