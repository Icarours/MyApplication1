package com.syl.mobileplayer.util;

import android.util.Log;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class LogUtil {
    public static boolean showLog = true;
    public static void logE(String tag,String msg){
        if (showLog) {
            Log.e(tag, msg);
        }
    }
}
