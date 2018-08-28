package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.Button;

import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.DownLoadInfo;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.manager.DownloadManger;
import com.syl.googleplay3.utils.CommonUtils;
import com.syl.googleplay3.utils.PrintDownLoadInfo;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.ProgressBtn;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by j3767 on 2016/12/10.
 *
 * @Describe 负责下载对应的UI展示
 * 观察者-->接收消息的人
 * @Called
 */
public class DetailDownloadHolder extends BaseHolder<ItemInfoBean> implements DownloadManger.DownLoadInfoObserver {

    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @Bind(R.id.app_detail_download_btn_download)
    ProgressBtn mAppDetailDownloadBtnDownload;
    private ItemInfoBean mItemInfoBean;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_download, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean itemInfoBean) {
        //保存数据到成员变量
        this.mItemInfoBean = itemInfoBean;
        /*------------------ 2.根据不同的状态给用户提示 -----------------*/
        //得到state-->得到DownloadInfoManager-->DownloadInfo
        DownLoadInfo downLoadInfo = DownloadManger.getInstance().getDownLoadInfo(itemInfoBean);
        refreshDownloadProgressBtnUI(downLoadInfo);
    }

    /**
     * @param downLoadInfo
     * @des 根据downloadInfo里面的状态, 刷新下载按钮的UI
     */
    private void refreshDownloadProgressBtnUI(DownLoadInfo downLoadInfo) {
        int state = downLoadInfo.state;
        mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (state) {
            case DownloadManger.STATE_UNDOWNLOAD://未下载
                mAppDetailDownloadBtnDownload.setText("下载");
                break;
            case DownloadManger.STATE_DOWNLOADING://下载中
                mAppDetailDownloadBtnDownload.setIsProgressEnable(true);
                mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                int index = (int) (downLoadInfo.progress * 100.0f / downLoadInfo.max + .5f);
                mAppDetailDownloadBtnDownload.setText(index + "%");
                mAppDetailDownloadBtnDownload.setMax(downLoadInfo.max);
                mAppDetailDownloadBtnDownload.setProgress(downLoadInfo.progress);
                break;
            case DownloadManger.STATE_PAUSEDOWNLOAD://暂停下载
                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case DownloadManger.STATE_WAITINGDOWNLOAD://等待下载
                mAppDetailDownloadBtnDownload.setText("等待中");
                break;
            case DownloadManger.STATE_DOWNLOADFAILED://下载失败
                mAppDetailDownloadBtnDownload.setText("重试");
                break;
            case DownloadManger.STATE_DOWNLOADED://下载完成
                mAppDetailDownloadBtnDownload.setText("安装");
                mAppDetailDownloadBtnDownload.setIsProgressEnable(false);
                break;
            case DownloadManger.STATE_INSTALLED://已安装
                mAppDetailDownloadBtnDownload.setText("打开");
                break;
            default:
                break;
        }
    }

    /**
     * 3.根据不同的状态触发不同的操作
     *
     * @param view
     */
    @OnClick(R.id.app_detail_download_btn_download)
    public void clickDownLoad(View view) {
        /*------------------ 3.根据不同的状态触发不同的操作 -----------------*/
        //得到state-->得到DownloadInfoManager-->DownloadInfo
        DownLoadInfo downLoadInfo = DownloadManger.getInstance().getDownLoadInfo(mItemInfoBean);
        int state = downLoadInfo.state;
        switch (state) {
            case DownloadManger.STATE_UNDOWNLOAD://如果是未下载,去下载
                doDownload(downLoadInfo);
                break;
            case DownloadManger.STATE_DOWNLOADING://如果是下载中,暂停下载
                pauseDownload(downLoadInfo);
                break;
            case DownloadManger.STATE_PAUSEDOWNLOAD://如果是暂停下载,断点继续下载
                doDownload(downLoadInfo);
                break;
            case DownloadManger.STATE_WAITINGDOWNLOAD://如果是等待下载,取消下载
                cancelDownload(downLoadInfo);
                break;
            case DownloadManger.STATE_DOWNLOADFAILED://如果是下载失败,重试下载
                doDownload(downLoadInfo);
                break;
            case DownloadManger.STATE_DOWNLOADED://如果是下载完成,安装应用
                installApk(downLoadInfo);
                break;
            case DownloadManger.STATE_INSTALLED://如果是已安装,打开应用
                openApk(downLoadInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 打开apk
     *
     * @param downLoadInfo
     */
    private void openApk(DownLoadInfo downLoadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
    }

    /**
     * 安装apk
     *
     * @param downLoadInfo
     */
    private void installApk(DownLoadInfo downLoadInfo) {
        String savePath = downLoadInfo.savePath;
        File saveFile = new File(savePath);
        CommonUtils.installApp(UIUtils.getContext(), saveFile);
    }

    /**
     * 取消下载
     *
     * @param downLoadInfo
     */
    private void cancelDownload(DownLoadInfo downLoadInfo) {
        DownloadManger.getInstance().cancelDownLoad(downLoadInfo);
    }

    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    private void pauseDownload(DownLoadInfo downLoadInfo) {
        DownloadManger.getInstance().pauseDownLoad(downLoadInfo);
    }

    /**
     * 1.开始下载,2.断点继续下载,3.重试
     *
     * @param downLoadInfo
     */
    private void doDownload(DownLoadInfo downLoadInfo) {
        DownloadManger.getInstance().downLoad(downLoadInfo);
    }

    /**
     * 收到DownLoadInfo改变的通知
     * 发布消息在子线程,收到消息也在子线程
     * 发布消息在主线程,收到消息也在子线程
     *
     * @param downLoadInfo
     */
    @Override
    public void onDownLoadInfoUpdate(final DownLoadInfo downLoadInfo) {
        PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
        //过滤观察者downLoadInfo,被观察者会通知所有的观察者更新UI.我们只需要当前的观察者接收到通知,并更新UI.否则,直接返回
        if (!downLoadInfo.packageName.equals(mItemInfoBean.getPackageName())) {
            return;
        }
        //将更新UI的任务交给主线程,添加到子线程的handler中.
        MyApplication.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                refreshDownloadProgressBtnUI(downLoadInfo);
            }
        });
    }
}
