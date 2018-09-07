package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.Api;
import com.syl.myapplication1.domain.Params1;
import com.syl.myapplication1.domain.WarnMessage;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe
 * @Called
 */
public class RxEasyHttpFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.btn_post1)
    Button mBtnPost1;
    @Bind(R.id.btn_post2)
    Button mBtnPost2;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_rx_easy_http, null);
        ButterKnife.bind(this, rootView);
        mBtnPost1.setOnClickListener(this);
        mBtnPost2.setOnClickListener(this);
        return rootView;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post1:
                post1();
                break;
            case R.id.btn_post2:
                break;
            default:
                break;
        }
    }

    private void post1() {
        Gson gson = new Gson();
        //http://60.205.177.21/lanlyc_gongdi/warnMessage/getWarnMessageList?paramJson=
        //
        //{'pageSize':'5000','pageNumber':'1','searchParam':'区域'}
        String url ="http://60.205.177.21/lanlyc_gongdi/warnMessage/getWarnMessageList";
        Params1 params1 = new Params1("20","1","区域");
        String toJson = gson.toJson(params1);
        EasyHttp.post(url)
                .params("paramJson",toJson)
                .execute(new CallBackProxy<Api<List<WarnMessage>>, List<WarnMessage>>(new SimpleCallBack<List<WarnMessage>>() {
                    @Override
                    public void onError(ApiException e) {
                        System.out.println("请求失败..");
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(List<WarnMessage> warnMessages) {
                        for (int i = 0; i < warnMessages.size(); i++) {
                            String s = warnMessages.get(i).toString();
                            System.out.println("id---"+s);
                        }
                    }
                }) {
                });
    }
}
