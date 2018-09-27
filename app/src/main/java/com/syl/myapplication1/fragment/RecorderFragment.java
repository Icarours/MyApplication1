package com.syl.myapplication1.fragment;

import android.annotation.TargetApi;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/15.
 *
 * @Describe
 * @Called
 */
public class RecorderFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.btn_start)
    Button mBtnStart;
    @Bind(R.id.btn_stop)
    Button mBtnStop;
    @Bind(R.id.btn_exit)
    Button mBtnExit;
    @Bind(R.id.skbVolume)
    SeekBar skbVolume;
    @Bind(R.id.textView2)
    TextView mTextView2;
    private MediaRecorder mRecorder;
    private File mFile;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    private void resetRecorder() {
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//声源,麦克风
        mRecorder.setAudioChannels(1);
        mRecorder.setAudioSamplingRate(1000);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean isRecording = false;//是否录放的标记
    static final int frequency = 44100;
    static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    static final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int recBufSize,playBufSize;
    AudioRecord audioRecord;
    AudioTrack audioTrack;
    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_recorder, null);
        ButterKnife.bind(this, rootView);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);

        recBufSize = AudioRecord.getMinBufferSize(frequency,
                channelConfiguration, audioFormat);

        playBufSize= AudioTrack.getMinBufferSize(frequency,
                channelConfiguration, audioFormat);

        // -----------------------------------------
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, audioFormat, recBufSize);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
                channelConfiguration, audioFormat,
                playBufSize, AudioTrack.MODE_STREAM);

        skbVolume.setMax(100);//音量调节的极限
        skbVolume.setProgress(70);//设置seekbar的位置值
        audioTrack.setStereoVolume(0.7f, 0.7f);//设置当前音量大小
        skbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float vol=(float)(seekBar.getProgress())/(float)(seekBar.getMax());
                audioTrack.setStereoVolume(vol, vol);//设置音量
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
            }
        });

        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, rootView);
        return rootView;
    }
    class RecordPlayThread extends Thread {
        public void run() {
            fun1();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void fun1() {
        try {
            byte[] buffer = new byte[recBufSize];
            audioRecord.startRecording();//开始录制
            audioTrack.play();//开始播放

            while (isRecording) {
                //从MIC保存数据到缓冲区
                int bufferReadResult = audioRecord.read(buffer, 0,recBufSize);

                byte[] tmpBuf = new byte[bufferReadResult];
                new Thread(){}.start();
                System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
                //写入数据即播放
                audioTrack.write(tmpBuf, 0, tmpBuf.length, AudioTrack.MODE_STATIC);
            }
            audioTrack.stop();
            audioRecord.stop();
        } catch (Throwable t) {
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void fun2() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        android.os.Process.killProcess(android.os.Process.myPid());
        mRecorder.release();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                isRecording = true;
                new RecordPlayThread().start();// 开一条线程边录边放
                break;
            case R.id.btn_stop:
                isRecording = false;
                break;
            case R.id.btn_exit:
                isRecording = false;
//                getActivity().finish();
                break;
            default:
                break;
        }
    }
    void calc1(short[] lin, int off, int len) {
        int i, j;
        for (i = 0; i < len; i++) {
            j = lin[i + off];
            lin[i + off] = (short) (j >> 2);
        }
    }
    void calc2(byte[] lin, int off, int len) {
        int i, j;
        for (i = 0; i < len; i++) {
            j = lin[i + off];
            lin[i + off] = (byte) (j >> 2);
        }
    }

}
