package com.syl.myapplication1aid.service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1aid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/8/22 14:11
 * desc
 * 测试butterknife:7.0.1',发现可以正常使用,有的module可能是由于某些R文件的问题导致butterknife快捷键不能正常工作,只要clean一下project就可以了
 */
public class Main3Activity extends AppCompatActivity {


    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
    }
}
