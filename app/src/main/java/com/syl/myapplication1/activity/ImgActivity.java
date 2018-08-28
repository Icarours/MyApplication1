package com.syl.myapplication1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加载图片
 */
public class ImgActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 2;
    private static final String TAG = ImgActivity.class.getSimpleName();

    @Bind(R.id.btn_load_img_local)
    Button mBtnLoadImgLocal;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);//父类的空方法可以删掉
            switch (msg.what) {
                case SUCCESS_CODE:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mIv.setImageBitmap(bitmap);
                    Log.d(TAG, "图片加载成功..");
                    break;
                case ERROR_CODE:
                    Toast.makeText(ImgActivity.this, "图片加载失败..", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "图片加载失败..");
                    break;
                default:
                    break;
            }
        }
    };
    private EditText mEtPath;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ButterKnife.bind(this);
        mEtPath = findViewById(R.id.et_path);
        mIv = findViewById(R.id.iv_img);
        findViewById(R.id.btn_load_img_net).setOnClickListener(this);
        mBtnLoadImgLocal.setOnClickListener(this);
        mTvTips.setText("加载网络图片,加载本地图片");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_img_net:
                loadImgFromNet();
                break;
            case R.id.btn_load_img_local:
                loadImgFromLocal();
                break;
            default:
                break;
        }
    }

    /**
     * 从手机本地加载图片
     */
    private void loadImgFromLocal() {
        //获得窗口管理器
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //获得图像相关的信息(手机设备)
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        Log.d(TAG, "width==" + width + ",height==" + height);
        //创建一个解码图片的选项
        BitmapFactory.Options options = new BitmapFactory.Options();
        String widthImg = null;
        String heightImg = null;
        try {
            ExifInterface exif = new ExifInterface("/mnt/sdcard/img1.jpg");
            widthImg = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            heightImg = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            Log.d(TAG, "widthImg=" + widthImg + ";heightImg=" + heightImg);
            /*
           //如果是PNG的图片，请以以下方式获取宽高
		   Options opt = new Options();

		   //不返回位图图像，只要图像的分辨率即可。
		   opt.inJustDecodeBounds = true;
		   Bitmap bitmap =  BitmapFactory.decodeFile("/mnt/sdcard/biger.jpg" ,opt);

		   opt.outHeight;
		   opt.outWidth;*/
        } catch (IOException e) {
            e.printStackTrace();
        }
//        options.inSampleSize = 16;//设置采样率
        boolean b = Integer.parseInt(widthImg) / width > Integer.parseInt(heightImg) / height;//判断宽宽比和高高比那个大,就取那个
        options.inSampleSize = b ? Integer.parseInt(widthImg) / width : Integer.parseInt(heightImg) / height;//设置采样率
        Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/img1.jpg", options);
        mIv.setImageBitmap(bitmap);
    }

    private void loadImgFromNet() {
        final String imgPath = mEtPath.getText().toString();
        if (TextUtils.isEmpty(imgPath)) {
            Toast.makeText(this, "图片的URL地址不能为空..", Toast.LENGTH_SHORT).show();
        }
        new Thread() {
            @Override
            public void run() {
//                super.run();//如果父类方法为空,super.run()可以删掉
                try {
                    URL url = new URL(imgPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5 * 1000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS_CODE;//识别码
                        msg.obj = bitmap;//传递的数据
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR_CODE;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
}
