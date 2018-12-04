package com.syl.coolwater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.syl.coolwater.Main1Activity;
import com.syl.coolwater.R;
import com.syl.coolwater.activity.ContentActivity;
import com.syl.coolwater.activity.DateActivity;
import com.syl.coolwater.activity.Rv1Activity;
import com.syl.coolwater.bean.RequestParameter;
import com.syl.coolwater.bean.WarnMessage;
import com.syl.coolwater.http.IDataListener;
import com.syl.coolwater.http.MyVolly;

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
    private static final int BTN_ACTIVITY_FRAGMENT = 2;//btn_activity_fragment
    private static final int BTN_RV1 = 3;//btn_rv1
    private static final int BTN_DATE_PICKER = 4;//btn_date_picker
    @Bind(R.id.btn_green_dao)
    Button mBtnGreenDao;
    @Bind(R.id.btn_activity_fragment)
    Button mBtnActivityFragment;
    @Bind(R.id.btn_myvolly)
    Button mBtnMyvolly;
    @Bind(R.id.btn_rv1)
    Button mBtnRv1;
    @Bind(R.id.btn_date_picker)
    Button mBtnDatePicker;


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
        mBtnMyvolly.setOnClickListener(this);
        mBtnRv1.setOnClickListener(this);
        mBtnDatePicker.setOnClickListener(this);
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
            default:
            case R.id.btn_green_dao:
                intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("fragment_code", BTN_GREEN_DAO);
                intent.putExtra("title", "greenDao");
                startActivity(intent);
                break;
            case R.id.btn_activity_fragment:
                intent = new Intent(getActivity(), Main1Activity.class);
                intent.putExtra("fragment_code", BTN_ACTIVITY_FRAGMENT);
                startActivity(intent);
                break;
            case R.id.btn_myvolly:
                btnMyVolly();
                break;
            case R.id.btn_rv1:
                intent = new Intent(getActivity(), Rv1Activity.class);
                intent.putExtra("fragment_code", BTN_RV1);
                startActivity(intent);
                break;
            case R.id.btn_date_picker:
                intent = new Intent(getActivity(), DateActivity.class);
                intent.putExtra("fragment_code", BTN_DATE_PICKER);
                startActivity(intent);
                break;
        }
    }

    //http://cloud.lanlyc.cn/new_gongdi/warnMessage/getWarnMessageList?paramJson={"pageNumber":"1","pageSize":"16"}
    private void btnMyVolly() {
        String url = "http://cloud.lanlyc.cn/new_gongdi/warnMessage/getWarnMessageList?paramJson=";
        RequestParameter requestParameter = new RequestParameter("1", 16, "");
        String params = JSON.toJSONString(requestParameter);
        MyVolly.sendJsonRequest(params, url, WarnMessage.class, new IDataListener<WarnMessage>() {
            @Override
            public void onSuccess(WarnMessage warnMessage) {
                Log.d("MyVolly", "result==" + warnMessage.toString());
            }

            @Override
            public void onFailure() {
                Log.d("MyVolly", "result==请求失败");
            }
        });
    }
}
