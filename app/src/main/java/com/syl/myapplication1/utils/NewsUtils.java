package com.syl.myapplication1.utils;

import android.util.Xml;

import com.syl.myapplication1.domain.NewsItem;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2018/7/4.
 *
 * @Describe 新闻的帮助类
 * @Called
 */

public class NewsUtils {

    private static final String TAG = NewsUtils.class.getSimpleName();

    //用于 返回所有的新闻的条目信息,这里会涉及 到链接服务器, 并且解析服务器返回的数据
    public static List<NewsItem> getAllItems(String path) {

        List<NewsItem> list = new ArrayList<NewsItem>();
        try {

            //准备一个 list , 将 每个 解析后封装了信息的 NewsItem 对象放到 list中去


            URL url = new URL(path);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置 连接超时时间
            conn.setConnectTimeout(5000);

            int code = conn.getResponseCode();
            if(code==200){
                //获得 服务器返回的流的数据
                InputStream in = conn.getInputStream();

                //由于这个流的数据是 xml 格式的数据, 所以这里要解析 xml 了, 解析xml , 要使用 pull解析器
                XmlPullParser pullParser = Xml.newPullParser();

                //设置要解析的流
                pullParser.setInput(in, "UTF-8");
//                String decode = StringTool.decode(in);
//                Log.d(TAG, "data==" + decode);
                //模版代码
                int eventType = pullParser.getEventType();
/*
 *
<item>
 <title>军报评徐才厚</title>
  <description>人死账不消 反腐步不停，支持，威武，顶，有希望了。 </description>
  <image>http://188.188.6.100:8080/img/a.jpg</image>
  <type>1</type>
  <comment>163</comment>
</item>
 */
                NewsItem item = null;
                while(eventType!=XmlPullParser.END_DOCUMENT){

                    if(eventType==XmlPullParser.START_TAG){

                        //具体判断是哪个 开始标签
                        if("item".equals(pullParser.getName())){

                            //当碰到 item的开始 标签的时候,  需要去 new 一个 NewsItem对象了
                            item = new NewsItem();
                        }else if("title".equals(pullParser.getName())){
                            item.setTitle(pullParser.nextText());
                        }else if("description".equals(pullParser.getName())){
                            item.setDescription(pullParser.nextText());
                        }else if("image".equals(pullParser.getName())){
                            item.setImage(pullParser.nextText());

                        }else if("type".equals(pullParser.getName())){
                            item.setType(Integer.parseInt(pullParser.nextText()));

                        }else if("comment".equals(pullParser.getName())){

                            item.setComment(Integer.parseInt(pullParser.nextText()));
                        }
                    }else if(eventType==XmlPullParser.END_TAG){

                        if("item".equals(pullParser.getName())){

                            //说明 是item的结束 标签 , 需要将item 添加到 list中去
                            list.add(item);
                        }
                    }
                    eventType=pullParser.next();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
