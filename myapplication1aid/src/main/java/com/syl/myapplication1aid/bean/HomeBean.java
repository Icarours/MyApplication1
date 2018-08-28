package com.syl.myapplication1aid.bean;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * @Called
 */

public class HomeBean {

    private List<String> picture;
    private List<ListBean> list;

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "picture=" + picture +
                ", list=" + list +
                '}';
    }

    public static class ListBean {
        /**
         * id : 1525489
         * name : 黑马程序员
         * packageName : com.itheima.www
         * iconUrl : app/com.itheima.www/icon.jpg
         * stars : 5
         * size : 91767
         * downloadUrl : app/com.itheima.www/com.itheima.www.apk
         * des : 产品介绍：google市场app测试。
         */

        private int id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private int size;
        private String downloadUrl;
        private String des;

        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", stars=" + stars +
                    ", size=" + size +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", des='" + des + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
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
    }
}
