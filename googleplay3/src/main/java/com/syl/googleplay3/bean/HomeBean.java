package com.syl.googleplay3.bean;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * @Called
 */

public class HomeBean {

    private List<String> picture;
    private List<ItemInfoBean> list;

    @Override
    public String toString() {
        return "HomeBean{" +
                "picture=" + picture +
                ", list=" + list +
                '}';
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<ItemInfoBean> getList() {
        return list;
    }

    public void setList(List<ItemInfoBean> list) {
        this.list = list;
    }

}
