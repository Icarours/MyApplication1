package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.TextView;

import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.CategoryBean;
import com.syl.googleplay3.utils.UIUtils;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe Title的Holder,Adapter 中 对getView()方法的抽取简化
 * @Called
 */

public class CategoryTitleHolder extends BaseHolder<CategoryBean> {

    private TextView mTv;

    @Override
    public View initHolderView() {
        //这个地方的TextView字体默认加载出来的颜色是白色的,背景也是白色的,如果不换一个字体颜色,看不出来加载的效果
        mTv = new TextView(UIUtils.getContext());
        mTv.setTextSize(18);
        mTv.setTextColor(UIUtils.getColor(R.color.colorAccent));
        int padding = UIUtils.dp2px(5);
        mTv.setPadding(padding, padding, padding, padding);
        return mTv;
    }

    @Override
    public void refreshHolderView(CategoryBean data) {
        mTv.setText(data.getTitle());
    }
}
