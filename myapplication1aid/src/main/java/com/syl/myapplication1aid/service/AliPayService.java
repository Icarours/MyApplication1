package com.syl.myapplication1aid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.syl.myapplication1aid.aidl.IPayNeixian;

/**
 * 提供支付服务,服务端
 */
public class AliPayService extends Service {
    public AliPayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PayNeiXian();
    }
    class PayNeiXian extends IPayNeixian.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void callPay(String merchantId, double money) throws RemoteException {
            System.out.println("商家=="+merchantId+";money=="+money);
        }
    }
}
