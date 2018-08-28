package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.ItemInfoBean;

import java.util.List;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public class AppProtocol extends BaseProtocol<List<ItemInfoBean>> {
    @Override
    public List<ItemInfoBean> parseJsonStr(String resJsonStr) {
        Gson gson = new Gson();
        //此处应该使用泛型解析
        return gson.fromJson(resJsonStr, new TypeToken<List<ItemInfoBean>>() {
        }.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "app";
    }
}
