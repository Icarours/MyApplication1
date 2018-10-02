package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.view.SimpleToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * author   Bright
 * date     2018/9/30 22:36
 * desc
 * 测试自定义的ToolBar
 */
public class ToolbarActivity2 extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    @Bind(R.id.simple_toolbar)
    SimpleToolbar mSimpleToolbar;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_toolbar2);
        ButterKnife.bind(this);
        mSimpleToolbar.setMainTitle("中间标题");
        mSimpleToolbar.setLeftTitleClickListener(this);
        mSimpleToolbar.setRightTitleClickListener(this);

        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText("这个自定义的ToolBar不好使,还是原生的ToolBar好用,以后导航栏就用原生的ToolBar了.");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left_title:
                finish();
                break;
            case R.id.txt_right_title:
                initPopMenu(v);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化弹出式菜单
     *
     * @param v
     */
    private void initPopMenu(View v) {
        //创建弹出式菜单,
        PopupMenu popupMenu = new PopupMenu(this, v);
        //获取菜单填充器
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        //填充菜单
        menuInflater.inflate(R.menu.menu_pop, popupMenu.getMenu());
        //设置菜单点击事件
        popupMenu.setOnMenuItemClickListener(this);
        //显示菜单
        popupMenu.show();
    }

    /**
     * 菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Toast.makeText(this, "退出..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(this, "设置..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                Toast.makeText(this, "账号..", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }
}
