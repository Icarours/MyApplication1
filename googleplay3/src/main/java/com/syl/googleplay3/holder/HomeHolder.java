package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.StringUtils;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe 1.提供视图, 2, 提供数据, 3.视图和数据的绑定
 * @Called
 */

public class HomeHolder extends BaseHolder<ItemInfoBean> {
    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @Bind(R.id.item_appinfo_progressview)
    ProgressView mItemAppinfoProgressview;
    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    //初始化视图
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_info, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    //视图和数据的绑定
    @Override
    public void refreshHolderView(ItemInfoBean itemInfoBean) {
        //data 局部变量
        //view 成员变量
        //view+data
        mItemAppinfoTvDes.setText(itemInfoBean.getDes());
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(itemInfoBean.getSize()));
        mItemAppinfoTvTitle.setText(itemInfoBean.getName());

        //重置itemInfoBean中progress的进度
        mItemAppinfoProgressview.setProgress(0);

        //评分的展示
        mItemAppinfoRbStars.setRating(itemInfoBean.getStars());
        //图片的加载
        Glide.with(UIUtils.getContext())
                .load(Constants.URLS.IMAGEBASEURL + itemInfoBean.getIconUrl())
                .into(mItemAppinfoIvIcon);
    }
}
