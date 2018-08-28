package com.syl.myapplication1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.syl.myapplication1.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件的上传和下载
 */
public class FileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = FileActivity.class.getSimpleName();
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = 2;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    private Button mBtnUpFile;
    private Button mBtnDownloadFile;
    private EditText mEtPath;
    private ImageView mIv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mIv.setImageBitmap(bitmap);
                    Log.d(TAG, "请求成功..");
                    break;
                case CODE_ERROR:
                    Log.d(TAG, "请求失败..");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBtnUpFile = findViewById(R.id.btn_upFile);
        mBtnDownloadFile = findViewById(R.id.btn_download_file);

        mIv = findViewById(R.id.iv_img);
        mBtnUpFile.setOnClickListener(this);
        mBtnDownloadFile.setOnClickListener(this);
        findViewById(R.id.btn_display_img).setOnClickListener(this);
        mTvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTips.setText("文件的上传和下载,网络请求");
        mEtPath = findViewById(R.id.et_path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upFile:
                upFile2();
//                upFile();
                break;
            case R.id.btn_download_file:
                downloadFile();
                break;
            case R.id.btn_display_img:
                displayImg();
                break;
            default:
                break;
        }
    }

    //使用OkHttp3上传总是失败,
    private void upFile() {
        String filePath = mEtPath.getText().toString().trim();
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "path不能为空", Toast.LENGTH_SHORT).show();
        }
        File file = new File(filePath);
        if (!file.exists() || file.length() < 0) {
            Toast.makeText(this, "文件不存在..", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "文件不存在..");
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"name\""),
                        RequestBody.create(null, "Square Logo"))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file\""),
                        RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.31.89:80/uploaddownload/upload")
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

    }

    private void displayImg() {
        String filePath = mEtPath.getText().toString().trim();
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "path不能为空", Toast.LENGTH_SHORT).show();
        }
        File file = new File(filePath);
        if (!file.exists() || file.length() < 0) {
            Toast.makeText(this, "文件不存在..", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "文件不存在..");
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            mIv.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //下载文件
    private void downloadFile() {
        String url = "http://192.168.31.89:80/mm2.jpg";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = CODE_SUCCESS;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message msg = Message.obtain();
                msg.what = CODE_SUCCESS;
                msg.obj = bitmap;
                mHandler.sendMessage(msg);
            }
        });
    }

    //上传文件
    private void upFile2() {
        String filePath = mEtPath.getText().toString().trim();
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "path不能为空", Toast.LENGTH_SHORT).show();
        }
        File file = new File(filePath);
        if (!file.exists() || file.length() < 0) {
            Toast.makeText(this, "文件不存在..", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "文件不存在..");
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("file", file);

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, "http://192.168.31.89:80/uploaddownload/upload", params, new RequestCallBack<File>() {

            //public abstract void onFailure(HttpException error, String msg);
            @Override
            public void onFailure(HttpException error, String msg) {
                //失败 会被 回调的方法
                Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
                Log.d(TAG, "上传失败..");
            }

            //   public abstract void onSuccess(ResponseInfo<T> responseInfo);
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //成功会被 回调的方法
                Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_LONG).show();
                Log.d(TAG, "上传成功..");
            }
        });
    }
}
