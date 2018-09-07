package com.syl.coolwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * author   Bright
 * date     2018/9/8 2:05
 * desc
 * Activity和Fragment之间传递数据
 */
public class Main1Activity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.button4)
    Button mButton4;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        mTvContent.setText("Main1Activity->Main2Activity\n" +
                "Main1Activity带着数据跳转到Main2Activity,Main2Activity中有两个Fragment,Fragment1和Fragment2,\n" +
                "由Main1Activity中带过来的数据中的state决定Main2Activity中是显示Fragment1还是Fragment2,state=0显示Fragment1,\n" +
                "state=1显示Fragment2,刚开始state=0.从Fragment1跳转到Main3Activity,Main3Activity处理数据成功,\n" +
                "返回resultCode,只要Main2Activity在onActivityResult()方法中收到requestCode,就改变flag,isOk=true.\n" +
                "然后在onStart()方法中修改mState=1,这样Fragment2就会代替Fragment1显示了..\n" +
                "\n" +
                "\n" +
                "还有一些要注意的地方:\n" +
                "1.Fragment1启动Main3Activity,Main3Activity finish()之后Fragment1和Main2Activity收到的resultCode是相同的,但是Fragment1和Main2Activity的requestCode是不同的;\n" +
                "2.Activity 中view的初始化放在onCreate()方法中进行,数据的初始化应该放在onStart()方法中进行,包括Fragment的生成和接收上一个Activity传递过来的数据");
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("test1", new Test1(100, 0, "张三"));
        System.out.println("Main1Activity-->Main2Activity");
        startActivity(intent);
    }
}
