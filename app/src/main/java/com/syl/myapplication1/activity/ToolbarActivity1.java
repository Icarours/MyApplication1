package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2018/9/29 23:30
 * desc
 * ToolBar1,常用的ToolBar
 */
public class ToolbarActivity1 extends AppCompatActivity {

    @Bind(R.id.toolbar_normal)
    Toolbar mToolbarNormal;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有Title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//如果Activity继承的是Activity
        setContentView(R.layout.activity_toolbar1);
        ButterKnife.bind(this);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText("1.弹出式菜单\n" +
                "2.Toolbar的简单用法\n" +
                "3.使用Android Studio自带的图片\n" +
                "使用Android studio自带的图标\n" +
                "\n" +
                "Vector Asset\n" +
                "1.在资源文件夹res/drawable下右键->new->Vector Asset\n" +
                "2.name:ic_arrow_back_black_24dp\t指定图标名称\n" +
                "Clip Art:指定,选择自己需要的图标\n" +
                "Size:指定图标尺寸,如果要自己重新指定尺寸,必须勾选Override\n" +
                "Color:指定图标的颜色\n" +
                "\n" +
                "\n" +
                "\n" +
                "Vector Image\n" +
                "1.在资源文件夹res/drawable下右键->new->Vector Image\n" +
                "2.name:ic_arrow_back_black_24dp\t指定图标名称\n" +
                "Clip Art:指定,选择自己需要的图标,一定要选择Clip Art\n" +
                "Color:指定图标的颜色\n");
        mToolbarNormal.inflateMenu(R.menu.menu_normal);
        mToolbarNormal.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(ToolbarActivity1.this, "action_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        Toast.makeText(ToolbarActivity1.this, "action_notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_one:
                        Toast.makeText(ToolbarActivity1.this, "action_item_one", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_two:
                        Toast.makeText(ToolbarActivity1.this, "action_item_two", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ToolbarActivity1.this, "您点击了左侧返回按钮", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
