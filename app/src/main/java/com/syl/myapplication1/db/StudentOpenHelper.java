package com.syl.myapplication1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright on 2018/7/2.
 *
 * @Describe 数据库帮助类
 * @Called
 */

public class StudentOpenHelper extends SQLiteOpenHelper {
    public StudentOpenHelper(Context context) {
        super(context, "db_student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table students(_id integer primary key autoincrement,name varchar(20),gender varchar(4))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
