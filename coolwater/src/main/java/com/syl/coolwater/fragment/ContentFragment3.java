package com.syl.coolwater.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.coolwater.R;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class ContentFragment3 extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_content3, null);
        return rootView;
    }

}
