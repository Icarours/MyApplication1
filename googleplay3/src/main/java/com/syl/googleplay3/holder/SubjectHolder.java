package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.SubjectInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe 返回ListView条目的视图
 * @Called
 */

public class SubjectHolder extends BaseHolder<SubjectInfoBean> {
    @Bind(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @Bind(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        ButterKnife.bind(this,holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(SubjectInfoBean data) {
        mItemSubjectTvTitle.setText(data.getDes());
        Glide.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + data.getUrl()).into(mItemSubjectIvIcon);
    }
}
