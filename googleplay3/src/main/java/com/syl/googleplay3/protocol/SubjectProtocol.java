package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.SubjectInfoBean;

import java.util.List;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe
 * @Called
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>> {
    @Override
    public List<SubjectInfoBean> parseJsonStr(String resJsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonStr,new TypeToken<List<SubjectInfoBean>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}
