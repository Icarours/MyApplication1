package com.syl.googleplay3.fragment;

import android.view.View;
import android.widget.ListView;

import com.syl.googleplay3.factory.ListViewFactory;
import com.syl.googleplay3.adapter.CategoryAdapter;
import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.CategoryBean;
import com.syl.googleplay3.protocol.CategoryProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bright on 2018/7/30.
 *
 * @Describe 分类
 * @Called
 */

public class CategoryFragment extends BaseFragment {

    private CategoryProtocol mProtocol;
    private List<CategoryBean> mCategoryBeans;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new CategoryAdapter(listView,mCategoryBeans,mProtocol));
        return listView;
    }

    /**
     * 在子线程中真正加载数据,外界触发加载数据时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        try {
            mProtocol = new CategoryProtocol();
            mCategoryBeans = mProtocol.loadData(0);
            return checkResData(mCategoryBeans);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
