package com.syl.googleplay3.config;


import com.syl.googleplay3.utils.LogUtils;

/**
 * Created by j3767 on 2016/11/16.
 *
 * @Describe
 * @Called
 */
public class Constants {
    /**
     * LogUtils.LEVEL_ALL 打开所有日志
     * LogUtils.LEVEL_OFF 关闭所有日志
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;

    public static final class URLS {
        public static final String BASEURL = "http://192.168.31.89:80/GooglePlayServer/";
        public static final String IMAGEBASEURL = BASEURL + "image?name=";
        public static final String DOWNLOADBASEURL = BASEURL + "download";
    }
}
