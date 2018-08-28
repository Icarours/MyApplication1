package com.syl.myapplication1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright on 2018/7/3.
 *
 * @Describe 数据库帮助类
 * @Called
 */

public class UserAccountOpenHelper extends SQLiteOpenHelper {
    public UserAccountOpenHelper(Context context) {
        super(context, "db_user_account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//sqlite数据库中double对应的数据类型是real
        String sql = "create table useraccounts(_id integer primary key autoincrement,name varchar(20),money real)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
