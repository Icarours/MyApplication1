package com.syl.myapplication1.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.syl.myapplication1.db.StudentOpenHelper;
import com.syl.myapplication1.domain.Student;

import java.util.ArrayList;

/**
 * Created by Bright on 2018/7/2.
 *
 * @Describe 数据库帮助类
 * @Called
 */

public class StudentDao {

    StudentOpenHelper mHelper;

    public StudentDao(Context context) {
        mHelper = new StudentOpenHelper(context);
    }

    /**
     * add 学生,添加数据
     * @param student
     * @return
     */
    public boolean addStudent(Student student) {//操作数据库应该会抛异常,但是Google好像把异常都隐藏了
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "insert into students values(null,?,?)";
        db.execSQL(sql,new Object[]{student.getName(),student.getGender()});
        db.close();//释放资源
        return true;
    }

    public Student queryByName(String name) {
        Student student = null;
        String sname ="";
        String sgender ="";
        SQLiteDatabase database = mHelper.getWritableDatabase();
        String sql ="select * from students where name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{name});
        while (cursor.moveToNext()){
            sname = cursor.getColumnNames()[1];
            sgender = cursor.getColumnNames()[2];
            student = new Student(sname,sgender);
        }
        cursor.close();//释放资源
        database.close();
        return student;
    }

    public ArrayList<Student> queryAll() {
        ArrayList<Student> list = new ArrayList<>();
        Student student;
        SQLiteDatabase database = mHelper.getWritableDatabase();
        String sql ="select * from students";
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String sname = cursor.getString(1);//取出数据的时候特别注意,cursor.getString(columnIndex)
            String sgender = cursor.getString(2);
            student = new Student(sname,sgender);
            list.add(student);
        }
        System.out.println("cursor.getCount()"+cursor.getCount());
        cursor.close();//释放资源
        database.close();
        return list;
    }

    //删除成功,返回true;才做数据库应该会抛异常的.但是看样子,Google已经将异常处理了.
    public boolean deleteByName(Student name) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        String sql = "delete from students where name = ?";
        database.execSQL(sql,new Object[]{name.getName()});
        database.close();
        return true;
    }
}
