package com.syl.coolwater.http;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe 调用接口,帮助类
 * @Called
 */
public class MyVolly {
    public static <T, M> void sendJsonRequest(T requestInfo,String url,Class<M> response,IDataListener<M> dataListener) {
        JsonHttpService httpService = new JsonHttpService();
        JsonHttpListener httpListener = new JsonHttpListener(response, dataListener);
        HttpTask<T> httpTask = new HttpTask<>(url, requestInfo, httpService, httpListener);
        ThreadPoolManager.getOurInstance().execute(httpTask);
    }
}
