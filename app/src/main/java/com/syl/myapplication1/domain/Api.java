package com.syl.myapplication1.domain;

import com.zhouyou.http.model.ApiResult;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe 根据实际情况,只需要复写getMsg()和isOk()两个方法
 * @Called
 */
public class Api<T> extends ApiResult<T> {
    private String message;
    //T data;

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public boolean isOk() {
        return getCode() == 200;
    }
}
