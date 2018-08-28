package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/7/2.
 *
 * @Describe 封装数据,为了后面方便传递数据,记得要序列化
 * @Called
 */

public class Student implements Serializable{
    private int id;
    private String name;
    private String gender;

    public Student() {
    }

    public Student(String name, String gender) {
        this.name = name;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (getId() != student.getId()) return false;
        if (getName() != null ? !getName().equals(student.getName()) : student.getName() != null)
            return false;
        return getGender() != null ? getGender().equals(student.getGender()) : student.getGender() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
