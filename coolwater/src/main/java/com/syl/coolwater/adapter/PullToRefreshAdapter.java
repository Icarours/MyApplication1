package com.syl.coolwater.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.syl.coolwater.R;
import com.syl.coolwater.bean.WarnMessage;

import java.util.List;

/**
 * Created by Bright on 2018/11/25.
 *
 * @Describe
 * @Called
 */
public class PullToRefreshAdapter extends BaseQuickAdapter<WarnMessage, BaseViewHolder> {

    public PullToRefreshAdapter(int layoutResId, @Nullable List<WarnMessage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WarnMessage item) {
        helper.setText(R.id.tv_camera_id, item.getCamera_id());
        helper.setText(R.id.tv_id,item.getId());
        helper.setText(R.id.tv_warn_type,item.getWarn_picture());
        helper.setText(R.id.tv_create_time,item.getCreate_time());
        ImageView imageView = helper.getView(R.id.iv_warn_picture);
    }
}
