package com.syl.googleplay3.utils;

import android.content.Context;
import android.content.res.Resources;

import com.syl.googleplay3.config.MyApplication;


/**
 * Created by j3767 on 2016/11/16.
 *
 * @Describe 和UI相关的操作
 * 得到application文件中的一些数据string.xml, color.xml文件中的信息
 * @Called
 */

public class UIUtils {
    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 得到resource对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 得到string.xml文件的string字符数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getContext().getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml文件中的color信息
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return getResource().getColor(resId);
    }

    /**
     * 得到包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * dip -->px
     * @param dip
     * @return
     */
    public static int dp2px(int dip) {
        /**
         * dp = dip;
         * dp-->px,px-->dp
         * 1.px/dp = density
         * 2.px/(ppi/160) = dp;
         */
        //获取像素密度
        float density = UIUtils.getResource().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    /**
     * px-->dip
     *
     * @param px 像素值
     * @return
     */
    public static int px2dp(int px) {
        /**
         * dp = dip;
         * dp-->px,px-->dp
         * 1.px/dp = density
         * 2.px/(ppi/160) = dp;
         */
        //获取像素密度
        float density = UIUtils.getResource().getDisplayMetrics().density;
        int dp = (int) (px / density + .5f);
        return dp;
    }
}
