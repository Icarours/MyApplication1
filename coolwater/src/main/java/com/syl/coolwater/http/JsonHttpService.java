package com.syl.coolwater.http;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe json数据请求
 * @Called
 */
public class JsonHttpService implements IHttpService {
    private String mUrl;
    private byte[] mRequestData;
    private IHttpListener mHttpListener;

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        mRequestData = requestData;
    }

    //真实的网络操作在这里实现
    @Override
    public void execute() {
        httpUrlconnPost();
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        mHttpListener = httpListener;
    }

    private HttpURLConnection conn = null;

    public void httpUrlconnPost() {
        URL url = null;
        try {
            url = new URL(mUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);//设置连接超时
            conn.setUseCaches(false);//不使用缓存
            conn.setInstanceFollowRedirects(true);//自动重定向
            conn.setReadTimeout(3000);//读取超时
            conn.setDoInput(true);//可以写入
            conn.setDoOutput(true);//可以发出
            conn.setRequestMethod("POST");//设置请求方法
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置请求返回数据类型和编码格式
            conn.connect();//连接,上述配置必须在该方法调用之前设置完成
            /*------------------ 使用字节流发送数据 -----------------*/
            OutputStream out = conn.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);//缓存字节流包装字节数组
            if (mRequestData != null) {
                bos.write(mRequestData);
            }
            //把这个字节数组的数据写入缓冲区中
            bos.flush();//率先呢缓冲区,发送数据
            out.close();
            bos.close();
            /*------------------ 字节流写入数据 -----------------*/
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {//得到服务器返回的数据
                InputStream in = conn.getInputStream();
                mHttpListener.onSuccess(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHttpListener.onFailure();
        }finally {
            conn.disconnect();//使用完毕,关闭TCP连接,释放资源
        }
    }
}
