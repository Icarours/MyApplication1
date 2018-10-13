package com.syl.myapplication1.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syl.myapplication1.R;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/11.
 *
 * @Describe
 * @Called 获取当前手机的参数
 */
public class MobileFragment extends BaseFragment {
    public static final String TAG = MobileFragment.class.getSimpleName();
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private StringBuffer mSb;

    @Override
    protected void init() {
        mSb = new StringBuffer();
        mSb.append("当前手机的参数\n\n");

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field :
                fields) {
            try {
                field.setAccessible(true);
                mSb.append("Build." + field.getName() + "===" + field.get(null)+"\n");
                Log.d(TAG, "Build." + field.getName() + "===" + field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mSb.append("+++++++++++++++\n");
        Field[] fieldsVersion = Build.VERSION.class.getDeclaredFields();
        for (Field field : fieldsVersion) {
            try {
                field.setAccessible(true);
                Log.d(TAG, "Build.VERSION." + field.getName() + " === " + field.get(null));
                mSb.append("Build.VERSION." + field.getName() + " === " + field.get(null)+"\n");
            } catch (Exception e) {
                Log.e(TAG, "an error occurred when collect crash info", e);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mobile, null);
        ButterKnife.bind(this, rootView);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText(mSb.toString());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
