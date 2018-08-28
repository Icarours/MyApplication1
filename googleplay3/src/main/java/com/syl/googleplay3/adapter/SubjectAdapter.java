package com.syl.googleplay3.adapter;

import android.os.SystemClock;
import android.widget.AbsListView;

import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.base.SuperBaseAdapter;
import com.syl.googleplay3.bean.SubjectInfoBean;
import com.syl.googleplay3.holder.SubjectHolder;

import java.util.List;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe
 * @Called
 */

public class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean> {
    private BaseProtocol<List<SubjectInfoBean>> mProtocol;
    private List<SubjectInfoBean> mSubjectInfoBeans;

    public SubjectAdapter(AbsListView absListView, List<SubjectInfoBean> dataSet, BaseProtocol protocol) {
        super(absListView, dataSet, protocol);
        mProtocol = protocol;
        mSubjectInfoBeans = dataSet;
    }

    @Override
    public BaseHolder getSpecialHolder(int position) {
        return new SubjectHolder();
    }

    /**
     * 决定是否加载更多
     * @return
     */
    @Override
    public boolean hasLoadMore() {
        return true;
    }

    /**
     * 加载更多的具体内容
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectInfoBean> onLoadMoreData() throws Exception {
        SystemClock.sleep(1000);
        if (mSubjectInfoBeans != null) {
            return mProtocol.loadData(mSubjectInfoBeans.size());
        }
        return super.onLoadMoreData();
    }
}
