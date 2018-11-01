package com.syl.myapplication1.activity;

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

import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.TitleBean;
import com.syl.myapplication1.fragment.BaseFragment;
import com.syl.myapplication1.fragment.CameraFragment;
import com.syl.myapplication1.fragment.SearchViewFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/10/27 19:40
 * desc 底部按钮,第三个按钮对应的内容
 *ContentFragment3->Content3Activity
 */
public class Content3Activity extends AppCompatActivity {

    private static final String TAG = Content3Activity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fl_content3)
    FrameLayout mFlContent3;
    private TitleBean mTitleBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content3);
        ButterKnife.bind(this);

        mTitleBean = (TitleBean) getIntent().getSerializableExtra("bean");
        initToolBar();
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = mTitleBean.getId();
        BaseFragment fragment;
        switch (id) {
            default:
            case 0:
                fragment = new CameraFragment();
                transaction.replace(R.id.fl_content3, fragment);
                break;
            case 1:
                fragment = new SearchViewFragment();
                transaction.replace(R.id.fl_content3, fragment);
                break;
        }
        transaction.commit();
    }

    private void initToolBar() {
        mToolbar.setTitle(mTitleBean.getTitle());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_normal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(Content3Activity.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(Content3Activity.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(Content3Activity.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(Content3Activity.this, "action_item_two", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

}
