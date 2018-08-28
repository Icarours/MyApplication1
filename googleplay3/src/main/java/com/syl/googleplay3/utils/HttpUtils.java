package com.syl.googleplay3.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     Administrator
 * 创建时间   2016/3/27 14:31
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-03-27 14:35:58 +0800 (星期日, 27 三月 2016) $
 * 更新描述   拼接网络请求的参数
 */
public class HttpUtils {
    public static String getUrlParamsByMap(HashMap<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
