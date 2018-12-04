package com.syl.coolwater.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe
 * @Called
 */
public class JsonHttpListener<M> implements IHttpListener {
    Class<M> responseClass;
    IDataListener<M> mDataListener;
    //用于切换线程
    Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> dataListener) {
        this.responseClass = responseClass;
        mDataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //获取响应结果,把byte数据转换成String数据
        String content = getContent(inputStream);
        //json字符串转化为对象
        final M response = JSON.parseObject(content, responseClass);
        //把结果传送到调用层
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDataListener != null) {
                    mDataListener.onSuccess(response);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        //把结果传送到调用层
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDataListener != null) {
                    mDataListener.onFailure();
                }
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error==" + e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println("Error==" + e.toString());
            }
        }
        return sb.toString();
    }
}
