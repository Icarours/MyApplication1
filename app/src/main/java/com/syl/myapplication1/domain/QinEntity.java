package com.syl.myapplication1.domain;

/**
 * Created by Bright on 2018/9/4.
 *
 * @Describe
 * @Called
 */
public class QinEntity {
    private int id;
    private String name;

    public QinEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public QinEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QinEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
}
