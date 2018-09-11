package com.syl.myapplication1.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.syl.myapplication1.R;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends TakePhotoActivity implements View.OnClickListener {

    @Bind(R.id.btn_select_photo)
    Button mBtnSelectPhoto;
    @Bind(R.id.btn_take_photo)
    Button mBtnTakePhoto;
    @Bind(R.id.iv_capture)
    ImageView mIvCapture;
    private TakePhoto mTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        mBtnSelectPhoto.setOnClickListener(this);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnTakePhoto.setVisibility(View.GONE);//隐藏拍照按钮
        //获取TakePhoto实例
        mTakePhoto = getTakePhoto();
    }

    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig;  //压缩参数

    @Override
    public void onClick(View v) {
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        switch (v.getId()) {
            case R.id.btn_select_photo:
                //裁剪参数
                mTakePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
                mTakePhoto.onPickMultiple(6);
                break;
            case R.id.btn_take_photo:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                Uri imageUri = Uri.fromFile(file);//图片保存路径

                configCompress(mTakePhoto);
                configTakePhotoOption(mTakePhoto);
                mTakePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
//                Glide.with(PhotoActivity.this).load(imageUri).into(mIvCapture);
                break;
            default:
                break;
        }
    }

    boolean isPickWithOwn = false;
    boolean isCorrectTool = false;

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (!isPickWithOwn) {//是否使用本地相册自带的裁切工具
            builder.setWithOwnGallery(true);
        }
        if (!isCorrectTool) {//是否裁切
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }

    public int DEFAULT_TOOL;//压缩工具
    boolean isCompress = false;

    private void configCompress(TakePhoto takePhoto) {
        if (!isCompress) {
            //是否压缩.第二个参数,是否显示进度条
            takePhoto.onEnableCompress(null, false);
            return;
        }
        int maxSize = 5 * 1024;
        int width = 800;//像素值pix
        int height = 800;//像素值pix
        boolean showProgressBar = true;
        boolean enableRawFile = false;
        CompressConfig config;
        if (DEFAULT_TOOL == CompressTool.COMPRESS_OWN) {
            config = new CompressConfig.Builder().setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    class CompressTool {

        public static final int COMPRESS_OWN = 1;
        public static final int COMPRESS_LU_BAN = 2;
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        System.out.println("out  ---  result===" + result);
        showImg(result.getImages());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
}
