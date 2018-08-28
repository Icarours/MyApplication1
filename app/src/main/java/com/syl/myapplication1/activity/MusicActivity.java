package com.syl.myapplication1.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 简易音乐播放器
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.et_music_path)
    EditText mEtMusicPath;
    @Bind(R.id.btn_play)
    Button mBtnPlay;
    @Bind(R.id.btn_pause)
    Button mBtnPause;
    @Bind(R.id.btn_stop)
    Button mBtnStop;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        mBtnPause.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mTvTips.setText("简易音乐播放器..");
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        //在根布局创建的时候就创建MediaPlayer
        cretePlayer();
    }

    //创建单例音乐播放器
    private void cretePlayer() {
        if (mMediaPlayer == null) {
            synchronized (MusicActivity.class) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                btnPlay();
                break;
            case R.id.btn_pause:
                btnPause();
                break;
            case R.id.btn_stop:
                btnStop();
                break;
            default:
                break;
        }
    }

    private void btnPlay() {
        cretePlayer();//如果没有播放器,就创建一个
        String etPath = mEtMusicPath.getText().toString();
        if (TextUtils.isEmpty(etPath)) {
            Toast.makeText(this, "文件路径不能为空..", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(etPath);
        if (!file.exists()) {
            Toast.makeText(this, "文件路径非法..", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (currentPath.equals(etPath)) {
            return;
        } else {
            mMediaPlayer.release();
        }*/
        try {
            mMediaPlayer.setDataSource(etPath);
            //在主线程中缓冲
            mMediaPlayer.prepare();
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
//            currentPath = etPath;
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //播放按钮点击之后变灰,不可用
        mBtnPlay.setEnabled(false);
    }

    private void btnPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mBtnPause.setText("播放");
        } else {
            mMediaPlayer.start();
            mBtnPause.setText("继续");
        }
        //暂停按钮点击之后,播放按钮回复正常
        mBtnPlay.setEnabled(true);
    }

    //停止按钮点击之后,播放按钮回复正常
    private void btnStop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mBtnPlay.setEnabled(true);
    }
}
