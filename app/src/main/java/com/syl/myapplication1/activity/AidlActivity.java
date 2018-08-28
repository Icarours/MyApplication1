package com.syl.myapplication1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.utils.IntentUtil;
import com.syl.myapplication1aid.PersonManager;
import com.syl.myapplication1aid.aidl.Person;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AidlActivity.class.getSimpleName();
    @Bind(R.id.btn_add_person)
    Button mBtnAddPerson;
    @Bind(R.id.tv_person)
    TextView mTvPerson;
    @Bind(R.id.btn_aidl_bind)
    Button mBtnAidlBind;
    @Bind(R.id.btn_aidl_display)
    Button mBtnAidlDisplay;
    @Bind(R.id.btn_aidl_unbind)
    Button mBtnAidlUnbind;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private PersonManager mPersonManager;
    private PersonConnection mConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        mBtnAddPerson.setOnClickListener(this);
        mBtnAidlBind.setOnClickListener(this);
        mBtnAidlDisplay.setOnClickListener(this);
        mBtnAidlUnbind.setOnClickListener(this);
        mTvPerson.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("AIDL进程间通信的一种解决方案.这是客户端,调用远程APP的方法;服务端是另一个APP:com.syl.myapplication1aid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aidl_bind:
                btnAidlBind();
                break;
            case R.id.btn_add_person:
                addPerson();
                break;
            case R.id.btn_aidl_display:
                displayPerson();
                break;
            case R.id.btn_aidl_unbind:
                btnUnbind();
                break;
            default:
                break;
        }
    }

    private void btnUnbind() {
        unbindService(mConn);
        mConn = null;
        Log.d(TAG, "mConn-- 解除绑定..");
    }

    private void displayPerson() {
        try {
            List<Person> personList = mPersonManager.getPersonList();
            Log.d(TAG, "personList---" + personList);
            mTvPerson.setText(personList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addPerson() {
        Random random = new Random();
        Person person = new Person("shixin" + random.nextInt(10), random.nextInt(10));
        try {
            mPersonManager.addPerson(person);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, person + "-- 添加成功..");
    }

    private void btnAidlBind() {
        Intent intent1 = new Intent();
        intent1.setAction("com.syl.PERSONMANAGERSERVICE");
        Intent intent = IntentUtil.createExplicitFromImplicitIntent(this, intent1);
        mConn = new PersonConnection();
        bindService(intent, mConn, BIND_AUTO_CREATE);
        Log.d(TAG, "mConn-- 绑定成功..");
    }

    class PersonConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPersonManager = PersonManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mConn = null;
        }
    }
}
