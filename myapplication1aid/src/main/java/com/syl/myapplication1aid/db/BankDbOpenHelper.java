package com.syl.myapplication1aid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright on 2018/7/14.
 *
 * @Describe BankContentProvider对应的数据库帮助类
 * @Called
 */

public class BankDbOpenHelper extends SQLiteOpenHelper{
    public BankDbOpenHelper(Context context) {
        //创建数据库
        super(context, "bank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        String sql ="create table account(_id integer primary key autoincrement," +
                "name varchar(20)," +
                "money real)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
