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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by j3767 on 2016/12/10.
 *
 * @Describe
 * @Called
 */
public class DetailInfoHolder extends BaseHolder<ItemInfoBean> {

    @Bind(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @Bind(R.id.app_detail_info_tv_name)
    TextView mAppDetailInfoTvName;
    @Bind(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @Bind(R.id.app_detail_info_tv_downloadnum)
    TextView mAppDetailInfoTvDownloadnum;
    @Bind(R.id.app_detail_info_tv_version)
    TextView mAppDetailInfoTvVersion;
    @Bind(R.id.app_detail_info_tv_time)
    TextView mAppDetailInfoTvTime;
    @Bind(R.id.app_detail_info_tv_size)
    TextView mAppDetailInfoTvSize;
    public View mHolderView;

    @Override
    public View initHolderView() {
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        ButterKnife.bind(this, mHolderView);
        return mHolderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {

        String date = UIUtils.getResource().getString(R.string.detail_downloadnum, data.getDate());
        String downloadNum = UIUtils.getResource().getString(R.string.detail_date, data.getDownloadNum());
        String size = UIUtils.getResource().getString(R.string.detail_size, StringUtils.formatFileSize(data.getSize()));
        String version = UIUtils.getResource().getString(R.string.detail_version, data.getVersion());

        mAppDetailInfoTvDownloadnum.setText(downloadNum);
        mAppDetailInfoTvName.setText(data.getName());
        mAppDetailInfoTvSize.setText(size);
        mAppDetailInfoTvTime.setText(date);
        mAppDetailInfoTvVersion.setText(version);

        //评分
        mAppDetailInfoRbStar.setRating(Float.parseFloat(data.getStars()+""));
        //图标加载
        Glide.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + data.getIconUrl()).into(mAppDetailInfoIvIcon);
    }
}
