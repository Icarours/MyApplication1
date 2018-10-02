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
import com.syl.coolwater.fragment.GaodeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentActivity2 extends AppCompatActivity {
    private static final int BTN_GAODE_MAP = 1;//高德地图
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int fragment_code = intent.getIntExtra("fragment_code", 1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment baseFragment;
        switch (fragment_code) {
            default:
            case BTN_GAODE_MAP:
                baseFragment = new GaodeFragment();
                transaction.replace(R.id.fl_content,baseFragment);
                transaction.commit();
                break;
        }
        mToolbar.setTitle(title);
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(ContentActivity2.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(ContentActivity2.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(ContentActivity2.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(ContentActivity2.this, "action_item_two", Toast.LENGTH_SHORT).show();
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
