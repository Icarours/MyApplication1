package com.syl.myapplication1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.Contact;

import java.util.List;

/**
 * Created by Bright on 2018/7/15.
 *
 * @Describe 联系人的Adapter
 * @Called
 */

public class ContactsAdapter extends BaseAdapter {
    private List<Contact> mInfoList;
    private Context mContext;

    public ContactsAdapter(Context context, List<Contact> data) {
        mContext = context;
        mInfoList = data;
    }

    @Override
    public int getCount() {
        if (mInfoList != null) {
            return mInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mInfoList != null) {
            return mInfoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mInfoList != null) {
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
        Contact contact = mInfoList.get(position);
        holder.tv.setText(contact.toString());
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
