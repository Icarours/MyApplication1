package com.syl.coolwater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syl.coolwater.R;
import com.syl.coolwater.activity.ContentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class ContentFragment1 extends BaseFragment implements View.OnClickListener {
    private static final int BTN_GREEN_DAO = 1;//greenDao
    @Bind(R.id.btn_green_dao)
    Button mBtnGreenDao;
    @Bind(R.id.button2)
    Button mButton2;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_content1, null);
        ButterKnife.bind(this, rootView);
        mBtnGreenDao.setOnClickListener(this);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_green_dao:
                Intent intent = new Intent(getActivity(),ContentActivity.class);
                intent.putExtra("fragment_code",BTN_GREEN_DAO);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
