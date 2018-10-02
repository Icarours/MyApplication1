package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.ToolbarActivity1;
import com.syl.myapplication1.activity.ToolbarActivity2;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/29.
 *
 * @Describe 弹出式菜单
 * @Called
 */
public class PopFragment extends BaseFragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    @Bind(R.id.btn_pop)
    Button mBtnPop;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.btn_toolbar1)
    Button mBtnToolbar1;
    @Bind(R.id.btn_toolbar2)
    Button mBtnToolbar2;
    private View mRootView;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        mRootView = View.inflate(getActivity(), R.layout.fragment_pop, null);
        ButterKnife.bind(this, mRootView);
        //设置多行文本显示
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText("弹出式菜单\nToolBar\n");
        mBtnPop.setOnClickListener(this);
        mBtnToolbar1.setOnClickListener(this);
        mBtnToolbar2.setOnClickListener(this);
        return mRootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_pop:
                initPopMenu(v);
                break;
            case R.id.btn_toolbar1:
                intent = new Intent(getActivity(),ToolbarActivity1.class);
                startActivity(intent);
                break;
            case R.id.btn_toolbar2:
                intent = new Intent(getActivity(),ToolbarActivity2.class);
                startActivity(intent);
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
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
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
                Toast.makeText(getActivity(), "退出..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(getActivity(), "设置..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                Toast.makeText(getActivity(), "账号..", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }
}
