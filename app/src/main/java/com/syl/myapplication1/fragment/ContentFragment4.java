package com.syl.myapplication1.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.factory.ListViewFactory;
import com.syl.myapplication1.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/7/18.
 *
 * @Describe 主菜单(底部菜单)对应的第四个按钮
 * @Called
 */

public class ContentFragment4 extends BaseFragment {
    private List<String> mList = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 100; i++) {
            mList.add("test---" + i);
        }
    }

    @Override
    public View initView() {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ListView listView = ListViewFactory.createListView();
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.img2);

        frameLayout.addView(imageView);
        frameLayout.addView(listView);
        return frameLayout;

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList!=null){
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mList!=null){
                return mList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mList!=null){
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(UIUtils.getContext(), R.layout.list_item_single_text, null);
                holder.tv = convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mList.get(position));

            System.out.println(mList.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }
}
