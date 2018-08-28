package com.syl.myapplication1.fragment;

import android.view.View;

import com.syl.myapplication1.R;
import com.syl.myapplication1.view.ArcView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/8/21.
 *
 * @Describe
 * @Called
 */

public class ArcFragment extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_arc, null);
        ArcView arcView = (ArcView) rootView.findViewById(R.id.arcView);
        List<Times> times = new ArrayList<>();
        for (int i = 6; i > 0; i--) {
            Times t = new Times();
            t.hour = i;
            t.text = "Number"+i;
            times.add(t);
        }

        //must set adapter!
        ArcView.ArcViewAdapter myAdapter = arcView.new ArcViewAdapter<Times>(){
            @Override
            public double getValue(Times times) {
                return times.hour;
            }

            @Override
            public String getText(Times times) {
                return times.text;
            }
        };
        //You'd better sort the data from large to small !!!
        myAdapter.setData(times);//must set adapter's data
        arcView.setMaxNum(7);//the max piece of sector  optional
        arcView.setOthersText("Others");//the text of others  optional
        //arcView.setRadius(150);//set radius  optional
        //arcView.setColors(new int[]{getResources().getColor(R.color.green),getResources().getColor(R.color.colorAccent)});//set colors  optional
        return rootView;
    }
    class Times {
        double hour;
        String text;
    }
}
