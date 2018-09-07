package com.syl.coolwater;

import android.view.View;
import android.widget.TextView;

import com.syl.coolwater.fragment.BaseFragment;

/**
 * Created by Bright on 2018/9/7.
 *
 * @Describe
 * @Called
 */
public class Fragment2 extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        TextView textView = new TextView(getActivity());
        textView.setText(this.getClass().getSimpleName());
        return textView;
    }
}
