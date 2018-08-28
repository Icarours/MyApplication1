package com.syl.myapplication1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright on 2018/7/5.
 *
 * @Describe upFile帮助类
 * @Called
 */

public class UpFileOpenHelper extends SQLiteOpenHelper {
    public UpFileOpenHelper(Context context) {
        super(context, "db_file.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table upfiles(\n" +
                "\tint id primary key autoincrement,\n" +
                "\tuuidname varchar(150),\n" +
                "\tfilename varchar(100),\n" +
                "\tsavepath`varchar(255),\n" +
                "\tuploadtime timestamp,\n" +
                "\tusername varchar(20)\n" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
