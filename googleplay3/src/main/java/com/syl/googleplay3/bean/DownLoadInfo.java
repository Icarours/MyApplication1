package com.syl.googleplay3.bean;

/**
 * Created by j3767 on 2016/12/11.
 *
 * @Describe 放置和下载相关的参数:1.下载地址,2.下载文件存放位置
 * @Called
 */

public class DownLoadInfo {
    public String downLoadUrl;
    public String savePath;
    public int state;
    public String packageName;
    public long max;//进度最大值
    public long progress;//当前进度
    public Runnable task;
}
