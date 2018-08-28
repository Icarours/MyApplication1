package com.syl.googleplay3.fragment;

import android.view.View;
import android.widget.ListView;

import com.syl.googleplay3.adapter.AppAdapter;
import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.factory.ListViewFactory;
import com.syl.googleplay3.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;


/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 应用
 * @Called
 */

public class AppFragment extends BaseFragment {

    private AppProtocol mAppProtocol;
    private List<ItemInfoBean> mAppItemInfoBeans;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.createListView();
        lv.setAdapter(new AppAdapter(lv,mAppItemInfoBeans,mAppProtocol));
        return lv;
    }

    /**
     * 在子线程中真正加载数据,外界触发加载数据时调用
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        mAppProtocol = new AppProtocol();
        try {
            mAppItemInfoBeans = mAppProtocol.loadData(0);
            return checkResData(mAppItemInfoBeans);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
