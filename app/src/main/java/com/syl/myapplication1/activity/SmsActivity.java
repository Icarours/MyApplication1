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
import android.widget.ListView;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.adapter.SmsAdapter;
import com.syl.myapplication1.domain.SmsEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 获得短信的内容提供者
 */
public class SmsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SmsActivity.class.getSimpleName();
    @Bind(R.id.btn_sms_add)
    Button mBtnSmsAdd;
    @Bind(R.id.btn_sms_delete)
    Button mBtnSmsDelete;
    @Bind(R.id.btn_sms_query)
    Button mBtnSmsQuery;
    @Bind(R.id.lv_sms)
    ListView mLvSms;
    @Bind(R.id.tv_content)
    TextView mTv;
    private List<SmsEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        mBtnSmsAdd.setOnClickListener(this);
        mBtnSmsDelete.setOnClickListener(this);
        mBtnSmsQuery.setOnClickListener(this);
        mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTv.setText("Provider 内容提供者,获取手机短信");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sms_add:
                smsAdd();
                break;
            case R.id.btn_sms_delete:
                smsDelete();
                break;
            case R.id.btn_sms_query:
                smsQuery();
                break;
            default:
                break;
        }
    }

    private void smsQuery() {
        ContentResolver resolver = getContentResolver();
        //后面的path不写，默认就是inbox 收件箱
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = resolver.query(uri, new String[]{"address", "type", "body", "date"}, null, null, null);
        SmsEntity smsEntity;
        while (cursor.moveToNext()) {
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println(format.format(System.currentTimeMillis()));
            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
            Date date1 = new Date(Long.parseLong(date));//先把String型的日期转换为long型,再转换为Date型数据
            String date2 = dateFormat.format(date1);
            smsEntity = new SmsEntity(body, date2, type, address);
            mList.add(smsEntity);
        }
        SmsAdapter adapter = new SmsAdapter(this, mList);
        mLvSms.setAdapter(adapter);
        Log.d(TAG, "查询成功..");
    }

    private void smsDelete() {
        ContentResolver resolver = getContentResolver();
        //后面的path不写，默认就是inbox 收件箱
        Uri uri = Uri.parse("content://sms");
        resolver.delete(uri, "address=?", new String[]{"100"});
        Log.d(TAG, "短信删除成功..");
    }

    private void smsAdd() {
        ContentResolver resolver = getContentResolver();
        //后面的path不写，默认就是inbox 收件箱
        Uri uri = Uri.parse("content://sms");
        ContentValues values = new ContentValues();
        values.put("body", "hello world..");//短信内容
        values.put("address", "100");//收信人或者发信人地址
        values.put("type", 1);//是发送的短信？ 收的短信
        values.put("date", System.currentTimeMillis());//时间
        resolver.insert(uri, values);
        Log.d(TAG, "短信添加成功..");
    }
}
