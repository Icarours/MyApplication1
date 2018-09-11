package com.syl.myapplication1.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.syl.myapplication1.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * author   Bright
 * date     2018/9/11 22:24
 * desc
 * 使用相机拍摄视频
 */
public class PhotoVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String FILE_PATH = "/sdcard/sysvideocamera.3gp";
    private static final String TAG = PhotoVideoActivity.class.getSimpleName();
    @Bind(R.id.btn_ph_video)
    Button mBtnPhVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video);
        ButterKnife.bind(this);
        mBtnPhVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ph_video:
                Intent intent = new Intent();
                intent.setAction("android.media.action.VIDEO_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");
                File file = new File(FILE_PATH);
                if (file.exists()) {
                    file.delete();
                }
                Uri uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "拍摄完成，resultCode=" + requestCode);
    }
}
