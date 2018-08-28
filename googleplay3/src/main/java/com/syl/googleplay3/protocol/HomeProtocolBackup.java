package com.syl.googleplay3.protocol;

import com.google.gson.Gson;
import com.syl.googleplay3.bean.HomeBean;
import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.utils.HttpUtils;
import com.syl.googleplay3.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 *  Home网络请求的协议封装
 * @Called
 */

public class HomeProtocolBackup {
    private static final String TAG = HomeProtocolBackup.class.getSimpleName();
    private HomeBean mHomeBean;

    /**
     * 对于代码中的异常是要抛还是try catch取决于该异常对代码的影响,如果不用该异常就直接抛出,如果要用到该异常,就try catch
     * @return HomeBean
     * @throws IOException
     */
    public HomeBean loadData() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Constants.URLS.BASEURL + "home";
        HashMap<String, Object> map = new HashMap<>();
        map.put("index", "" + 0);
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(map);
        url = url + "?" + urlParamsByMap;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String resJsonStr = response.body().string();
            LogUtils.d(TAG, "resJsonStr==" + resJsonStr);
            Gson gson = new Gson();
            mHomeBean = gson.fromJson(resJsonStr, HomeBean.class);
            LogUtils.d(TAG, "homeBean==" + mHomeBean);
            return mHomeBean;
        }
        return null;
    }
}
