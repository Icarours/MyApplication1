package com.syl.coolwater.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe
 * @Called
 */
public class RequestParameter implements Serializable{
    private String pageNumber;
    private Integer pageSize;
    private String key;

    public RequestParameter(String pageNumber, Integer pageSize, String key) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.key = key;
    }

    public RequestParameter() {
    }

    @Override
    public String toString() {
        return "RequestParameter{" +
                "pageNumber='" + pageNumber + '\'' +
                ", pageSize=" + pageSize +
                ", key='" + key + '\'' +
                '}';
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
