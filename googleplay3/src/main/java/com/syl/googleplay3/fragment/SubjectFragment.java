package com.syl.googleplay3.fragment;

import android.view.View;
import android.widget.ListView;

import com.syl.googleplay3.factory.ListViewFactory;
import com.syl.googleplay3.adapter.SubjectAdapter;
import com.syl.googleplay3.base.BaseFragment;
import com.syl.googleplay3.base.LoadingPager;
import com.syl.googleplay3.bean.SubjectInfoBean;
import com.syl.googleplay3.protocol.SubjectProtocol;

import java.io.IOException;
import java.util.List;


/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 分类
 * @Called
 */

public class SubjectFragment extends BaseFragment {

    private SubjectProtocol mProtocol;
    private List<SubjectInfoBean> mSubjectInfoBeans;

    /**
     * 决定成功视图长什么样子,为成功视图绑定数据
     *
     * @return
     */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new SubjectAdapter(listView,mSubjectInfoBeans,mProtocol));
        return listView;
    }

    /**
     * 在子线程中加载数据,外界触发加载时调用
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResult initData() {
        try {
            mProtocol = new SubjectProtocol();
            mSubjectInfoBeans = mProtocol.loadData(0);
            return checkResData(mSubjectInfoBeans);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }
}
