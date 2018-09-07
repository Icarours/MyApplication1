package com.syl.coolwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.button3)
    Button mButton3;
    private Test1 mTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mTest1 = (Test1) intent.getSerializableExtra("test1");
        mTest1.setState(0);
        mButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("test1",mTest1);
        intent.putExtra("data","data");
        setResult(1001,intent);
        System.out.println("Main3Activity-->Main2Activity");
        finish();
    }
}
