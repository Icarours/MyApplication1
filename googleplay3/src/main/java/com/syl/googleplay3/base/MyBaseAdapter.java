package com.syl.googleplay3.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 简化BaseAdapter中的前三个方法
 * @Called
 */

public abstract class MyBaseAdapter<ITEMBEANTYPE> extends BaseAdapter {
    public List<ITEMBEANTYPE> mDataSet;
    public MyBaseAdapter(List<ITEMBEANTYPE> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public int getCount() {
        if (mDataSet !=null){
            return mDataSet.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSet !=null){
            return mDataSet.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mDataSet !=null){
            return position;
        }
        return 0;
    }
}
