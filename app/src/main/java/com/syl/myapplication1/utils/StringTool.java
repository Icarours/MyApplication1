package com.syl.myapplication1.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bright on 2018/7/3.
 *
 * @Describe 处理String的工具类
 * @Called
 */

public class StringTool {
    //将字节流解析为String
    public static String decode(InputStream inputStream) throws IOException {
        //关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException。
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建底层输出流
        int len;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf)) > 0) {
            baos.write(buf, 0, len);
        }
        return baos.toString();
    }
}
