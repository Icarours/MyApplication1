package com.syl.myapplication1.fragment;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.myapplication1.R;
import com.syl.myapplication1.data.ImageData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/8/20.
 *
 * @Describe
 * @Called
 */

public class GlideFragment extends BaseFragment {
    @Bind(R.id.lv_glide)
    ListView mLvGlide;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_glide, null);
        ButterKnife.bind(this, rootView);
        String[] imageUrls = ImageData.IMAGESNET;
        GlideAdapter adapter = new GlideAdapter(getActivity(), imageUrls);
        mLvGlide.setAdapter(adapter);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * author   Bright
     * date     2018/8/20 23:12
     * desc
     * Glide对应的ListView数据
     */
    class GlideAdapter extends BaseAdapter {
        private String[] imageUrls;
        private Context mContext;

        public GlideAdapter(Context context, String[] imageUrls) {
            this.imageUrls = imageUrls;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            if (imageUrls != null) {
                return imageUrls.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (imageUrls != null) {
                return imageUrls[position];
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (imageUrls != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.lv_image_item, null);
                holder.iv = convertView.findViewById(R.id.iv);
                holder.tv = convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(mContext).load(imageUrls[position]).into(holder.iv);
            holder.tv.setText("item--" + position);
            return convertView;
        }
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
