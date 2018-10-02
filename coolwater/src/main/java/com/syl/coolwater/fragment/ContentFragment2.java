package com.syl.coolwater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.syl.coolwater.R;
import com.syl.coolwater.activity.ContentActivity2;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/29.
 *
 * @Describe
 * @Called
 */

public class ContentFragment2 extends BaseFragment implements View.OnClickListener {
    private static final int BTN_GAODE_MAP = 1;//高德地图
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.btn_gaode_map)
    Button mBtnGaodeMap;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_content2, null);
        ButterKnife.bind(this, rootView);
        mTvContent.setText("主界面2");
        mBtnGaodeMap.setOnClickListener(this);
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
            case R.id.btn_gaode_map:
                Intent intent = new Intent(getActivity(), ContentActivity2.class);
                intent.putExtra("fragment_code",BTN_GAODE_MAP);
                intent.putExtra("title","高德地图");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
