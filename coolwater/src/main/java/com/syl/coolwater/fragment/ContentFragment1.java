package com.syl.coolwater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syl.coolwater.Main1Activity;
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
    @Bind(R.id.btn_activity_fragment)
    Button mBtnActivityFragment;


    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_content1, null);
        ButterKnife.bind(this, rootView);
        mBtnGreenDao.setOnClickListener(this);
        mBtnActivityFragment.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_green_dao:
                intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("fragment_code", BTN_GREEN_DAO);
                intent.putExtra("title","greenDao");
                break;
            case R.id.btn_activity_fragment:
                intent = new Intent(getActivity(), Main1Activity.class);
                intent.putExtra("fragment_code", BTN_GREEN_DAO);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
