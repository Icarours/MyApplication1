package com.syl.coolwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @Bind(R.id.et_content)
    EditText mEtContent;
    @Bind(R.id.fl_content)
    FrameLayout mFlContent;
    private int mState;
    boolean isOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        Log.d("tag", "--onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag", "--onStart()");

        Intent intent = getIntent();
        Test1 test1 = (Test1) intent.getSerializableExtra("test1");
        mState = test1.getState();
        if (isOk) {
            mState = 1;
        }
        initData();
    }

    private void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mState == 0) {
            transaction.replace(R.id.fl_content, new Fragment1());
            System.out.println("Main2Activity--Fragment1");
        } else {
            transaction.replace(R.id.fl_content, new Fragment2());
            System.out.println("Main2Activity--Fragment2");
        }
//        getIntent().removeExtra("test1");
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag", "--onResume()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        getIntent().removeExtra("test1");
        Log.d("tag", "--onActivityResult()");
        System.out.println("Main2Activity---" + "onActivityResult--resultCode==" + resultCode + "---requestCode==" + requestCode);
        if (resultCode == 1001) {
            isOk = true;
            System.out.println(intent.getStringExtra("data"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "--onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "--onDestroy()");
    }
}
