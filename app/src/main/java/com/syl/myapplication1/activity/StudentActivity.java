package com.syl.myapplication1.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.db.dao.StudentDao;
import com.syl.myapplication1.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView的使用
 */
public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtName;
    private RadioGroup mRadioGroup;
    private TextView mTvInfo;
    private ListView mLv;
    private ArrayList<Student> mListStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mEtName = findViewById(R.id.et_name);
        mRadioGroup = findViewById(R.id.radioGroup);
        mTvInfo = findViewById(R.id.tv_info);
        mLv = findViewById(R.id.lv);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_add_more).setOnClickListener(this);
        findViewById(R.id.btn_query_all).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveStudent();
                break;
            case R.id.btn_query:
                queryStudent();
                break;
            case R.id.btn_add_more:
                addMore();
                break;
            case R.id.btn_query_all:
                queryAll();
                break;
            default:
                break;
        }
    }

    //查询所有学生信息
    private void queryAll() {
        refreshData();//刷新信息
    }

    //保存单个学生信息
    private void saveStudent() {
        Student student = new Student();
        int radioButtonId = mRadioGroup.getCheckedRadioButtonId();
        String name = mEtName.getText().toString();
        student.setName(name);
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "The name cannot be empty..", Toast.LENGTH_SHORT).show();
        }
        switch (radioButtonId) {
            case R.id.rb_male:
                student.setGender("男");
                break;
            case R.id.rb_female:
                student.setGender("女");
                break;
            default:
                break;
        }
        StudentDao sDao = new StudentDao(StudentActivity.this);
        boolean isAdd = sDao.addStudent(student);
        if (isAdd) {
            Toast.makeText(this, "学生信息保存成功..", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "学生信息保存失败..", Toast.LENGTH_SHORT).show();
        }
    }

    //查询单个学生信息
    private void queryStudent() {
        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "用户名不能为空..", Toast.LENGTH_SHORT).show();
        }
        StudentDao sDao = new StudentDao(StudentActivity.this);
        Student targetStudent = sDao.queryByName(name);
        if (targetStudent.getGender().equals("nan")) {
            mRadioGroup.check(R.id.rb_male);
        } else {
            mRadioGroup.check(R.id.rb_female);
        }
        mTvInfo.setText(targetStudent.getName() + "-" + targetStudent.getGender());
    }

    //插入多个数据,保存
    public void addMore() {
        StudentDao sdao = new StudentDao(StudentActivity.this);
        for (int i = 0; i < 500; i++) {
            Student student = new Student();
            student.setName("Lina" + i);
            if (i % 2 == 0) {
                student.setGender("男");
            } else {
                student.setGender("女");
            }
            sdao.addStudent(student);
        }
    }

    private class StudentAdapter extends BaseAdapter {
        private List<Student> mData;

        public StudentAdapter(List<Student> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mData != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(StudentActivity.this, R.layout.list_item_student, null);
                holder.ivGender = convertView.findViewById(R.id.iv_gender);
                holder.tvStudent = convertView.findViewById(R.id.tv_student);
                holder.iBtnDelete = convertView.findViewById(R.id.ibtn_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Student student = mData.get(position);
            if ("男".equals(student.getGender())) {
                holder.ivGender.setImageResource(R.mipmap.nan);
            } else {
                holder.ivGender.setImageResource(R.mipmap.nv);
            }
            holder.tvStudent.setText(student.getName() + "-" + student.getGender());
            holder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                    builder.setTitle("你确定要删除"+student.getName()+"的信息吗??")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteStudentItem(student);
                        }
                    }).setNegativeButton("返回",null)//如果用户点了返回按钮,就什么都不做.
                            .show();
                }
            });
            return convertView;
        }

        private void deleteStudentItem(Student student) {
            StudentDao studentDao = new StudentDao(StudentActivity.this);
            boolean isDelete = studentDao.deleteByName(student);
            if (isDelete) {//删除成功后,重新查询所有,刷新数据
                refreshData();
                Toast.makeText(StudentActivity.this, "删除成功..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(StudentActivity.this, "删除失败..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //刷新数据
    private void refreshData() {
        StudentDao studentDao = new StudentDao(StudentActivity.this);
        mListStudent = studentDao.queryAll();
        StudentAdapter studentAdapter = new StudentAdapter(mListStudent);
        mLv.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
//        studentAdapter.notifyDataSetInvalidated();
    }

    class ViewHolder {
        ImageView ivGender;
        TextView tvStudent;
        ImageButton iBtnDelete;
    }
}