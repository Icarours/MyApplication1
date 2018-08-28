package com.syl.myapplication1.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BanzhengService extends Service {
    private static final String TAG = BanzhengService.class.getSimpleName();

    public BanzhengService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind()...");
        return new XiaoMi();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d(TAG,"unbindService()...");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind()...");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate()...");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()...");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()...");
        return super.onStartCommand(intent, flags, startId);
    }

    public class XiaoMi extends Binder implements IInnerService {

        @Override
        public void banZheng(String name, double money) {
            if(money<150){
                Toast.makeText(getApplicationContext(), "对不起,这点钱不够..我们需要按制度办事儿...", Toast.LENGTH_LONG	).show();
                return;
            }
            methodInService(name,money);
        }
    }

    private void methodInService(String name, double money) {
        System.out.println(name +" , 您的证办好了 ...  付款 " + money);
    }
}
