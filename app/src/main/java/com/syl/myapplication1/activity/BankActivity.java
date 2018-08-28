package com.syl.myapplication1.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义内容提供者客户端(获取信息)
 */
public class BankActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BankActivity.class.getSimpleName();
    @Bind(R.id.btn_bank_add)
    Button mBtnBankAdd;
    @Bind(R.id.btn_bank_delete)
    Button mBtnBankDelete;
    @Bind(R.id.btn_bank_update)
    Button mBtnBankUpdate;
    @Bind(R.id.btn_bank_query)
    Button mBtnBankQuery;
    @Bind(R.id.tv_content)
    TextView mTv;
    @Bind(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        mBtnBankAdd.setOnClickListener(this);
        mBtnBankDelete.setOnClickListener(this);
        mBtnBankUpdate.setOnClickListener(this);
        mBtnBankQuery.setOnClickListener(this);
        mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("自定义内容提供者客户端(获取信息),服务端(提供信息者)在另一个APP:com.syl.myapplication1aid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bank_add:
                bankAdd();
                break;
            case R.id.btn_bank_delete:
                bankDelete();
                break;
            case R.id.btn_bank_update:
                bankUpdate();
                break;
            case R.id.btn_bank_query:
                bankQuery();
                break;
            default:
                break;
        }
    }

    private void bankQuery() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            Log.d(TAG, "name=" + name + ";money=" + money);
            sb.append("name=" + name + ";money=" + money + "\r\n");

        }
        cursor.close();
        mTv.setText(sb.toString());
    }

    /**
     * 更新数据的时候最好先查一下要更新的目标数据存不存在
     */
    private void bankUpdate() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        ContentValues values = new ContentValues();
        values.put("money", 1000);
        resolver.update(uri, values, "name=?", new String[]{"zhangsan0"});
        Log.d(TAG, "数据更新成功..");
    }

    private void bankDelete() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        resolver.delete(uri, "name = ?", new String[]{"zhangsan1"});
        Log.d(TAG, "数据删除成功..");
    }

    private void bankAdd() {
        //获得内容解析者
        ContentResolver resolver = getContentResolver();
        //获得Uri
        Uri uri = Uri.parse("content://com.syl.myapplication1aid/account");
        Random random = new Random();
        for (int i = 10; i < 20; i++) {
            ContentValues values = new ContentValues();
            values.put("name", "zhangsan" + i);
            values.put("money", random.nextLong() + 10);
            resolver.insert(uri, values);
        }
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("money", 100.00);
        resolver.insert(uri, values);
        Log.d(TAG, "数据添加成功..");
    }
}
