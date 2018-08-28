package com.syl.myapplication1.domain;

import java.io.Serializable;

/**
 * Created by Bright on 2018/7/4.
 *
 * @Describe 实体类,封装数据,为了后面方便传递数据,记得要序列化
 * @Called
 */

public class Forecast implements Serializable{

    /**
     * date : 3日星期二
     * high : 高温 21℃
     * fengli :
     * low : 低温 19℃
     * fengxiang : 东风
     * type : 中雨
     */

    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;

    public Forecast() {
    }

    public Forecast(String date, String high, String fengli, String low, String fengxiang, String type) {
        this.date = date;
        this.high = high;
        this.fengli = fengli;
        this.low = low;
        this.fengxiang = fengxiang;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", fengli='" + fengli + '\'' +
                ", low='" + low + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast)) return false;

        Forecast forecast = (Forecast) o;

        if (getDate() != null ? !getDate().equals(forecast.getDate()) : forecast.getDate() != null)
            return false;
        if (getHigh() != null ? !getHigh().equals(forecast.getHigh()) : forecast.getHigh() != null)
            return false;
        if (getFengli() != null ? !getFengli().equals(forecast.getFengli()) : forecast.getFengli() != null)
            return false;
        if (getLow() != null ? !getLow().equals(forecast.getLow()) : forecast.getLow() != null)
            return false;
        if (getFengxiang() != null ? !getFengxiang().equals(forecast.getFengxiang()) : forecast.getFengxiang() != null)
            return false;
        return getType() != null ? getType().equals(forecast.getType()) : forecast.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getHigh() != null ? getHigh().hashCode() : 0);
        result = 31 * result + (getFengli() != null ? getFengli().hashCode() : 0);
        result = 31 * result + (getLow() != null ? getLow().hashCode() : 0);
        result = 31 * result + (getFengxiang() != null ? getFengxiang().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
