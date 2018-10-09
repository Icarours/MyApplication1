package com.syl.mobileplayer.util;

import android.database.Cursor;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class CursorLog {
    public static void cursorLog(Cursor cursor){
        if(cursor==null||cursor.getCount()==0) return;
        while (cursor.moveToNext()){
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                LogUtil.logE("videolist","key:"+cursor.getColumnName(i)+",value:"+cursor.getString(i));
            }
        }
    }
}
