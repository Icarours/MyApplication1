package com.syl.googleplay3.fragment;

import android.view.View;
import android.widget.ListView;

import com.syl.googleplay3.adapter.GameAdapter;
import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.factory.ListViewFactory;
import com.syl.googleplay3.protocol.GameProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe 游戏
 * @Called
 */

public class GameFragment extends BaseFragment {

    private GameProtocol mProtocol;
    private List<ItemInfoBean> mItemInfoBeans;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new GameAdapter(listView,mItemInfoBeans,mProtocol));
        return listView;
    }

    /**
     * 在子线程中加载数据,外界触发加载数据时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        mProtocol = new GameProtocol();
        try {
            mItemInfoBeans = mProtocol.loadData(0);
            return checkResData(mItemInfoBeans);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
