package com.syl.googleplay3.bean;

import java.util.List;

/**
 * Created by j3767 on 2016/11/22.
 *
 * @Describe APP详情的实体类
 * @Called
 */
public class ItemInfoBean {
    private long id;
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private long size;
    private String downloadUrl;
    private String des;


    /*------------------ 额外字段(应用详情里面的字段) -----------------*/
    private String downloadNum;      //"40万+",
    private String version;          //"1.1.0605.0",
    private String date;             //"2015-06-10",
    private String author;           //"黑马程序员",

    public List<SafeBean> safe;     //Array
    public List<String> screen;   //Array

    public class SafeBean {
        public String safeUrl     ;//: "app/com.itheima.www/safeIcon0.jpg",
        public String safeDesUrl  ;//: "app/com.itheima.www/safeDesUrl0.jpg",
        public String safeDes     ;//: "已通过安智市场安全检测，请放心使用",
        public int safeDesColor;//: 0

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public int getSafeDesColor() {
            return safeDesColor;
        }

        public void setSafeDesColor(int safeDesColor) {
            this.safeDesColor = safeDesColor;
        }

        @Override
        public String toString() {
            return "SafeBean{" +
                    "safeUrl='" + safeUrl + '\'' +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeDes='" + safeDes + '\'' +
                    ", safeDesColor=" + safeDesColor +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ItemInfoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<SafeBean> getSafe() {
        return safe;
    }

    public void setSafe(List<SafeBean> safe) {
        this.safe = safe;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }
}
