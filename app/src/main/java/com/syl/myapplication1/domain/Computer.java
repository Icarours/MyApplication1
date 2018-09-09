package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/9/8.
 *
 * @Describe
 * @Called
 */
public class Computer implements Serializable{
    private String name;
    private String desc;

    public Computer(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
