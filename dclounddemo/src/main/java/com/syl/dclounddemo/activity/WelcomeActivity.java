package com.syl.dclounddemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.syl.dclounddemo.MainActivity;
import com.syl.dclounddemo.R;
import com.syl.dclounddemo.service.GaodeLocationService;
import com.syl.dclounddemo.service.ILocation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import io.dcloud.application.DCloudApplication;
import io.dcloud.common.adapter.util.Logger;

/**
 * author   Bright
 * date     2018/10/13 1:34
 * desc
 * 欢迎界面
 */
public class WelcomeActivity extends CheckPermissionsActivity {
    public static final String TAG = WelcomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Logger.d("JIGUANG-Example", "[ExampleApplication] onCreate");
        DCloudApplication application = (DCloudApplication) getApplication();
        System.out.println("application==="+application);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

//        initLocation();
        String s = sHA1(this);
        Log.d(TAG, "sHA1==" + s);
        System.out.println("sHA1==" + s);
        Intent i = new Intent(this, GaodeLocationService.class);
        startService(i);
        GaodeLoactionConnection conn = new GaodeLoactionConnection();
        bindService(i, conn, BIND_AUTO_CREATE);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 测试高德地图的sha1值是否正确,核对一下打印出来的sha1值和高德地图控制台注册的sha1是否一致.
     *
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    class GaodeLoactionConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ILocation location = (ILocation) service;
            location.startLocation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
