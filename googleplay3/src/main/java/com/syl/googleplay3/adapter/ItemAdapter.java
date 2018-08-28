package com.syl.googleplay3.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.syl.googleplay3.activity.DetailActivity;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.base.SuperBaseAdapter;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.holder.ItemInfoHolder;
import com.syl.googleplay3.manager.DownloadManger;
import com.syl.googleplay3.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * @Called
 */

public class ItemAdapter extends SuperBaseAdapter<ItemInfoBean> {
    private BaseProtocol mBaseProtocol;//这儿的HomeProtocol不需要加泛型
    private List<ItemInfoBean> mItemInfoBeans;
    public List<ItemInfoHolder> mItemInfoHolders = new ArrayList<>();
    public ItemAdapter(AbsListView absListView, List dataSet,BaseProtocol baseProtocol) {
        super(absListView, dataSet);
        mBaseProtocol = baseProtocol;
        mItemInfoBeans = dataSet;
    }

    @Override
    public BaseHolder getSpecialHolder(int position) {
        ItemInfoHolder mItemInfoHolder = new ItemInfoHolder();
        //添加观察者到集合中
        mItemInfoHolders.add(mItemInfoHolder);
        DownloadManger.getInstance().addObserver(mItemInfoHolder);
        return mItemInfoHolder;
    }

    /**
     * Home页面有加载更多
     *
     * @return
     */
    @Override
    public boolean hasLoadMore() {
        return true;
    }

    /**
     * 条目点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        String packageName = ((ItemInfoBean) mDataSet.get(position)).getPackageName();
        Toast.makeText(UIUtils.getContext(), packageName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        UIUtils.getContext().startActivity(intent);
    }
}
