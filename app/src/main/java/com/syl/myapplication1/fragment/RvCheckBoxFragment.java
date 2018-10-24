package com.syl.myapplication1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.CheckBoxBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.utils.Log;

/**
 * Created by Bright on 2018/10/24.
 *
 * @Describe RecycleView中带有CheckBox的RecycleView,无法根据CheckBox的状态改变对应的bean数据
 * @Called
 */
public class RvCheckBoxFragment extends BaseFragment {
    @Bind(R.id.rv_check_box)
    RecyclerView mRvCheckBox;
    //    @Bind(R.id.tv_content)
//    TextView mTvContent;
    private CheckBoxAdapter mCheckBoxAdapter;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rv_check_box, null);
        ButterKnife.bind(this, rootView);
//        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvCheckBox.setLayoutManager(layoutManager);

        mCheckBoxAdapter = new CheckBoxAdapter(R.layout.rv_item_check_box, generateData());
        mRvCheckBox.setAdapter(mCheckBoxAdapter);
        mCheckBoxAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//开启缩放动画
        mCheckBoxAdapter.isFirstOnly(true);//true,第一次加载数据时有动画;false,每次加载数据时都有动画

        mCheckBoxAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {//加载更多的时候延迟两秒,这样看起来更舒服
                    @Override
                    public void run() {
                        mCheckBoxAdapter.addData(generateMoreData());
                        mCheckBoxAdapter.loadMoreComplete();
                    }
                }, 2000);
            }
        }, mRvCheckBox);

        mCheckBoxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), position + "--was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class CheckBoxAdapter extends BaseQuickAdapter<CheckBoxBean, BaseViewHolder> {
        public CheckBoxAdapter(int layoutResId, @Nullable List<CheckBoxBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final CheckBoxBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_content, item.getContent());
            final CheckBox checkBox = helper.getView(R.id.cb);
            checkBox.setChecked(item.isChecked());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setChecked(checkBox.isChecked());
                    Log.d("isChecked=="+isChecked+item);
                }
            });
        }
    }

    public List<CheckBoxBean> generateData() {
        List<CheckBoxBean> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CheckBoxBean checkBoxBean = new CheckBoxBean(i, "title--" + i, "content--" + i, false);
            list.add(checkBoxBean);
        }
        return list;
    }

    public List<CheckBoxBean> generateMoreData() {
        List<CheckBoxBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CheckBoxBean checkBoxBean = new CheckBoxBean(i, "more title--" + i, "more content--" + i, false);
            list.add(checkBoxBean);
        }
        return list;
    }
}
