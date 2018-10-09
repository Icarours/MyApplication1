package com.syl.mobileplayer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.bean.VideoItem;
import com.syl.mobileplayer.util.StringUtil;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class VideoPlayerActivity extends BaseActivity {
    private static final int MSG_UPDATE_TIME = 0;
    private VideoView video_player_videoview;
    private ImageView video_player_play_state;
    private TextView video_player_title;
    private VideoBatteryReceiver receiver;
    private ImageView video_player_battery;
    private TextView video_player_tiem;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_TIME:
                    startUpdateTime();
                    break;
            }
        }
    };
    @Override
    protected void initData() {
        VideoItem videoItem = (VideoItem) getIntent().getSerializableExtra("videoItem");
        //设置播放路径
        video_player_videoview.setVideoPath(videoItem.getPath());
       video_player_title.setText(videoItem.getTitle());
        //创建广播接收者
        receiver = new VideoBatteryReceiver();
        //intentfilter
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,filter);

        startUpdateTime();
    }

    private void startUpdateTime() {
                //获取当前时间
        String time = StringUtil.getCurrentTiem();
        logE("当前时间："+time);
        video_player_tiem.setText(time);
        //定时更新时间
        handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME,500);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startUpdateTime();
//            }
//        },1000);
    }

    @Override
    protected void initListener() {
        video_player_videoview.setOnPreparedListener(new VideoOnprepareListener());
        video_player_play_state.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        video_player_videoview = (VideoView) findViewById(R.id.video_player_videoview);
        video_player_play_state = (ImageView) findViewById(R.id.video_player_play_state);
        video_player_title = (TextView) findViewById(R.id.video_player_title);
        video_player_battery = (ImageView) findViewById(R.id.video_player_battery);
        video_player_tiem = (TextView) findViewById(R.id.video_player_time);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()){
            case R.id.video_player_play_state:
                switchPlayState();
                break;
        }
    }
    //切换播放状态
    private void switchPlayState() {
        //获取播放状态
        //切换播放状态
        if(video_player_videoview.isPlaying()){
            //播放状态  暂停
            video_player_videoview.pause();
        }else{
            //暂停状态  播放
            video_player_videoview.start();
        }
        //修改播放状态按钮图片
        updatePlayMenu();
    }
    //根据当前播放状态设置播放状态按钮
    private void updatePlayMenu() {
        //获取当前播放状态
        if(video_player_videoview.isPlaying()){
            video_player_play_state.setImageResource(R.drawable.video_player_play_selector);
        }else{
            video_player_play_state.setImageResource(R.drawable.video_player_pause_selector);
        }
        //设置播放状态图片
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        //handler移除消息  避免内存泄漏
        handler.removeCallbacksAndMessages(null);
    }

    class VideoOnprepareListener implements MediaPlayer.OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备完成 直接播放
            video_player_videoview.start();
            //更新播放状态按钮
            updatePlayMenu();
        }
    }
    class VideoBatteryReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level",0);
            updateBatteryImage(level);
        }
    }
    //更新电量图片
    private void updateBatteryImage(int level) {
        if(level<10){
            video_player_battery.setImageResource(R.drawable.ic_battery_0);
        }else if(level<20){

            video_player_battery.setImageResource(R.drawable.ic_battery_10);
        }else if(level<40){

            video_player_battery.setImageResource(R.drawable.ic_battery_20);
        }else if(level<60){

            video_player_battery.setImageResource(R.drawable.ic_battery_40);
        }else if(level<80){
            video_player_battery.setImageResource(R.drawable.ic_battery_60);

        }else if(level<100){
            video_player_battery.setImageResource(R.drawable.ic_battery_80);

        }else if(level==100){
            video_player_battery.setImageResource(R.drawable.ic_battery_100);

        }
    }
}
