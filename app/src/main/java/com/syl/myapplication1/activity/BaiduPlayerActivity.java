package com.syl.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.cloud.videoplayer.demo.AdvancedPlayActivity;
import com.baidu.cloud.videoplayer.demo.SimplePlayActivity;
import com.baidu.cloud.videoplayer.demo.info.VideoInfo;
import com.syl.myapplication1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaiduPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SimplePlayActivity";
    @Bind(R.id.btn_baidu_player)
    Button mBtnBaiduPlayer;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    private VideoInfo mInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_player);
        ButterKnife.bind(this);
        mBtnBaiduPlayer.setOnClickListener(this);
        mInfo = new VideoInfo("test", "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_baidu_player:
                Intent intent = null;
                boolean isSimple = false;
                if (isSimple) {
                    // SimplePlayActivity简易播放窗口，便于快速了解播放流程
                    intent = new Intent(BaiduPlayerActivity.this, SimplePlayActivity.class);
                } else {
                    // AdvancedPlayActivity高级播放窗口，内含丰富的播放控制逻辑
                    intent = new Intent(BaiduPlayerActivity.this, AdvancedPlayActivity.class);
                }
                intent.putExtra("videoInfo", mInfo);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}


