package com.syl.mobileplayer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.syl.mobileplayer.R;
import com.syl.mobileplayer.bean.VideoItem;
import com.syl.mobileplayer.util.LogUtil;
import com.syl.mobileplayer.util.StringUtil;

import java.util.ArrayList;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by ThinkPad on 2016/4/11.
 * 播放器页面
 */
public class VideoPlayerActivity extends BaseActivity {
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();
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
                case MSG_UPDATE_PROGRESS:
                    startUpdateProgress();
                    break;
                case MSG_HIDE:
                    hideLinear();//隐藏上部状态栏和下部控制栏
                    break;
                case MSG_UPDATE_BUFFER:
                    startUpdateBuffer();//更新缓冲
                    break;
            }
        }
    };
    private AudioManager mAudioManager;
    private int mMarkVolume;
    private int mOrignalVolume;
    private float mStartY;
    private View mVideoPlayerCover;
    private ArrayList<VideoItem> mVideoItems;
    private int mPosition;
    private boolean isFullScreen = false;
    private int mScreenW;
    private int mScreenH;
    private int mVideoViewW;
    private int mVideoViewH;
    private LinearLayout mVideoPlayerLinearBotoom;
    private LinearLayout mVideoPlayerLinearTop;
    private GestureDetector mGesture;
    private boolean isHide = true;//上部状态栏和下部控制栏是否隐藏
    private int mBottomH;//下部控制栏的高度
    private int mTopH;//上部状态栏的高度
    private LinearLayout mLlProgressBar;

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
        mVideoPlayerCover = findViewById(R.id.video_player_cover);//创建一个遮罩,控制播放器屏幕的亮度
        mVideoPlayerLinearBotoom = findViewById(R.id.video_player_linear_bottom);
        mVideoPlayerLinearTop = findViewById(R.id.video_player_linear_top);
        mLlProgressBar = findViewById(R.id.ll_progress_bar);
    }

    @Override
    protected void initData() {
        //创建手势事件,接收,处理手势事件
        mGesture = new GestureDetector(this, new VideoOnGestureListener());

        //显示正在加载,加载进度
        mLlProgressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {//如果uri不为null,说明是从本APP外部直接调用播放器
            mVideoPlayerVideoview.setVideoURI(uri);
        } else {//通过APP正常调用播放器
            mVideoItems = (ArrayList<VideoItem>) intent.getSerializableExtra("videoItems");
            mPosition = intent.getIntExtra("position", -1);
//        VideoItem videoItem = (VideoItem) getIntent().getSerializableExtra("videoItem");
            //Log.d(TAG, "videoItems.size()==" + mVideoItems.size() + "---position==" + mPosition);
            playItem();//播放视频
        }

        //创建广播接收者,实时通知电池电量变化
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

        //获取当前屏幕的宽高
        mScreenW = getWindowManager().getDefaultDisplay().getWidth();
        mScreenH = getWindowManager().getDefaultDisplay().getHeight();

        //获得播放器上部状态栏和下部控制栏的高度
        int topH = mVideoPlayerLinearTop.getHeight();
        int bottomH = mVideoPlayerLinearBotoom.getHeight();
        Log.d(TAG, "topH==" + topH + "----bottomH==" + bottomH);
        mVideoPlayerLinearBotoom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {//只有当控件的布局完成之后才能获得控件的宽高
                mVideoPlayerLinearBotoom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mBottomH = mVideoPlayerLinearBotoom.getHeight();
                //通过动画隐藏底部控制栏
                ViewPropertyAnimator.animate(mVideoPlayerLinearBotoom).translationY(mBottomH);
                Log.d(TAG, "onGlobalLayout()----bottomH==" + mBottomH);
            }
        });
        mVideoPlayerLinearTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mVideoPlayerLinearTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mTopH = mVideoPlayerLinearTop.getHeight();
                //通过动画隐藏顶部状态栏
                ViewPropertyAnimator.animate(mVideoPlayerLinearTop).translationY(-mTopH);
                Log.d(TAG, "onGlobalLayout()----topH==" + mTopH);
            }
        });
    }

    /**
     * 播放当前条目对应的视频
     */
    private void playItem() {
        //判断当前条目是否有效
        if (mPosition == -1 || mVideoItems == null || mVideoItems.size() == 0)
            return;
        //更新上一个视频,下一个视频按钮
        mVideoPlayerPre.setEnabled(mVideoItems != null && mPosition != 0 && mPosition != -1);
        mVideoPlayerNext.setEnabled(mVideoItems != null && mPosition != mVideoItems.size() - 1 && mPosition != -1);
        //设置播放路径
        VideoItem videoItem = mVideoItems.get(mPosition);
        mVideoPlayerVideoview.setVideoPath(videoItem.getPath());
        mVideoPlayerTitle.setText(videoItem.getTitle());
    }

    /**
     * 更新当前的音量   更新seekbar  更新系统音量
     *
     * @param currentVolume
     */
    private void updateVolumeSk(int currentVolume) {
        //flags:0,调节音量时,不显示系统音量按钮;1,调节音量时,显示系统音量按钮
        mAudioManager.setStreamVolume(STREAM_MUSIC, currentVolume, 0);
        mVideoPlayerVoiceSk.setProgress(currentVolume);
    }

    private void startUpdateTime() {
        //获取当前时间
        String time = StringUtil.getCurrentTiem();
//        logE("当前时间：" + time);
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
        mVideoPlayerVideoview.setOnCompletionListener(new VideoOnCompletionListener());
        mVideoPlayerPlayState.setOnClickListener(this);
        VideoOnseekbarChangerListener videoOnseekbarChangerListener = new VideoOnseekbarChangerListener();
        mVideoPlayerProgressSk.setOnSeekBarChangeListener(videoOnseekbarChangerListener);
        mVideoPlayerVoiceSk.setOnSeekBarChangeListener(videoOnseekbarChangerListener);
        mVideoPlayerMute.setOnClickListener(this);
        mVideoPlayerNext.setOnClickListener(this);
        mVideoPlayerPre.setOnClickListener(this);
        mVideoPlayerScreenState.setOnClickListener(this);
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
            case R.id.video_player_mute:
                switchPlayerMute();
                break;
            case R.id.video_player_next:
                playNext();
                break;
            case R.id.video_player_pre:
                playPre();
                break;
            case R.id.video_player_screen_state:
                switchScreenState();
                break;
        }
    }

    /**
     * 调整屏幕全屏和非全屏
     */
    private void switchScreenState() {
        if (isFullScreen) {
            //全屏状态  设置VideoView的宽高为记录宽高
            mVideoPlayerVideoview.getLayoutParams().width = mVideoViewW;
            mVideoPlayerVideoview.getLayoutParams().height = mVideoViewH;
        } else {//默认屏幕状态    记录VideoView的宽高为屏幕的宽高
            //记录VideoView的宽高
            mVideoViewW = mVideoPlayerVideoview.getWidth();
            mVideoViewH = mVideoPlayerVideoview.getHeight();

            mVideoPlayerVideoview.getLayoutParams().width = mScreenW;
            mVideoPlayerVideoview.getLayoutParams().height = mScreenH;
        }
        //更新布局
        mVideoPlayerVideoview.requestLayout();
        //修改屏幕状态值(更新isFullScreen的值)
        isFullScreen = !isFullScreen;
    }

    /**
     * 播放上一个视频
     */
    private void playPre() {
        if (mVideoItems == null || mPosition == 0) return;
        mPosition--;
        playItem();
    }

    /**
     * 播放下一个视频
     */
    private void playNext() {
        if (mVideoItems == null || mPosition == mVideoItems.size() - 1) return;
        mPosition++;
        playItem();
    }

    private void switchPlayerMute() {
        //获取当前音量 int值
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume > 0) {
            //如果当前音量>0,记录当前音量,并将当前音量设置为0
            mMarkVolume = currentVolume;
            updateVolumeSk(0);
        } else {
            //如果当前音量音量=0,将当前音量设置为记录音量
            updateVolumeSk(mMarkVolume);
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
            handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 500);
        } else {
            mVideoPlayerPlayState.setImageResource(R.drawable.video_player_pause_selector);
            handler.removeMessages(MSG_UPDATE_PROGRESS);
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

    /**
     * author   Bright
     * date     2018/10/20 18:51
     * desc
     * 监听准备进度
     */
    class VideoOnprepareListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备完成 直接播放,视频真正加载进内存
            mVideoPlayerVideoview.start();

            //隐藏正在加载页面,加载进度
            mLlProgressBar.setVisibility(View.GONE);

            //更新播放状态按钮
            updatePlayMenu();

            //获取视频时长,duration
            int duration = mVideoPlayerVideoview.getDuration();
            mVideoPlayerDuration.setText(StringUtil.parseDuration(duration));
            mVideoPlayerProgressSk.setMax(duration);//设置进度条最大值

            //更新进度
            startUpdateProgress();

            //更新缓冲进度
//            startUpdateBuffer();
            // TODO: 2018/10/20 获取不到缓冲进度,费解
            mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    mVideoPlayerProgressSk.setSecondaryProgress(percent);
                    Log.d(TAG, "percent==" + percent);
                }
            });
        }
    }

    /**
     * 定时更新缓冲进度
     */
    private void startUpdateBuffer() {
        //获取当前缓冲进度
        int bufferPercentage = mVideoPlayerVideoview.getBufferPercentage();

        float percent = bufferPercentage / (float) 100;
        int bufferProgress = (int) (percent * mVideoPlayerVideoview.getDuration());
        Log.d(TAG, "bufferProgress==" + bufferProgress);
        //设置缓冲进度
        mVideoPlayerProgressSk.setSecondaryProgress(bufferProgress);
        //更新缓冲进度
        handler.sendEmptyMessageDelayed(MSG_UPDATE_BUFFER, 500);
    }

    private void startUpdateProgress() {
        //获取当前进度
        int progress = mVideoPlayerVideoview.getCurrentPosition();
        //设置进度数值
        updateProgress(progress);
        //定时获取进度
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 500);
    }

    private void updateProgress(int progress) {
//        Log.d(TAG, "执行了更新进度：" + progress);
        //更新进度数值
        mVideoPlayerProgress.setText(StringUtil.parseDuration(progress));
        //更新进度条
        mVideoPlayerProgressSk.setProgress(progress);
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
            //关闭5秒隐藏上下栏的通知
            handler.removeMessages(MSG_HIDE);
        }

        //手指离开seekbar
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //开启5秒隐藏上下栏的通知
            handler.sendEmptyMessageDelayed(MSG_HIDE, 5000);
        }
    }


    /**
     * 手势滑动,改变音量
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //优先处理手势事件
        mGesture.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOrignalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手势滑动距离=endY-startY;
                float endY = event.getY();
                float offSetY = mStartY - endY;
                //手势滑动距离与屏幕高度比值=手势滑动距离/屏幕高度
                float offsetPercent = offSetY / getWindowManager().getDefaultDisplay().getHeight();
                //滑动事件在屏幕的左半部分时,触发音量调节;右半部分时,触发亮度调节
                if (event.getX() <= getWindowManager().getDefaultDisplay().getWidth() / 2) {
                    //音量变化量=手势滑动比*最大音量
                    float offSetVolume = offsetPercent * mAudioManager.getStreamMaxVolume(STREAM_MUSIC);
                    //最终音量=当前音量+变化音量
                    int endVolume = (int) (mOrignalVolume + offSetVolume);
                    updateVolumeSk(endVolume);//更新音量
                } else {
                    updateScreenBright(offsetPercent);//调节屏幕亮度
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updateScreenBright(float offsetPercent) {
        //亮度变化量=手势滑动比*最大透明度
        float offSetAlpha = offsetPercent * 1;//最大亮度值是1
        //最终亮度=当前亮度+亮度变化量
        float finalAlpha = mVideoPlayerCover.getAlpha() - offSetAlpha;
        if (finalAlpha < 0 || finalAlpha > 1) return;
        ViewHelper.setAlpha(mVideoPlayerCover, finalAlpha);
        LogUtil.d(TAG, "finalAlpha===" + finalAlpha);
    }

    //视频播放完成监听
    class VideoOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //关闭进度更新,移除消息队列message
            handler.removeMessages(MSG_UPDATE_PROGRESS);
            //设置播放进度为duration
            updateProgress(mVideoPlayerVideoview.getDuration());
            //更新播放状态按钮图片
            updatePlayMenu();
        }
    }

    /**
     * 监听手势点击屏幕,控制上部状态栏和下部控制栏的显隐
     */
    class VideoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * 单击事件响应,接收到手势事件之后会先确认一下是单击事件还是双击事件,只有确认了是单击事件才会响应.
         * 使用该方法控制上部状态栏和下部控制栏的显隐
         *
         * @param e 事件
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed---");
            showAndHide();
            return super.onSingleTapConfirmed(e);
        }

        /**
         * 单击事件,双击事件都会响应
         *
         * @param e 事件
         * @return
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp---");
            return super.onSingleTapUp(e);
        }

        /**
         * 双击事件
         *
         * @param e 事件
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            switchScreenState();//调整屏幕状态
            return super.onDoubleTap(e);
        }

        /**
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            switchPlayState();//切换播放状态
        }
    }

    /**
     * 显示和隐藏上部状态栏和下部控制栏
     */
    private void showAndHide() {
        if (isHide) {
            //隐藏->显示
            showLinear();
        } else {
            //显示->隐藏
            hideLinear();
        }
    }

    /**
     * 隐藏上部状态栏和下部控制栏
     * 显示->隐藏
     */
    private void hideLinear() {
        ViewPropertyAnimator.animate(mVideoPlayerLinearTop).translationY(-mTopH);
        ViewPropertyAnimator.animate(mVideoPlayerLinearBotoom).translationY(mBottomH);
        //上部状态栏和下部控制栏关闭之后,移除msg
        handler.removeMessages(MSG_HIDE);
        //设置isHide
        isHide = true;
    }

    /**
     * 显示上部状态栏和下部控制栏
     * 隐藏->显示
     */
    private void showLinear() {
        ViewPropertyAnimator.animate(mVideoPlayerLinearTop).translationY(0);
        ViewPropertyAnimator.animate(mVideoPlayerLinearBotoom).translationY(0);
        //如果上部状态栏和下部控制栏处于隐藏状态,发送延迟消息关闭上部状态栏和下部控制栏
        handler.sendEmptyMessageDelayed(MSG_HIDE, 5000);
        //设置isHide
        isHide = false;
    }
}
