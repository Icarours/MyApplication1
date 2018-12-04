package com.syl.coolwater.http;

import java.io.InputStream;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe 封装响应
 * @Called
 */
public interface IHttpListener {
    //接收上一个接口返回到的结果
    void onSuccess(InputStream inputStream);
    void onFailure();
}
