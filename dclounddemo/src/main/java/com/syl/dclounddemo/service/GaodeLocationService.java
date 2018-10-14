package com.syl.dclounddemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.syl.dclounddemo.config.MyApplication;
import com.syl.dclounddemo.utils.Utils;

import java.io.IOException;


/**
 * author   Bright
 * date     2018/10/13 1:07
 * desc
 * 高德地图定位服务
 * 这个Service是从别的项目中考过来的,忘了在Manifest文件中注册,结果GaodeLocationService一直不能运行,卡了好久
 */

public class GaodeLocationService extends Service {
    private static final String TAG = GaodeLocationService.class.getSimpleName();
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private StringBuffer mSb = new StringBuffer();
    private MediaPlayer mMediaPlayer;

    public GaodeLocationService() {
        //为了使服务可以一直在后台运行,在服务中开启一个音乐播放器,让音乐播放器在后台循环播放音乐.
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            synchronized (GaodeLocationService.class) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                }
            }
        }

        //音乐播放属于耗时操作,应该放在子线程,否则有可能阻塞主线程
        new Thread() {
            @Override
            public void run() {
                try {
                    AssetManager am = MyApplication.getInstance().getAssets();
                    AssetFileDescriptor afd = am.openFd("tyr.mp3");
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mMediaPlayer.prepare();
                    mMediaPlayer.setVolume(0, 0);//静音
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.seekTo(0);
                            mp.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //开启定位
                initLocation();
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d(TAG, "unbindService()...");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()...");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()...");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()...");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()...");
        return super.onStartCommand(intent, flags, startId);
    }

    class LocationBinder extends Binder implements ILocation {

        @Override
        public void startLocation() {

        }

        @Override
        public void stopLocation() {

        }
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    mSb.append("定位成功" + "\n");
                    mSb.append("定位类型: " + location.getLocationType() + "\n");
                    mSb.append("经    度    : " + location.getLongitude() + "\n");
                    mSb.append("纬    度    : " + location.getLatitude() + "\n");
                    mSb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    mSb.append("提供者    : " + location.getProvider() + "\n");

                    mSb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    mSb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    mSb.append("星    数    : " + location.getSatellites() + "\n");
                    mSb.append("国    家    : " + location.getCountry() + "\n");
                    mSb.append("省            : " + location.getProvince() + "\n");
                    mSb.append("市            : " + location.getCity() + "\n");
                    mSb.append("城市编码 : " + location.getCityCode() + "\n");
                    mSb.append("区            : " + location.getDistrict() + "\n");
                    mSb.append("区域 码   : " + location.getAdCode() + "\n");
                    mSb.append("地    址    : " + location.getAddress() + "\n");
                    mSb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    mSb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    mSb.append("定位失败" + "\n");
                    mSb.append("错误码:" + location.getErrorCode() + "\n");
                    mSb.append("错误信息:" + location.getErrorInfo() + "\n");
                    mSb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                mSb.append("***定位质量报告***").append("\n");
                mSb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                mSb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                mSb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                mSb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                mSb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                mSb.append("****************").append("\n");
                //定位之后的回调时间
                mSb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = mSb.toString();
                Log.d(TAG, "result===" + result);
            } else {
                Log.d(TAG, "定位失败，loc is null");
            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }
}
