package com.syl.googleplay3.protocol;

import com.syl.googleplay3.base.BaseProtocol;
import com.syl.googleplay3.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe 网络请求协议,节点解析
 * @Called
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    public List<CategoryBean> parseJsonStr(String resJsonStr) {
        List<CategoryBean> result = new ArrayList<>();
        try {
            //json节点解析,获取节点数组数据
            JSONArray rootJsonArr = new JSONArray(resJsonStr);
            for (int i = 0; i < rootJsonArr.length(); i++) {
                JSONObject itemJsonObject = rootJsonArr.getJSONObject(i);
                String title = itemJsonObject.getString("title");
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setTitle(title);
                categoryBean.setIsTitle(true);
                result.add(categoryBean);//加入集合
                JSONArray infos = itemJsonObject.getJSONArray("infos");
                for (int j = 0; j < infos.length(); j++) {
                    //解析数组
                    JSONObject categoryBeanJsonObject = infos.getJSONObject(j);
                    CategoryBean categoryBean2 = new CategoryBean();
                    categoryBean2.setUrl1(categoryBeanJsonObject.getString("url1"));
                    categoryBean2.setUrl2(categoryBeanJsonObject.getString("url2"));
                    categoryBean2.setUrl3(categoryBeanJsonObject.getString("url3"));
                    categoryBean2.setName1(categoryBeanJsonObject.getString("name1"));
                    categoryBean2.setName2(categoryBeanJsonObject.getString("name2"));
                    categoryBean2.setName3(categoryBeanJsonObject.getString("name3"));
                    result.add(categoryBean2);//加入集合
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInterfaceKey() {
        return "category";
    }
}
