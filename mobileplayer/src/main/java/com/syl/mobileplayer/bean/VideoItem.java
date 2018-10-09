package com.syl.mobileplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class VideoItem implements Serializable{
    private String path;
    private String title;//视频名
    private int duration;//视频时间
    private int size;//视频大小
    public static VideoItem  getVideoItem(Cursor cursor){
        VideoItem videoItem = new VideoItem();
        //cursor是否为空
        if(cursor==null||cursor.getCount()==0) return videoItem;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            videoItem.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
            videoItem.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
            videoItem.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
            videoItem.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
        }
        return videoItem;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
