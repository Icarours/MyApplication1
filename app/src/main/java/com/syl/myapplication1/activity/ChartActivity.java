package com.syl.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.fragment.LineFragment;
import com.syl.myapplication1.fragment.LineFragment2;
import com.syl.myapplication1.fragment.LineFragment3;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/10/1 8:57
 * desc
 * MPAndroid统计图表
 */
public class ChartActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fl_chart)
    FrameLayout mFlChart;
    private String mTitle;
    private int mChart_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mChart_code = intent.getIntExtra("chart_code", 0);

        initToolBar();

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment chartFragment = null;
        switch (mChart_code) {
            case 0:
                chartFragment = new LineFragment();
                transaction.replace(R.id.fl_chart, chartFragment);
                transaction.commit();
                break;
            case 1:
                chartFragment = new LineFragment2();
                transaction.replace(R.id.fl_chart, chartFragment);
                transaction.commit();
                break;
            case 2:
                chartFragment = new LineFragment3();
                transaction.replace(R.id.fl_chart, chartFragment);
                transaction.commit();
                break;
            default:
                break;
        }
    }

    private void initToolBar() {
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChartActivity.this, "back-----", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(ChartActivity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(ChartActivity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(ChartActivity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(ChartActivity.this, "action_item_two", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
