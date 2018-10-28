package com.syl.myapplication1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.syl.myapplication1.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * author   Bright
 * date     2018/9/11 22:25
 * desc
 * 使用相机拍照,压缩照片
 */
public class Photo2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_TAKE_PICTURE = 100;
    @Bind(R.id.btn_take_photo)
    Button mBtnTakePhoto;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.btn_photo2)
    Button mBtnPhoto2;
    private static final String TAG = "Photo2Activity";
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo2);
        ButterKnife.bind(this);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnPhoto2.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode="+resultCode);

        if (requestCode == 0) {
//            File file = new File(FILE_PATH);
//            Uri uri = Uri.fromFile(file);
//            mIv.setImageURI(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(FILE_PATH, options);
            mIv.setImageBitmap(bitmap);
        } else if (requestCode == 1) {//相机拍照返回的默认地址是null
//            Log.i(TAG, "默认content地址："+data.getData());
//            mIv.setImageURI(data.getData());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            // 指定相机拍摄照片保存地址
            case R.id.btn_take_photo:
                intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                // 根据文件地址创建文件
                File file = new File(FILE_PATH);
                if (file.exists()) {
                    file.delete();
                }
                // 把文件地址转换成Uri格式
                Uri uri = Uri.fromFile(file);
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
                break;
            // 不指定相机拍摄照片保存地址
            case R.id.btn_photo2:
                intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

}
