package com.syl.googleplay3.utils;


import com.syl.googleplay3.bean.DownLoadInfo;
import com.syl.googleplay3.manager.DownloadManger;

/**
 * @author Administrator
 * @version $Rev: 86 $
 * @time 2015-7-13 下午11:12:12
 * @des TODO
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2016-03-31 14:24:56 +0800 (星期四, 31 三月 2016) $
 * @updateDes TODO
 */
public class PrintDownLoadInfo {
    public static void printDownLoadInfo(DownLoadInfo info) {
        String result = "";
        switch (info.state) {
            case DownloadManger.STATE_UNDOWNLOAD:// 未下载
                result = "未下载";
                break;
            case DownloadManger.STATE_DOWNLOADING:// 下载中
                result = "下载中";
                break;
            case DownloadManger.STATE_PAUSEDOWNLOAD:// 暂停下载
                result = "暂停下载";
                break;
            case DownloadManger.STATE_WAITINGDOWNLOAD:// 等待下载
                result = "等待下载";
                break;
            case DownloadManger.STATE_DOWNLOADFAILED:// 下载失败
                result = "等待下载";
                break;
            case DownloadManger.STATE_DOWNLOADED:// 下载完成
                result = "下载完成";
                break;
            case DownloadManger.STATE_INSTALLED:// 已安装
                result = "已安装";
                break;

            default:
                break;
        }
        LogUtils.sf(result);
    }
}
