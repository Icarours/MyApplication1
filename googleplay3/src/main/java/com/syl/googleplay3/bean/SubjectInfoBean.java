package com.syl.googleplay3.bean;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe
 * @Called
 */

public class SubjectInfoBean {

    /**
     * des : 文艺VS2B
     * url : image/recommend_02.jpg
     */

    private String des;
    private String url;

    @Override
    public String toString() {
        return "SubjectInfoBean{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
