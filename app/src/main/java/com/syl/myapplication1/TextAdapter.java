package com.syl.myapplication1;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe 只有一个文本文件的ListItem
 * @Called
 */

public class TextAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public TextAdapter(List<String> list, FragmentActivity activity) {
        mList = list;
        mContext = activity;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mList != null) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mList != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_single_text, null);
            holder.tv = convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mList.get(position));

        System.out.println(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
