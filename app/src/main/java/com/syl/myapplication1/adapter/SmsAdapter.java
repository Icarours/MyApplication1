package com.syl.myapplication1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.SmsEntity;

import java.util.List;


/**
 * Created by Bright on 2018/7/14.
 *
 * @Describe sms
 * @Called
 */

public class SmsAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmsEntity> mData;

    public SmsAdapter(Context context, List<SmsEntity> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        if (mData!=null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mData!=null){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mData!=null){
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_sms, null);
            holder.tvItemSms = convertView.findViewById(R.id.tv_item_sms);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SmsEntity smsEntity = mData.get(position);
        holder.tvItemSms.setText(smsEntity.toString());
        return convertView;
    }

    class ViewHolder {
        TextView tvItemSms;
    }
}
