package com.syl.mobileplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.util.LogUtil;


/**
 * Created by ThinkPad on 2016/4/11.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(),getLayoutId(),null);
        initView();
        initListener();
        initData();
        regCommonBtn();
        return view;
    }

    private void regCommonBtn() {
        View back = view.findViewById(R.id.back);
        if(back!=null){
            back.setOnClickListener(this);
        }
    }

    //初始化view值
    protected abstract void initData();

    //设置listener  adapter
    protected abstract void initListener();

    //自定义findviewbyid
    public View findViewByid(int id){
        return view.findViewById(id);
    }
    //初始化view
    protected abstract void initView();

    //获取布局id
    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        //back按钮
        if (v.getId()==R.id.back){
            getFragmentManager().popBackStack();
        }else {
            //其他按钮
            processClick(v);
        }
    }
    //处理除了返回按钮之外的点击事件
    protected abstract void processClick(View v);
    public void logE(String msg){
        LogUtil.logE(getClass().getName(),msg);
    }
}
