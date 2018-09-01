package com.syl.myapplication1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Vitamio播放器框架,目前只能播放网络视屏,本地视屏还不能播放
 */
public class VitamioActivity extends AppCompatActivity {

    @Bind(R.id.vv)
    VideoView mVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//低版本可以隐藏标题栏,高版本不行
        //隐藏手机顶部的通知栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vitamio);
        getSupportActionBar().hide();//高版本隐藏标题栏
        ButterKnife.bind(this);
        vitamio();
    }

    private void vitamio() {
        //        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final VideoView vv = findViewById(R.id.vv);
        //视频地址
//        String VIDEOURL = "http://60.205.177.21/cloud_platform_release/html/upload/zhineng/Stone_bridge20180830181720.avi";
        String VIDEOURL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        //直播地址
        String VIDEOURL1 = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
        //.setSound(Uri.parse("android.resource://com.syl.myapplication1/" + R.raw.xsy));
        //本地视频
        String VIDEOURL2 = "/mnt/sdcard/lrx.mp4";

//        vv.setVideoURI(Uri.parse("android.resource://com.syl.myapplication1/" + R.raw.lrx));
//        vv.setVideoURI(Uri.parse("/mnt/sdcard/lrx.mp4"));?
        vv.setVideoPath(VIDEOURL); //设置播放路径
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv.start();
            }
        });
// 设置video的控制器
        vv.setMediaController(new MediaController(this));
    }
}
