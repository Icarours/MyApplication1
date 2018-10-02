package com.syl.coolwater.config;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.syl.coolwater.R;

public class BaseActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        mToolbar =findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(BaseActivity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(BaseActivity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(BaseActivity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(BaseActivity.this, "action_item_two", Toast.LENGTH_SHORT).show();
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

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
