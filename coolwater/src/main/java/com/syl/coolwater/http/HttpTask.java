package com.syl.coolwater.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe 一次用户请求
 * @Called
 */
public class HttpTask<T> implements Runnable {
    private IHttpService mHttpService;
    private IHttpListener mHttpListener;

    public <T> HttpTask(String url, T requestInfo, IHttpService httpService, IHttpListener httpListener) {
        mHttpService = httpService;
        mHttpListener = httpListener;
        mHttpService.setUrl(url);
        mHttpService.setHttpCallBack(mHttpListener);
        if (requestInfo != null) {
            String requestContent = JSON.toJSONString(requestInfo);
            try {
                mHttpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        mHttpService.execute();
    }
}
