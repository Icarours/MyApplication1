package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.HomeBean;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 *  Home网络请求的协议封装
 * @Called
 */

public class HomeProtocol extends BaseProtocol<HomeBean>{

    @Override
    public HomeBean parseJsonStr(String resJsonStr) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(resJsonStr, HomeBean.class);
        return homeBean;
    }

    @Override
    public String getInterfaceKey() {
        return "home";
    }
}
