package com.syl.coolwater;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.syl.coolwater.factory.FragmentFactory;
import com.syl.coolwater.view.BottomNavigationViewHelper;
import com.syl.coolwater.fragment.ContentFragment1;
import com.syl.coolwater.fragment.ContentFragment2;
import com.syl.coolwater.fragment.ContentFragment3;
import com.syl.coolwater.fragment.ContentFragment4;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_framework:
                    transaction.replace(R.id.fl_main,new ContentFragment1());
                    transaction.commit();
                    return true;
                case R.id.navigation_home:
                    transaction.replace(R.id.fl_main,new ContentFragment2());
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.fl_main,new ContentFragment3());
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.fl_main,new ContentFragment4());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //压制BottomNavigationView自带的动画效果,自带的动画效果不好.当前选中的按钮会压缩其他按钮的显示空间
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //使用Fragment替换FrameLayout,默认显示第一个content_fragment1
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main, FragmentFactory.createFragment("content_fragment1"));
        transaction.commit();
    }

}
