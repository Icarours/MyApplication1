package com.syl.googleplay3.bean;

/**
 * Created by Bright on 2018/8/2.
 *
 * @Describe
 * @Called
 */

public class CategoryBean {

    /**
     * url1 : image/category_game_0.jpg
     * url2 : image/category_game_1.jpg
     * url3 : image/category_game_2.jpg
     * name1 : 休闲
     * name2 : 棋牌
     * name3 : 益智
     */

    private String url1;
    private String url2;
    private String url3;
    private String name1;
    private String name2;
    private String name3;
    private String title;//游戏
    private boolean isTitle;

    @Override
    public String toString() {
        return "CategoryBean{" +
                "url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", title='" + title + '\'' +
                ", isTitle=" + isTitle +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(boolean title) {
        isTitle = title;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }
}
