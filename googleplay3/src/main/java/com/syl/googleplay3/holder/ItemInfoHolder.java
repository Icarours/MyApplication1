package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.DownLoadInfo;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.manager.DownloadManger;
import com.syl.googleplay3.utils.CommonUtils;
import com.syl.googleplay3.utils.PrintDownLoadInfo;
import com.syl.googleplay3.utils.StringUtils;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.ProgressView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 1.提供视图, 2, 提供数据, 3.视图和数据的绑定
 * @Called
 */

public class ItemInfoHolder extends BaseHolder<ItemInfoBean> implements DownloadManger.DownLoadInfoObserver{
    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @Bind(R.id.item_appinfo_progressview)
    ProgressView mProgressview;
    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;
    private ItemInfoBean mItemInfoBean;

    //初始化视图
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_info, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    //视图和数据的绑定
    @Override
    public void refreshHolderView(ItemInfoBean itemInfoBean) {
        //保存数据到成员变量
        mItemInfoBean = itemInfoBean;
        //data 局部变量
        //view 成员变量
        //view+data
        mItemAppinfoTvDes.setText(itemInfoBean.getDes());
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(itemInfoBean.getSize()));
        mItemAppinfoTvTitle.setText(itemInfoBean.getName());

        //重置itemInfoBean中progress的进度
        mProgressview.setProgress(0);

        //评分的展示
        mItemAppinfoRbStars.setRating(itemInfoBean.getStars());
        //图片的加载
        Glide.with(UIUtils.getContext())
                .load(Constants.URLS.IMAGEBASEURL + itemInfoBean.getIconUrl())
                .into(mItemAppinfoIvIcon);


        /*------------------ 2.根据不同的状态给用户提示 -----------------*/
        //得到state-->得到DownloadInfoManager-->DownloadInfo
        DownLoadInfo downLoadInfo = DownloadManger.getInstance().getDownLoadInfo(itemInfoBean);
        refreshProgressViewUI(downLoadInfo);
    }
    private void refreshProgressViewUI(DownLoadInfo downLoadInfo) {
        int state = downLoadInfo.state;
        switch (state) {
            case DownloadManger.STATE_UNDOWNLOAD://如果是未下载,提示图标下载
                mProgressview.setTvNote("下载");
                mProgressview.setIvIcon(R.drawable.ic_download);
                break;
            case DownloadManger.STATE_DOWNLOADING://如果是下载中,提示图标暂停下载
                mProgressview.setIvIcon(R.drawable.ic_pause);
                mProgressview.setIsProgressEnable(true);
                int index = (int) (downLoadInfo.progress * 100.0f / downLoadInfo.max + .5f);
                mProgressview.setTvNote(index + "%");
                mProgressview.setMax(downLoadInfo.max);
                mProgressview.setProgress(downLoadInfo.progress);
                break;
            case DownloadManger.STATE_PAUSEDOWNLOAD://如果是暂停下载,提示图标继续下载
                mProgressview.setIvIcon(R.drawable.ic_resume);
                mProgressview.setTvNote("继续下载");
                break;
            case DownloadManger.STATE_WAITINGDOWNLOAD://如果是等待下载,提示图标暂停下载
                mProgressview.setIvIcon(R.drawable.ic_pause);
                mProgressview.setTvNote("等待中");
                break;
            case DownloadManger.STATE_DOWNLOADFAILED://如果是下载失败,提示图标重试
                mProgressview.setIvIcon(R.drawable.ic_redownload);
                mProgressview.setTvNote("重试");
                break;
            case DownloadManger.STATE_DOWNLOADED://如果是下载完成,提示图标已安装
                mProgressview.setIvIcon(R.drawable.ic_install);
                mProgressview.setTvNote("安装");
                mProgressview.setIsProgressEnable(false);
                break;
            case DownloadManger.STATE_INSTALLED://如果是已安装,提示图标已安装
                mProgressview.setIvIcon(R.drawable.ic_install);
                mProgressview.setTvNote("打开");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.item_appinfo_progressview)
    public void clickProgressView(View view) {
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
                refreshProgressViewUI(downLoadInfo);
            }
        });
    }
}
