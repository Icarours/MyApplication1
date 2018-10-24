package com.syl.mobileplayer.ui.fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.adapter.VideoListAdapter;
import com.syl.mobileplayer.bean.VideoItem;
import com.syl.mobileplayer.ui.activity.VideoPlayerActivity;

import java.util.ArrayList;


/**
 * Created by ThinkPad on 2016/4/11.
 */
public class VideoFragment extends BaseFragment {

    private ListView video_listview;
    private VideoListAdapter adapter;

    @Override
    protected void initData() {
        ContentResolver resolver = getContext().getContentResolver();
//        Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Video.Media._ID,
//                        MediaStore.Video.Media.DATA,
//                MediaStore.Video.Media.TITLE,
//                MediaStore.Video.Media.DURATION,
//                MediaStore.Video.Media.SIZE},null,null,null);
//        adapter.swapCursor(cursor);
//        CursorLog.cursorLog(cursor);
        AsyncQueryHandler handler = new AsyncQueryHandler(resolver) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                ((VideoListAdapter)cookie).swapCursor(cursor);
            }
        };
        handler.startQuery(0,adapter,MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE},null,null,null);
    }

    @Override
    protected void initListener() {
        //创建adapter
        adapter = new VideoListAdapter(getContext(),null);

        //设置adapter
        video_listview.setAdapter(adapter);
        video_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前条目
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
//                VideoItem videoItem = VideoItem.getVideoItem(cursor);
                ArrayList<VideoItem> videoItems = VideoItem.getVideoItems(cursor);
                //跳转
                Intent intent =  new Intent(VideoFragment.this.getActivity(),VideoPlayerActivity.class);
                intent.putExtra("videoItems",videoItems);
                intent.putExtra("position",position);
//                intent.putExtra("videoItem",videoItem);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        video_listview = (ListView) findViewByid(R.id.video_listview);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void processClick(View v) {

    }
}
