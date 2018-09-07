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
        mTvContent.setText("直接将百度提供的sample作为一个library集成到APP中,其中有不少坑.\n" +
                "1.将一个module作为一个library依赖到另一个module中;\n" +
                "2.将百度播放器中的switch case 语句改为if else语句;\n" +
                "3.百度播放器和vitamio播放器使用的.so文件相同,处理多个.so文件冲突的问题\n" +
                "4.原来的module也可以自行启动运行,需要注解掉启动Activity的过滤器.否则进入本界面点击百度播放器之后也会启动module.");
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


