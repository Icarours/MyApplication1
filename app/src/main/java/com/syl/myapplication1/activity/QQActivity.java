package com.syl.myapplication1.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.myapplication1.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * QQ登录界面
 */
public class QQActivity extends AppCompatActivity {

    private static final String TAG = QQActivity.class.getSimpleName();
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private CheckBox mCbRemember;
    private TextView mTvInfo;

    private String username = "";
    private String password = "";
    private boolean isChecked;
    private SharedPreferences spInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        //请求授权
        //requestPermission();

        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mCbRemember = findViewById(R.id.cb_remember);
        mTvInfo = findViewById(R.id.tv_info);
        loginQQ();//授权之后的正常调用.
    }

    private void requestPermission() {
        //如果没有被授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    //继续执行原来的代码
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                //loginQQ();//授权之后的第一次调用,其实这儿什么都不用做
            } else {
                // Permission Denied
                Toast.makeText(this, "请授权..", Toast.LENGTH_SHORT).show();
                requestPermission();//如果拒绝授权,就再次申请权限
            }
        }
    }

    //继续运行,点击事件
    private void loginQQ() {
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.btn_display).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });
        findViewById(R.id.btn_save_sp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spInfo = getSharedPreferences("info", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = spInfo.edit();
                spInfo.edit()
                        .putString("username", username)
                        .putString("password", password)
                        .putBoolean("isChecked", isChecked)
                        .apply();
            }
        });
        findViewById(R.id.btn_display_sp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spInfo = getSharedPreferences("info", Context.MODE_PRIVATE);
                String username = spInfo.getString("username", "");
                String password = spInfo.getString("password", "");
                Boolean isChecked = spInfo.getBoolean("isChecked", false);
                String str = username + password + isChecked;
                mTvInfo.setText(str);
                System.out.println(str);
            }
        });
    }

    private void display() {
        BufferedReader br = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "info.txt");
            br = new BufferedReader(new FileReader(file));
            String msg = br.readLine();
            String username = msg.split("-")[0];
            String password = msg.split("-")[1];
            mTvInfo.setText(username + "-" + password);
            //获得手机sd的总的内存空间和空闲内存空间大小
            getSDSize();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获得sd卡的内存大小
    private void getSDSize() {
        long totalSpace = Environment.getExternalStorageDirectory().getTotalSpace();//总的内存
        long usableSpace = Environment.getExternalStorageDirectory().getUsableSpace();//可用的内存
        long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();//空闲内存,注意:空闲内存比可用内存要大一点
        System.out.println("totalSpace=" + Formatter.formatFileSize(this, totalSpace));
        System.out.println("usableSpace=" + Formatter.formatFileSize(this, usableSpace));
        System.out.println("freeSpace=" + Formatter.formatFileSize(this, freeSpace));
    }

    /**
     * 登录,保存用户名和密码到缓存文件下的info.txt文件中
     */
    private void login() {
        username = mEtUsername.getText().toString();
        password = mEtPassword.getText().toString();
        isChecked = mCbRemember.isChecked();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或者密码为空..", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "用户名或者密码为空..");
            return;
        }
        if (isChecked) {
//            File file = saveCache();//保存到cache文件件
//            File file = saveFiles();//保存到files文件夹
            File file = saveExternalSd();//保存到sd卡
            String msg = username + "-" + password;
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(msg.getBytes());
                os.close();
                Toast.makeText(this, "保存成功..", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "保存成功.." + msg);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "没有发现文件..", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "没有发现文件..");
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, "读写失败..", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "读写失败..");
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private File saveExternalSd() {
        return new File(Environment.getExternalStorageDirectory(), "info.txt");
//        return new File("/mnt/sdcard","info.txt");
    }

    //将info.txt保存到/data/data/包名/files/info.txt
    @NonNull
    private File saveFiles() {
        return new File(getFilesDir(), "info.txt");
    }

    //将info.txt保存到/data/data/包名/cache/info.txt
    @NonNull
    private File saveCache() {
        return new File(getCacheDir(), "info.txt");
    }

    //获得授权的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);//继续执行原来的代码
    }
}

