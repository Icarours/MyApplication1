package com.syl.myapplication1.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.master.permissionhelper.PermissionHelper;
import com.syl.myapplication1.R;
import com.syl.myapplication1.factory.FragmentFactory;
import com.syl.myapplication1.view.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //使用Fragment替换FrameLayout
                    transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment1"));
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment2"));
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment3"));
                    transaction.commit();
                    return true;
                case R.id.navigation_setting:
                    transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment4"));
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };
    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请权限//读取内存卡的权限//读取短信的权限//打电话的权限//联系人权限
        mPermissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS,
                Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, 100);
        mPermissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Log.d(TAG, "onPermissionGranted() called");
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                Log.d(TAG, "onIndividualPermissionGranted() called with: grantedPermission = [" + TextUtils.join(",",grantedPermission) + "]");
            }

            @Override
            public void onPermissionDenied() {
                Log.d(TAG, "onPermissionDenied() called");
            }

            @Override
            public void onPermissionDeniedBySystem() {
                Log.d(TAG, "onPermissionDeniedBySystem() called");
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //压制BottomNavigationView自带的动画效果,自带的动画效果不好.当前选中的按钮会压缩其他按钮的显示空间
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //使用Fragment替换FrameLayout,默认显示第一个content_fragment1
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment1"));
        transaction.commit();
    }

    //获得授权的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
