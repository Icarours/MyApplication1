package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/10/24.
 *
 * @Describe CheckBoxBean
 * @Called
 */
public class CheckBoxBean implements Serializable{
    private int id;
    private String title;
    private String content;
    private boolean isChecked;

    public CheckBoxBean(int id, String title, String content, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "CheckBoxBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
