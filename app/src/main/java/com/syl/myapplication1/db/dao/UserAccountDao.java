package com.syl.myapplication1.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.syl.myapplication1.db.UserAccountOpenHelper;
import com.syl.myapplication1.domain.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/7/3.
 *
 * @Describe UserAccountDao数据库帮助类
 * @Called
 */

public class UserAccountDao {

    private final UserAccountOpenHelper mHelper;

    public UserAccountDao(Context context) {
        mHelper = new UserAccountOpenHelper(context);
    }

    public void insertData(UserAccount userAccount) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        String sql = "insert into useraccounts values(null,?,?)";
        database.execSQL(sql, new Object[]{userAccount.getName(), userAccount.getMoney()});
        database.close();//释放资源
    }

    public List<UserAccount> queryAll() {
        List<UserAccount> list = new ArrayList<>();
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from useraccounts", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            double money = cursor.getDouble(2);
            UserAccount userAccount = new UserAccount(name, money);
            list.add(userAccount);
        }
        database.close();//释放资源
        return list;
    }

    public void modify() {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        try {
            database.beginTransaction();//开启事务Transaction
            String sql1 = "update useraccounts set money = money-100 where name = ? ";
            database.execSQL(sql1, new Object[]{"Linda1"});
//            int i = 1 / 0;//
            String sql2 = "update useraccounts set money = money+100 where name = ? ";
            database.execSQL(sql2, new Object[]{"Linda2"});
            database.setTransactionSuccessful();//设置 标记成功的代码就会执行
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();//关闭事务
            database.close();//释放资源
        }
    }
}
