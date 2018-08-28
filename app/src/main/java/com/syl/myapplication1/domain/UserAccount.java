package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/7/3.
 *
 * @Describe 封装数据,为了后面方便传递数据,记得要序列化
 * @Called
 */

public class UserAccount implements Serializable{
    private int id;
    private String name;
    private double money;

    public UserAccount() {
    }

    public UserAccount(String name, double money) {
        this.name = name;
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;

        UserAccount that = (UserAccount) o;

        if (getId() != that.getId()) return false;
        if (Double.compare(that.getMoney(), getMoney()) != 0) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        temp = Double.doubleToLongBits(getMoney());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
