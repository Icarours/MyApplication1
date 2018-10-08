package com.syl.myapplication1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.activity.DrawableActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/10/7.
 *
 * @Describe Drawable, 图形和绘图
 * @Called
 */
public class DrawableFragment extends BaseFragment {
    @Bind(R.id.rv_drawable)
    RecyclerView mRvDrawable;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private List<String> mTitle = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        mTitle.add("渐变的ListView行");
        mTitle.add("圆角视图组");
        mTitle.add("圆角位图");
        mTitle.add("圆形遮罩");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawable, null);
        ButterKnife.bind(this, rootView);

        mTvContent.setText("Drawable, 图形和绘图");
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDrawable.setLayoutManager(manager);
        DrawableAdapter adapter = new DrawableAdapter(R.layout.list_item_single_text, mTitle);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), DrawableActivity.class);
                intent.putExtra("title", mTitle.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        mRvDrawable.setAdapter(adapter);
        return rootView;
    }

    class DrawableAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public DrawableAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
