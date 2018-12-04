package com.syl.coolwater.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.syl.coolwater.R;
import com.syl.coolwater.bean.Api;
import com.syl.coolwater.bean.Params1;
import com.syl.coolwater.bean.WarnMessage;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Rv1Activity extends AppCompatActivity {
    private static final int PAGE_SIZE = 20;
    @Bind(R.id.rv_list)
    RecyclerView mRecyclerView;
    private PullToRefreshAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv1);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        initAdapter();
        initData();
    }

    private void initData() {
        final List<WarnMessage> list = new ArrayList<>();
        Gson gson = new Gson();
        String url = "http://cloud.lanlyc.cn/new_gongdi/warnMessage/getWarnMessageList";
        Params1 params1 = new Params1("20", "1", "");
        String toJson = gson.toJson(params1);
        EasyHttp.post(url)
                .params("paramJson", toJson)
                .execute(new CallBackProxy<Api<List<WarnMessage>>, List<WarnMessage>>(new SimpleCallBack<List<WarnMessage>>() {
                    @Override
                    public void onError(ApiException e) {
                        System.out.println("请求失败..");
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(List<WarnMessage> warnMessages) {
                        if (warnMessages != null && warnMessages.size() > 0) {
                            list.addAll(warnMessages);
                        }
                        initAdapter(list);
                        for (int i = 0; i < warnMessages.size(); i++) {
                            String s = warnMessages.get(i).toString();
                            System.out.println("id---" + s);
                        }
                    }
                }) {
                });
    }

    private void initAdapter(List<WarnMessage> list) {
        mAdapter = new PullToRefreshAdapter(R.layout.rv_item1, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<WarnMessage> getData() {
        final List<WarnMessage> list = new ArrayList<>();
        Gson gson = new Gson();
        String url = "http://cloud.lanlyc.cn/new_gongdi/warnMessage/getWarnMessageList";
        Params1 params1 = new Params1("20", "1", "");
        String toJson = gson.toJson(params1);
        EasyHttp.post(url)
                .params("paramJson", toJson)
                .execute(new CallBackProxy<Api<List<WarnMessage>>, List<WarnMessage>>(new SimpleCallBack<List<WarnMessage>>() {
                    @Override
                    public void onError(ApiException e) {
                        System.out.println("请求失败..");
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(List<WarnMessage> warnMessages) {
                        if (warnMessages != null && warnMessages.size() > 0) {
                            list.addAll(warnMessages);
                        }
                        for (int i = 0; i < warnMessages.size(); i++) {
                            String s = warnMessages.get(i).toString();
                            System.out.println("id---" + s);
                        }
                    }
                }) {
                });
        return list;
    }

    /**
     * Created by Bright on 2018/11/25.
     *
     * @Describe
     * @Called
     */
    public class PullToRefreshAdapter extends BaseQuickAdapter<WarnMessage, BaseViewHolder> {

        public PullToRefreshAdapter(int layoutResId, @Nullable List<WarnMessage> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WarnMessage item) {
            helper.setText(R.id.tv_camera_id, item.getCamera_id());
            helper.setText(R.id.tv_id, item.getId());
            helper.setText(R.id.tv_warn_type, item.getWarn_picture());
            helper.setText(R.id.tv_create_time, item.getCreate_time());
            ImageView imageView = helper.getView(R.id.iv_warn_picture);
            Glide.with(Rv1Activity.this).load(item.getWarn_picture())
                    .into(imageView);
        }
    }

}

interface RequestCallBack {
    void success(List<WarnMessage> data);

    void fail(Exception e);
}

class Request extends Thread {
    private static final int PAGE_SIZE = 6;
    private int mPage;
    private RequestCallBack mCallBack;
    private Handler mHandler;

    private static boolean mFirstPageNoMore;
    private static boolean mFirstError = true;

    public Request(int page, RequestCallBack callBack) {
        mPage = page;
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        if (mPage == 2 && mFirstError) {
            mFirstError = false;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallBack.fail(new RuntimeException("fail"));
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallBack.success(getData());
                }
            });
        }
    }

    private List<WarnMessage> getData() {
        List<WarnMessage> list = new ArrayList<>();
        Gson gson = new Gson();
        String url = "http://cloud.lanlyc.cn/new_gongdi/warnMessage/getWarnMessageList";
        Params1 params1 = new Params1("20", "1", "");
        String toJson = gson.toJson(params1);
        EasyHttp.post(url)
                .params("paramJson", toJson)
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
                            System.out.println("id---" + s);
                        }
                    }
                }) {
                });
        return list;
    }
}
