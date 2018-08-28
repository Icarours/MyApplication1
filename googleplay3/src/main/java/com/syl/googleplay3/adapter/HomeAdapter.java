package com.syl.googleplay3.adapter;

import android.os.SystemClock;
import android.widget.AbsListView;

import com.syl.googleplay3.bean.HomeBean;
import com.syl.googleplay3.protocol.HomeProtocol;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * @Called
 */

public class HomeAdapter extends ItemAdapter {
    private HomeProtocol mBaseProtocol;//这儿的HomeProtocol不需要加泛型
    private List<HomeBean> mItemInfoBeans;

    public HomeAdapter(AbsListView absListView, List dataSet, HomeProtocol homeProtocol) {
        super(absListView, dataSet, homeProtocol);
        mBaseProtocol = homeProtocol;
        mItemInfoBeans = dataSet;
    }

    /**
     * 在子线程中真正加载数据
     * @return
     * @throws Exception
     */
    @Override
    public List onLoadMoreData() throws Exception {
        SystemClock.sleep(1000);
        HomeBean homeBean = mBaseProtocol.loadData(mItemInfoBeans.size());
        return homeBean.getList();
    }

}
