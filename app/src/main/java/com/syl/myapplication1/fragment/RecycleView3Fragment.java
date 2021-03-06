package com.syl.myapplication1.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/6.
 *
 * @Describe 上滑加载更多
 * @Called
 */
public class RecycleView3Fragment extends BaseFragment {
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_rv3, null);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item1, getDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
                ImageView iv = helper.getView(R.id.imageView);
                Resources res = getActivity().getResources();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 16;
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.img1, options);
                iv.setImageBitmap(bitmap);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//开启缩放动画
        mAdapter.isFirstOnly(true);//true,第一次加载数据时有动画;false,每次加载数据时都有动画
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {//加载更多的时候延迟两秒,这样看起来更舒服
                    @Override
                    public void run() {
                        mAdapter.addData(addDatas());
                        mAdapter.loadMoreComplete();
                    }
                }, 2000);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), position+"--was clicked ---", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public List<String> addDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add("苹果超薄游戏本" + i);
        }
        return datas;
    }

    private List<String> getDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("苹果超薄笔记本电脑.." + i);
        }
        return list;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
