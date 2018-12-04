package com.syl.coolwater.http;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe 对象接口
 * @Called
 */
public interface IDataListener<M> {
    void onSuccess(M m);

    void onFailure();
}
