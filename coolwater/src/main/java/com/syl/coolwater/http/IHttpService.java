package com.syl.coolwater.http;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe封装请求
 * @Called
 */
public interface IHttpService {
    void setUrl(String url);
    void setRequestData(byte[] requestData);
    void execute();
    //需要设置两个接口之间的关系
    void setHttpCallBack(IHttpListener httpListener);
}
