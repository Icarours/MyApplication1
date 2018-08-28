package com.syl.googleplay3.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.syl.googleplay3.R;
import com.syl.googleplay3.base.BaseHolder;
import com.syl.googleplay3.bean.CategoryBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe 普通条目的Holder, Adapter 中 对getView()方法的抽取简化
 * @Called
 */

public class CategoryNormalHolder extends BaseHolder<CategoryBean> {
    @Bind(R.id.item_category_icon_1)
    ImageView mItemCategoryIcon1;
    @Bind(R.id.item_category_name_1)
    TextView mItemCategoryName1;
    @Bind(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;
    @Bind(R.id.item_category_icon_2)
    ImageView mItemCategoryIcon2;
    @Bind(R.id.item_category_name_2)
    TextView mItemCategoryName2;
    @Bind(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;
    @Bind(R.id.item_category_icon_3)
    ImageView mItemCategoryIcon3;
    @Bind(R.id.item_category_name_3)
    TextView mItemCategoryName3;
    @Bind(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        ButterKnife.bind(this,holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(CategoryBean data) {
        setData(data.getName1(), data.getUrl1(), mItemCategoryName1, mItemCategoryIcon1);
        setData(data.getName2(), data.getUrl2(), mItemCategoryName2, mItemCategoryIcon2);
        setData(data.getName3(), data.getUrl3(), mItemCategoryName3, mItemCategoryIcon3);
    }

    private void setData(final String name, String url, TextView tv, ImageView iv) {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(url)) {//如果tv为空而且url为空,就将tv对应的parent控件设置为不可见
            ViewGroup parent = (ViewGroup) tv.getParent();
            parent.setVisibility(View.INVISIBLE);//这里要设置View.INVISIBLE,不能设置View.GONE.设置成View.GONE会打乱原来的布局
        } else {
            tv.setText(name);
            Glide.with(UIUtils.getContext()).load(Constants.URLS.IMAGEBASEURL + url).into(iv);
            ViewGroup parent = (ViewGroup) tv.getParent();
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), name + "was clicked.", Toast.LENGTH_SHORT).show();
                }
            });
            parent.setVisibility(View.VISIBLE);
        }
    }
}
