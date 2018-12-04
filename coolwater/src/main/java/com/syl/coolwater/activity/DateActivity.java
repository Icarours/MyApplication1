package com.syl.coolwater.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;

import com.syl.coolwater.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DateActivity extends AppCompatActivity {

    @Bind(R.id.dp)
    DatePicker mDp;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    @Bind(R.id.tv_date2)
    TextView mTvDate2;
    @Bind(R.id.tv_date3)
    TextView mTvDate3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        ButterKnife.bind(this);
        mDp.init(2018, 11, 4, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();//创建日历对象,获取当前的日期,时间
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年YY月dd日 HH:mm");
                DateFormat dateInstance = SimpleDateFormat.getDateInstance();
                String format1 = dateInstance.format(calendar.getTime());
                mTvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                mTvDate2.setText(format.format(calendar.getTime()));
                mTvDate3.setText(format1);
            }
        });
    }
}
