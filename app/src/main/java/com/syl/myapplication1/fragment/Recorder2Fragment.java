package com.syl.myapplication1.fragment;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/15.
 *
 * @Describe
 * @Called
 */
public class Recorder2Fragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.btn_start_record)
    Button mBtnStartRecord;
    boolean running = false;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment_recorder2, null);
        ButterKnife.bind(this, rootView);
        mBtnStartRecord.setOnClickListener(this);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        running = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_record:
                startRecord();
                break;
            default:
                break;
        }
    }

    private void startRecord() {
        new RecordTest().start();
    }

    void calc1(short[] lin, int off, int len) {
        int i, j;

        for (i = 0; i < len; i++) {
            j = lin[i + off];
            lin[i + off] = (short) (j >> 2);
        }
    }

    class RecordTest extends Thread {
        @Override
        public void run() {
            int samp_rate = 8000;
            int min = AudioRecord.getMinBufferSize(samp_rate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            Log.e("tag", "min buffer size:" + min);

//用于采集音频源
            AudioRecord record = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,//the recording source
                    samp_rate, //采样频率，一般为8000hz/s
                    AudioFormat.CHANNEL_OUT_DEFAULT,
                    AudioFormat.ENCODING_PCM_16BIT,
                    min * 10);
            record.startRecording();
//用于播放音频源
            int maxjitter = AudioTrack.getMinBufferSize(samp_rate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, samp_rate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    maxjitter * 10, AudioTrack.MODE_STREAM);
            track.play();

            int frame_size = 320;//g726_32 : 4:1的压缩比
            byte[] audioData = new byte[frame_size / 4];
            short[] encodeData = new short[frame_size / 2];
            int num = 0;

//库函数
//                G726Codec codec = new g726Codec ();

            short[] putIn = new short[160];

// int result= 0;
            while (running) {
                num = record.read(encodeData, 0, 160);
                Log.e("tag", "num:" + num);

                calc1(encodeData, 0, 160);

                int wirteNum = track.write(encodeData, 0, num);

            }

            record.stop();
            record.release();
            record = null;

            track.stop();
            track.release();
            track = null;
        }
    }
}
