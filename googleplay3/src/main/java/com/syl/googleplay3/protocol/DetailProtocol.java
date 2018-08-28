package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.ItemInfoBean;

import java.util.HashMap;

/**
 * Created by Bright on 2018/8/3.
 *
 * @Describe
 * @Called
 */

public class DetailProtocol extends BaseProtocol<ItemInfoBean> {
    private String packageName;

    public DetailProtocol(String packageName) {
        this.packageName =packageName;
    }

    @Override
    public ItemInfoBean parseJsonStr(String resJsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonStr,ItemInfoBean.class);
    }

    @Override
    public String getInterfaceKey() {
        return "detail";
    }
    /**
     * 得到请求参数对应的hashMap值
     * 请求参数对应的Map集合可能是其他情况,所以交给子类去复写
     *
     * @param index
     * @return
     */
    public HashMap<String, Object> getRequestParams(int index) {
        HashMap<String, Object> defaultParams = new HashMap<>();
        defaultParams.put("packageName", packageName);
        return defaultParams;
    }

    @Override
    public String generateKay(int index) {
        return packageName;
    }
}
