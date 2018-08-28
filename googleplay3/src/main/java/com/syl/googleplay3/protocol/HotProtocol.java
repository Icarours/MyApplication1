package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syl.googleplay3.base.BaseProtocol;

import java.util.List;

/**
 * Created by Bright on 2018/8/3.
 *
 * @Describe
 * @Called
 */

public class HotProtocol extends BaseProtocol<List<String>>{
    @Override
    public List<String> parseJsonStr(String resJsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonStr,new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "hot";
    }
}
