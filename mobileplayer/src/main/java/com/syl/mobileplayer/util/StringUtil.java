package com.syl.mobileplayer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class StringUtil {
    private static final int HOUR = 60 * 60 * 1000;
    private static final int MIN = 60 * 1000;
    private static final int SEC = 1000;

    public static String parseDuration(int duration) {
        int hour = duration / HOUR;
        int min = duration % HOUR / MIN;
        int sec = duration % HOUR % MIN / SEC;
        if (hour == 0) {
            return String.format("%02d:%02d", min, sec);
        } else {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    //返回当前的时间
    public static String getCurrentTiem() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }
}
