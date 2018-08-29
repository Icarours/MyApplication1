package com.syl.coolwater.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.syl.coolwater.R;
import com.syl.coolwater.fragment.GreenDaoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity {

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,new GreenDaoFragment());
        transaction.commit();
    }
}
