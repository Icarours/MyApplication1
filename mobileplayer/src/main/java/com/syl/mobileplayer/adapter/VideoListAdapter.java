package com.syl.mobileplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.bean.VideoItem;
import com.syl.mobileplayer.util.StringUtil;


/**
 * Created by ThinkPad on 2016/4/11.
 */
public class VideoListAdapter extends CursorAdapter {
    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public VideoListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public VideoListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    //创建view
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.video_list_item,null);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }
    //view值初始化
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        VideoItem videoItem = VideoItem.getVideoItem(cursor);
        holder.video_list_title.setText(videoItem.getTitle());
        holder.video_list_duration.setText(StringUtil.parseDuration(videoItem.getDuration()));
        holder.video_list_size.setText(android.text.format.Formatter.formatFileSize(context,videoItem.getSize()));
    }
    class ViewHolder {
        TextView video_list_title,video_list_duration,video_list_size;
        public ViewHolder(View view){
            video_list_title = (TextView) view.findViewById(R.id.video_list_title);
            video_list_duration = (TextView) view.findViewById(R.id.video_list_duration);
            video_list_size = (TextView) view.findViewById(R.id.video_list_size);
        }


    }
}
