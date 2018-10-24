package com.syl.mobileplayer.util;

import android.util.Log;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class LogUtil {
    public static boolean showLog = true;

    public static void logE(String tag, String msg) {
        if (showLog) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (showLog)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (showLog)
            Log.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (showLog)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (showLog)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (showLog)
            Log.e(tag, msg);
    }
}
