package com.syl.myapplication1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syl.myapplication1.R;
import com.syl.myapplication1.domain.ImgBing;
import com.syl.myapplication1.service.LoadImgIntentService;
import com.syl.myapplication1.service.LoadImgService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 两个思路加载显示图片(两种思路都行):
 * 1.直接在本Activity中使用OkHttp异步加载,获取资源,显示图片
 * 2.使用service,在service中使用OkHttp异步加载图片资源,然后将数据通过Connection回传给Activity
 * 第二种方式出现了内存泄露,不推荐这种方式.
 * 绑定service之前,一定要先启动service
 */
public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    private static final int CODE_SUCCESS_DATA_ACTIVITY = 1;
    private static final int CODE_FAILURE_DATA_ACTIVITY = 2;
    private static final int CODE_SUCCESS_LOAD_IMG = 3;
    private static final int CODE_FAILURE_LOAD_IMG = 4;
    @Bind(R.id.btn_start_service)
    Button mBtnStartService;
    @Bind(R.id.btn_stop_service)
    Button mBtnStopService;
    @Bind(R.id.btn_load_img_net)
    Button mBtnLoadImg;
    @Bind(R.id.iv_img)
    ImageView mIv;
    @Bind(R.id.btn_bind_service)
    Button mBtnBindService;
    @Bind(R.id.tv_content)
    TextView mTv;
    @Bind(R.id.btn_unbind)
    Button mBtnUnbind;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private String mData = "data in ServiceActivity";
    private MyHandler mHandler = new MyHandler();//创建一个Handler的子类,专门处理线程间的通信,减少代码数量
    private LoadImgService.LoadImgBinder mLoadImgBinder;
    private LoadImgConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        mBtnStartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);
        mBtnLoadImg.setOnClickListener(this);
        mBtnBindService.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
        mTv.setText(R.string.tv_memory_leak);
        mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
//        mBtnBindService.setVisibility(View.GONE);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTv.setText("在Service中进行网络请求,然后加载图片..");
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(this, LoadImgService.class);
        Intent intent2 = new Intent(this, LoadImgIntentService.class);
        switch (v.getId()) {
            case R.id.btn_start_service:
                //将数据带到Service中.
                intent1.putExtra("data", mData);
                startService(intent1);
//                intent2.putExtra("data", mData);
//                startService(intent2);
                break;
            case R.id.btn_stop_service:
//                stopService(intent1);
                stopService(intent2);
                break;
            case R.id.btn_load_img_net:
                loadImg();
                break;
            case R.id.btn_bind_service:
                btnBindService();//绑定服务
                break;
            case R.id.btn_unbind:
                btnUnbindService();//解除绑定服务
                break;
            default:
                break;
        }
    }

    //解除绑定服务
    private void btnUnbindService() {
        unbindService(mConnection);
    }

    //绑定服务
    private void btnBindService() {
        Intent intent = new Intent(this, LoadImgService.class);
        mConnection = new LoadImgConnection();
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    //加载图片(加载数据)
    private void loadImg() {
        String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "数据请求失败..");
                Message msg = Message.obtain();
                msg.what = CODE_FAILURE_DATA_ACTIVITY;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                Message msg = Message.obtain();
                msg.obj = jsonString;
                msg.what = CODE_SUCCESS_DATA_ACTIVITY;
                mHandler.sendMessage(msg);
            }
        });
    }

    //自定义Handler的子类
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS_DATA_ACTIVITY:
                    String jsonString = (String) msg.obj;
                    dealData(jsonString);//处理json数据
                    break;
                case CODE_FAILURE_DATA_ACTIVITY:
                    Toast.makeText(ServiceActivity.this, "数据加载失败..", Toast.LENGTH_SHORT).show();
                    break;
                case CODE_SUCCESS_LOAD_IMG:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mIv.setImageBitmap(bitmap);
                    break;
                case CODE_FAILURE_LOAD_IMG:
                    Toast.makeText(ServiceActivity.this, "图片加载失败..", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    //处理json数据
    private void dealData(String jsonString) {
        try {
            Gson gson = new Gson();
            ImgBing imgBing = gson.fromJson(jsonString, ImgBing.class);//直接返回ImgBing实体类
            List<ImgBing.ImagesBean> imagesBeans = imgBing.getImages();
            ImgBing.ImagesBean imagesBean = imagesBeans.get(0);
            String url = imagesBean.getUrl();
            loadImgFromNet(url);//获得url,加载图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载图片
    private void loadImgFromNet(String url1) {
        String url = "http://s.cn.bing.net" + url1;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "load img failure");
                Message msg = Message.obtain();
                msg.what = CODE_FAILURE_LOAD_IMG;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message msg = Message.obtain();
                msg.what = CODE_SUCCESS_LOAD_IMG;
                msg.obj = bitmap;
                mHandler.sendMessage(msg);
            }
        });
    }

    private class LoadImgConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLoadImgBinder = (LoadImgService.LoadImgBinder) service;
            String jsonString = mLoadImgBinder.loadDataInService();
//            Log.d(TAG, "jsonString--==" + jsonString);
            dealData(jsonString);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLoadImgBinder = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
