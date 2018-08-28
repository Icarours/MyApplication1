package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/7/4.
 *
 * @Describe 实体类,封装数据,为了后面方便传递数据,记得要序列化
 * @Called
 */

public class NewsItem implements Serializable{
    private String title;
    private String description;
    private String image;
    private int type;//评论,视频,直播
    private int comment;

    public NewsItem() {
    }

    public NewsItem(String title, String description, String image, int type, int comment) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", comment=" + comment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsItem)) return false;

        NewsItem newsItem = (NewsItem) o;

        if (getType() != newsItem.getType()) return false;
        if (getComment() != newsItem.getComment()) return false;
        if (getTitle() != null ? !getTitle().equals(newsItem.getTitle()) : newsItem.getTitle() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(newsItem.getDescription()) : newsItem.getDescription() != null)
            return false;
        return getImage() != null ? getImage().equals(newsItem.getImage()) : newsItem.getImage() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + getType();
        result = 31 * result + getComment();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
