package com.syl.googleplay3.base;

import com.syl.googleplay3.config.Constants;
import com.syl.googleplay3.config.MyApplication;
import com.syl.googleplay3.utils.FileUtils;
import com.syl.googleplay3.utils.HttpUtils;
import com.syl.googleplay3.utils.IOUtils;
import com.syl.googleplay3.utils.LogUtils;
import com.syl.googleplay3.utils.UIUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bright on 2018/8/1.
 *
 * @Describe
 * @Called
 */

public abstract class BaseProtocol<T> {
    //泛型T是返回的数据
    private static final String TAG = BaseProtocol.class.getSimpleName();
    /**
     * 对于代码中的异常是要抛还是try catch取决于该异常对代码的影响,如果不用该异常就直接抛出,如果要用到该异常,就try catch
     * @return HomeBean
     * @throws IOException
     */
    public T loadData(int index) throws IOException {
        T t;
        //1.从内存中加载数据
        MyApplication application = (MyApplication) UIUtils.getContext();
        Map<String, String> protocolMap = application.getMemProtocolMap();
        String key = generateKay(index);
        if (protocolMap.containsKey(key)){
            String resJsonStr = protocolMap.get(key);
            t = parseJsonStr(resJsonStr);
            LogUtils.d(TAG,"从内存加载数据..");
            return t;
        }
        //2.在从本地磁盘中查找,如果有数据,存内存,返回数据
        t = loadDataFromLocal(key);
        if (t != null) {
            return t;
        }
        //3.从网络加载数据
        t = loadDataFromNet(index);
        return t;
    }

    /**
     * 从本地硬盘加载数据
     * @param key
     * @return
     */
    private T loadDataFromLocal(String key) {
        LogUtils.d(TAG,"从本地加载数据..");
        BufferedReader bufferedReader = null;
        try {
            File cacheFile = getCacheFile(key);
            if (cacheFile.exists()){
                bufferedReader = new BufferedReader(new FileReader(cacheFile));
                String cacheInsertTime = bufferedReader.readLine();
                long cacheInsertTime_ = Long.parseLong(cacheInsertTime);//本地缓存文件创建的时间
                //判断是否过期
                if (System.currentTimeMillis()-cacheInsertTime_>Constants.PROTOCOLTIMEOUT){
                    String resJsonStr = bufferedReader.readLine();
                    LogUtils.v(TAG, getCacheFile(key).getAbsolutePath() + "---从本地磁盘加载数据");
                    MyApplication application = (MyApplication) UIUtils.getContext();
                    Map<String, String> memProtocolMap = application.getMemProtocolMap();
                    memProtocolMap.put(key,resJsonStr);
                    LogUtils.v(TAG, getCacheFile(key).getAbsolutePath() + "---把数据存在本地磁盘");
                    T t = parseJsonStr(resJsonStr);
                    return t;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedReader);
        }
        return null;
    }

    /**
     * 得到缓存文件
     * @param key
     * @return
     */
    private File getCacheFile(String key) {
        String dir = FileUtils.getDir("json");//外置sd卡应用的缓存目录,sdcard/Android/data/包名/json/
        String fileName = key;
        return new File(dir, fileName);
    }

    /**
     * 缓存的key值必须是唯一命中
     * 定义缓存key的生成方式,子类可以修改key的生成方式,可以选择性复写
     * @param index
     * @return
     */
    public String generateKay(int index) {
        return getInterfaceKey() + index;//默认情况
    }

    private T loadDataFromNet(int index) throws IOException {
        LogUtils.d(TAG,"从网络加载数据..");
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Constants.URLS.BASEURL + getInterfaceKey();
        HashMap<String, Object> map = getRequestParams(index);
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(map);
        url = url + "?" + urlParamsByMap;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String resJsonStr = response.body().string();
            //存缓存
            MyApplication application = (MyApplication) UIUtils.getContext();
            Map<String, String> memProtocolMap = application.getMemProtocolMap();
            String key = generateKay(index);
            memProtocolMap.put(key,resJsonStr);
            //存本地
            LogUtils.v(TAG, "保存数据到内存----" + key);
            //将从网络请求返回的数据缓存到本地磁盘
            writeJsonString2Local(key, resJsonStr);
            return parseJsonStr(resJsonStr);
        }
        return null;
    }

    /**
     * 保存网络请求返回的数据到本地磁盘
     * @param key
     * @param resJsonStr
     */
    private void writeJsonString2Local(String key, String resJsonStr) {
        LogUtils.v(TAG, "保存数据到本地磁盘----" + getCacheFile(key).getAbsolutePath());
        BufferedWriter bufferedWriter =null;
        try {
            File cacheFile = getCacheFile(key);
            bufferedWriter = new BufferedWriter(new FileWriter(cacheFile));
            bufferedWriter.write(System.currentTimeMillis()+"");//第一行写入当前的存储的时间
            bufferedWriter.newLine();//换行
            bufferedWriter.write(resJsonStr);//写入需要缓存的数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedWriter);
        }
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
        defaultParams.put("index", index + "");
        return defaultParams;//默认情况
    }
    /**
     * 解析json数据
     * json
     * json-->jsonString-->bean-->Class-->bean解析
     * json-->jsonString-->List Map -->type -->泛型解析
     * @param resJsonStr
     * @return
     */
    public abstract T parseJsonStr(String resJsonStr);


    /**
     * 决定协议关键字
     * @return
     */
    public abstract String getInterfaceKey();
}
