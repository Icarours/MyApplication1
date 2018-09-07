package com.syl.coolwater;

import java.io.Serializable;

/**
 * Created by Bright on 2018/9/7.
 *
 * @Describe
 * @Called
 */
public class Test1 implements Serializable {
    private int id;
    private int state;
    private String name;

    public Test1(int id, int state, String name) {
        this.id = id;
        this.state = state;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test1{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
