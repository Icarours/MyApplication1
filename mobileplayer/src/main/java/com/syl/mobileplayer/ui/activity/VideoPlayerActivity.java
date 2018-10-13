package com.syl.mobileplayer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.syl.mobileplayer.R;
import com.syl.mobileplayer.bean.VideoItem;
import com.syl.mobileplayer.util.StringUtil;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by ThinkPad on 2016/4/11.
 */
public class VideoPlayerActivity extends BaseActivity {
    private static final int MSG_UPDATE_TIME = 0;
    private static final int MSG_UPDATE_PROGRESS = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_UPDATE_BUFFER = 3;
    private VideoView mVideoPlayerVideoview;
    private TextView mVideoPlayerTitle;
    private ImageView mVideoPlayerBattery;
    private TextView mVideoPlayerTime;
    private ImageView mVideoPlayerMute;
    private SeekBar mVideoPlayerVoiceSk;
    private TextView mVideoPlayerProgress;
    private SeekBar mVideoPlayerProgressSk;
    private TextView mVideoPlayerDuration;
    private ImageView mBack;
    private ImageView mVideoPlayerPre;
    private ImageView mVideoPlayerPlayState;
    private ImageView mVideoPlayerNext;
    private ImageView mVideoPlayerScreenState;
    private VideoBatteryReceiver receiver;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    startUpdateTime();
                    break;
            }
        }
    };
    private AudioManager mAudioManager;

    @Override
    protected void initView() {
        mVideoPlayerVideoview = (VideoView) findViewById(R.id.video_player_videoview);
        mVideoPlayerPlayState = (ImageView) findViewById(R.id.video_player_play_state);
        mVideoPlayerTitle = (TextView) findViewById(R.id.video_player_title);
        mVideoPlayerBattery = (ImageView) findViewById(R.id.video_player_battery);
        mVideoPlayerTime = (TextView) findViewById(R.id.video_player_time);
        mVideoPlayerMute = findViewById(R.id.video_player_mute);
        mVideoPlayerVoiceSk = findViewById(R.id.video_player_voice_sk);
        mVideoPlayerProgress = findViewById(R.id.video_player_progress);
        mVideoPlayerProgressSk = findViewById(R.id.video_player_progress_sk);
        mVideoPlayerDuration = findViewById(R.id.video_player_duration);
        mBack = findViewById(R.id.back);
        mVideoPlayerPre = findViewById(R.id.video_player_pre);
        mVideoPlayerNext = findViewById(R.id.video_player_next);
        mVideoPlayerScreenState = findViewById(R.id.video_player_screen_state);
    }

    @Override
    protected void initData() {
        VideoItem videoItem = (VideoItem) getIntent().getSerializableExtra("videoItem");
        //设置播放路径
        mVideoPlayerVideoview.setVideoPath(videoItem.getPath());
        mVideoPlayerTitle.setText(videoItem.getTitle());
        //创建广播接收者
        receiver = new VideoBatteryReceiver();
        //intentfilter
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

        startUpdateTime();

        //音量调节
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(STREAM_MUSIC);
        int currentVolume = mAudioManager.getStreamVolume(STREAM_MUSIC);
        mVideoPlayerVoiceSk.setMax(maxVolume);
        updateVolumeSk(currentVolume);
    }

    /**
     * 更新当前的音量   更新seekbar  更新系统音量
     *
     * @param currentVolume
     */
    private void updateVolumeSk(int currentVolume) {
        //flags:0,调节音量时,不显示系统音量;1,调节音量时,显示系统音量
        mAudioManager.setStreamVolume(STREAM_MUSIC, currentVolume, 0);
        mVideoPlayerVoiceSk.setProgress(currentVolume);
    }

    private void startUpdateTime() {
        //获取当前时间
        String time = StringUtil.getCurrentTiem();
        logE("当前时间：" + time);
        mVideoPlayerTime.setText(time);
        //定时更新时间
        handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 500);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startUpdateTime();
//            }
//        },1000);
    }

    @Override
    protected void initListener() {
        mVideoPlayerVideoview.setOnPreparedListener(new VideoOnprepareListener());
        mVideoPlayerPlayState.setOnClickListener(this);
        VideoOnseekbarChangerListener videoOnseekbarChangerListener = new VideoOnseekbarChangerListener();
        mVideoPlayerProgressSk.setOnSeekBarChangeListener(videoOnseekbarChangerListener);
        mVideoPlayerVoiceSk.setOnSeekBarChangeListener(videoOnseekbarChangerListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.video_player_play_state:
                switchPlayState();
                break;
        }
    }

    //切换播放状态
    private void switchPlayState() {
        //获取播放状态
        //切换播放状态
        if (mVideoPlayerVideoview.isPlaying()) {
            //播放状态  暂停
            mVideoPlayerVideoview.pause();
        } else {
            //暂停状态  播放
            mVideoPlayerVideoview.start();
        }
        //修改播放状态按钮图片
        updatePlayMenu();
    }

    //根据当前播放状态设置播放状态按钮
    private void updatePlayMenu() {
        //获取当前播放状态
        if (mVideoPlayerVideoview.isPlaying()) {
            mVideoPlayerPlayState.setImageResource(R.drawable.video_player_play_selector);
        } else {
            mVideoPlayerPlayState.setImageResource(R.drawable.video_player_pause_selector);
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


    class VideoOnprepareListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备完成 直接播放
            mVideoPlayerVideoview.start();
            //更新播放状态按钮
            updatePlayMenu();
        }
    }

    class VideoBatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            updateBatteryImage(level);
        }
    }

    //更新电量图片
    private void updateBatteryImage(int level) {
        if (level < 10) {
            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_0);
        } else if (level < 20) {

            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_10);
        } else if (level < 40) {

            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_20);
        } else if (level < 60) {

            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_40);
        } else if (level < 80) {
            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_60);

        } else if (level < 100) {
            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_80);

        } else if (level == 100) {
            mVideoPlayerBattery.setImageResource(R.drawable.ic_battery_100);

        }
    }

    class VideoOnseekbarChangerListener implements SeekBar.OnSeekBarChangeListener {
        /**
         * 进度改变
         *
         * @param seekBar  区分进度改变的seekbar
         * @param progress 当前进度
         * @param fromUser true 用户操作改变进度
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //判断当前进度改变是否为用户操作
            if (!fromUser) return;
            if (seekBar.getId() == R.id.video_player_voice_sk) {
                //更新seekbar和系统音量
                updateVolumeSk(progress);
            } else if (seekBar.getId() == R.id.video_player_progress_sk) {
                //更新视频播放进度
                mVideoPlayerVideoview.seekTo(progress);
                //更新进度数值和进度条位置
                updateProgress(progress);
            }
        }

        //手指触摸seekbar
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //关闭5秒隐藏上下栏
            handler.removeMessages(MSG_HIDE);
        }

        //手指离开seekbar
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //开启5秒隐藏上下栏
            handler.sendEmptyMessageDelayed(MSG_HIDE, 5000);
        }
    }

    private void updateProgress(int progress) {
        logE("执行了更新进度：" + progress);
        //更新进度数值
        mVideoPlayerProgress.setText(StringUtil.parseDuration(progress));
        //更新进度条
        mVideoPlayerProgressSk.setProgress(progress);
    }
}
