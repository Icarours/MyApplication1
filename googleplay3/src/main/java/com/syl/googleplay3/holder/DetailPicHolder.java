package com.syl.googleplay3.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.ItemInfoBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.UIUtils;
import com.syl.googleplay3.view.RatioLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by j3767 on 2016/12/10.
 *
 * @Describe
 * @Called
 */
public class DetailPicHolder extends BaseHolder<ItemInfoBean> {

    @Bind(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        List<String> screenUrls = data.getScreen();
        for (int i = 0; i < screenUrls.size(); i++) {
            ImageView iv = new ImageView(UIUtils.getContext());
            String url = screenUrls.get(i);
            Glide.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + url).into(iv);

            RatioLayout rl = new RatioLayout(UIUtils.getContext());
            rl.addView(iv);
            //设置图片的宽高比
            rl.mPicRatio = 150 * 1.0f / 250;
            //设置相对于谁计算
            rl.mRelative = RatioLayout.RELATIVE_WIDTH;
            int screenWidth = UIUtils.getResource().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth - UIUtils.dp2px(8);

            int width = screenWidth / 3;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {//如果不是第一张图片
                params.leftMargin = UIUtils.dp2px(2);
            }
            mAppDetailPicIvContainer.addView(rl, params);
        }
    }
}
