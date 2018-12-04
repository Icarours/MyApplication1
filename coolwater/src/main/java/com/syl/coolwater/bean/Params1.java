package com.syl.coolwater.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe
 * @Called
 */
public class Params1 implements Serializable{

    /**
     * pageSize : 5000
     * pageNumber : 1
     * searchParam : 区域
     */

    @Override
    public String toString() {
        return "Params1{" +
                "pageSize='" + pageSize + '\'' +
                ", pageNumber='" + pageNumber + '\'' +
                ", searchParam='" + searchParam + '\'' +
                '}';
    }

    public Params1(String pageSize, String pageNumber, String searchParam) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.searchParam = searchParam;
    }

    public Params1() {
    }

    private String pageSize;
    private String pageNumber;
    private String searchParam;

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
}
