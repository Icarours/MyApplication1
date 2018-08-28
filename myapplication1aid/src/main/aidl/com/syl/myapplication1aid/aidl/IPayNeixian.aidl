// IPayNeixian.aidl
package com.syl.myapplication1aid.aidl;

// Declare any non-default types here with import statements

interface IPayNeixian {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void callPay(String merchantId,double money);
}
