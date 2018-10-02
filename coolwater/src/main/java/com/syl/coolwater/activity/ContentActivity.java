package com.syl.coolwater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.syl.coolwater.R;
import com.syl.coolwater.fragment.BaseFragment;
import com.syl.coolwater.fragment.GreenDaoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity {

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;
    private static final int BTN_GREEN_DAO = 1;//greenDao
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int fragment_code = intent.getIntExtra("fragment_code", 1);
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment baseFragment;
        switch (fragment_code) {
            default:
            case BTN_GREEN_DAO:
                baseFragment = new GreenDaoFragment();
                transaction.replace(R.id.fl_content, baseFragment);
                transaction.commit();
                break;
        }
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(ContentActivity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(ContentActivity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(ContentActivity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(ContentActivity.this, "action_item_two", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
