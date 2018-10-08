package com.syl.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.fragment.BaseFragment;
import com.syl.myapplication1.fragment.CornersFragment;
import com.syl.myapplication1.fragment.RoundedCornerFragment;
import com.syl.myapplication1.fragment.RvDrawableFragment;
import com.syl.myapplication1.fragment.CircleFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/10/7 10:49
 * desc
 * Drawable, 图形和绘图.具体界面
 */
public class DrawableActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_normal)
    Toolbar mToolbarNormal;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.fl_drawable)
    FrameLayout mFlDrawable;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        mTitle = intent.getStringExtra("title");
        mTvContent.setText(mTitle);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        initFragment(position);

        initToolBar();
    }

    private void initToolBar() {
        mToolbarNormal.setTitle(mTitle);
        mToolbarNormal.inflateMenu(R.menu.menu_normal);
        mToolbarNormal.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(DrawableActivity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(DrawableActivity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(DrawableActivity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(DrawableActivity.this, "action_item_two", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mToolbarNormal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawableActivity.this, "您点击了左侧返回按钮", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment baseFragment;
        switch (position) {
            default:
            case 0:
                baseFragment = new RvDrawableFragment();
                transaction.replace(R.id.fl_drawable, baseFragment);
                transaction.commit();
                break;
            case 1:
                baseFragment = new CornersFragment();
                transaction.replace(R.id.fl_drawable, baseFragment);
                transaction.commit();
                break;
            case 2:
                baseFragment = new RoundedCornerFragment();
                transaction.replace(R.id.fl_drawable, baseFragment);
                transaction.commit();
                break;
            case 3:
                baseFragment = new CircleFragment();
                transaction.replace(R.id.fl_drawable, baseFragment);
                transaction.commit();
                break;
        }
    }
}
