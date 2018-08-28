package com.syl.googleplay3.adapter;

import android.widget.AbsListView;

import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.base.SuperBaseAdapter;
import com.syl.googleplay3.bean.CategoryBean;
import com.syl.googleplay3.holder.CategoryNormalHolder;
import com.syl.googleplay3.holder.CategoryTitleHolder;

import java.util.List;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe
 * @Called
 */

public class CategoryAdapter extends SuperBaseAdapter<CategoryBean> {
    private BaseProtocol mProtocol;
    private List<CategoryBean> mCategoryBeans;

    public CategoryAdapter(AbsListView absListView, List<CategoryBean> dataSet, BaseProtocol protocol) {
        super(absListView, dataSet, protocol);
        mProtocol = protocol;
        mCategoryBeans = dataSet;
    }

    @Override
    public BaseHolder getSpecialHolder(int position) {
        CategoryBean categoryBean = mCategoryBeans.get(position);
        if (categoryBean.getIsTitle()) {
            return new CategoryTitleHolder();
        } else {
            return new CategoryNormalHolder();
        }
    }

    /**
     * 父类有两种情况,返回值是2.现在实际有3中情况,再+1
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getNormalItemViewType(int position) {
        CategoryBean categoryInfoBean = mCategoryBeans.get(position);
        if (categoryInfoBean.getIsTitle()) {
            return 1;
        } else {
            return 2;
        }
    }
}
