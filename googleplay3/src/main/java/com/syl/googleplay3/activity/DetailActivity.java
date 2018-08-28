package com.syl.googleplay3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.syl.googleplay3.R;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.DownLoadInfo;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.holder.DetailDecHolder;
import com.syl.googleplay3.holder.DetailDownloadHolder;
import com.syl.googleplay3.holder.DetailInfoHolder;
import com.syl.googleplay3.holder.DetailPicHolder;
import com.syl.googleplay3.holder.DetailSafeHolder;
import com.syl.googleplay3.manager.DownloadManger;
import com.syl.googleplay3.protocol.DetailProtocol;
import com.syl.googleplay3.utils.UIUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.detail_fl_download)
    FrameLayout mDetailFlDownload;
    @Bind(R.id.detail_fl_info)
    FrameLayout mDetailFlInfo;
    @Bind(R.id.detail_fl_safe)
    FrameLayout mDetailFlSafe;
    @Bind(R.id.detail_fl_pic)
    FrameLayout mDetailFlPic;
    @Bind(R.id.detail_fl_dec)
    FrameLayout mDetailFlDec;
    private LoadingPager mLoadingPager;
    private String mPackageName;
    private ItemInfoBean mItemInfoBean;
    private DetailDownloadHolder mDetailDownloadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);//1.加载中视图;2,失败视图;3.空视图;4.成功视图
        init();
        initView();
        initData();
    }

    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    private void initView() {
        mLoadingPager = new LoadingPager(UIUtils.getContext()) {
            @Override
            public LoadResult initData() {
                return DetailActivity.this.loadData();
            }

            @Override
            public View initSuccessView() {
                return DetailActivity.this.initSuccessView();
            }
        };
        //R.layout.activity_detail作用是提供一个视图,LoadingPager就可以提供
        setContentView(mLoadingPager);//1.加载中视图;2,失败视图;3.空视图;4.成功视图
    }

    /**
     * 决定DetailActivity中的成功视图长什么样子
     * 决定DetailActivity中的成功视图和数据的绑定
     *
     * @return
     */
    private View initSuccessView() {
        View successView = View.inflate(UIUtils.getContext(), R.layout.item_detial_successview, null);
        ButterKnife.bind(this, successView);
        //往容器里面填充具体的数据
        //填充信息部分
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        mDetailFlInfo.addView(detailInfoHolder.mHolderView);
        detailInfoHolder.setDataAndRefreshHolderView(mItemInfoBean);
        //填充安全部分
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mDetailFlSafe.addView(detailSafeHolder.mHolderView);
        detailSafeHolder.setDataAndRefreshHolderView(mItemInfoBean);
        //填充图片部分
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        mDetailFlPic.addView(detailPicHolder.mHolderView);
        detailPicHolder.setDataAndRefreshHolderView(mItemInfoBean);
        //填充描述部分
        DetailDecHolder detailDecHolder = new DetailDecHolder();
        mDetailFlDec.addView(detailDecHolder.mHolderView);
        detailDecHolder.setDataAndRefreshHolderView(mItemInfoBean);
        //填充下载部分
        mDetailDownloadHolder = new DetailDownloadHolder();
        mDetailFlDownload.addView(mDetailDownloadHolder.mHolderView);
        mDetailDownloadHolder.setDataAndRefreshHolderView(mItemInfoBean);

        //将detailDownloadHolder添加到观察者集合中
        DownloadManger.getInstance().addObserver(mDetailDownloadHolder);
        return successView;
    }

    /**
     * 子线程中真正加载数据
     *
     * @return
     */
    private LoadingPager.LoadResult loadData() {
        try {
            DetailProtocol protocol = new DetailProtocol(mPackageName);
            mItemInfoBean = protocol.loadData(0);
            if (mItemInfoBean == null) {
                return LoadingPager.LoadResult.EMPTY;
            }
            return LoadingPager.LoadResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    //触发加载数据
    private void initData() {
        //触发加载Activity应该加载的数据-->异步加载-->得到数据-->处理数据-->决定展示四种视图中的哪一种
        mLoadingPager.triggerLoadData();
    }

    /**
     * mDetailDownloadHolder和DetailActivity生命周期绑定
     */
    @Override
    protected void onPause() {
        //从观察者集合中移除观察者
        if (mDetailDownloadHolder != null) {
            DownloadManger.getInstance().deleteObserver(mDetailDownloadHolder);
        }
        super.onPause();
    }

    /**
     * mDetailDownloadHolder和DetailActivity生命周期绑定
     */
    @Override
    protected void onResume() {
        //添加观察者到观察者集合中
        if (mDetailDownloadHolder != null) {
            DownloadManger.getInstance().addObserver(mDetailDownloadHolder);
            /*
            一个ItemInfoBean-->对应一个DownLoadInfo
            手动发布详情里面ItemInfoBean对应的DownLoadInfo的最新消息
             */
            DownLoadInfo downLoadInfo = DownloadManger.getInstance().getDownLoadInfo(mItemInfoBean);
            //将最新的downLoadInfo发布出去
            DownloadManger.getInstance().notifyObservers(downLoadInfo);
        }
        super.onResume();
    }
}
