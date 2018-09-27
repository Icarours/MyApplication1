package com.syl.myapplication1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/13.
 *
 * @Describe
 * @Called
 */
public class FragmentContentVp extends BaseFragment {
    @Bind(R.id.textView)
    TextView mTextView;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_content_vp, null);
        ButterKnife.bind(this, rootView);
        mTextView.setTextSize(30);
        mTextView.setTextColor(Color.RED);
        Bundle bundle = getArguments();
        String key = bundle.getString("key");
        mTextView.setText(key);
        return rootView;
    }

    /**
     * 静态传值
     *
     * @param key
     * @return
     */
    public static FragmentContentVp newInstance(String key) {
        FragmentContentVp fragmentContentVp = new FragmentContentVp();
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        fragmentContentVp.setArguments(bundle);
        return fragmentContentVp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
