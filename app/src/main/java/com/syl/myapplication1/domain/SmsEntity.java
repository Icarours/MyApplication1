package com.syl.myapplication1.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bright on 2018/7/14.
 *
 * @Describe 短信的实体类,短信数据表中有多个字段,现在只取了其中四个字段
 * @Called
 */

public class SmsEntity implements Parcelable{
    private String body;
    private String date;
    private String type;
    private String address;

    public SmsEntity() {
    }

    public SmsEntity(String body, String date, String type, String address) {
        this.body = body;
        this.date = date;
        this.type = type;
        this.address = address;
    }

    protected SmsEntity(Parcel in) {
        body = in.readString();
        date = in.readString();
        type = in.readString();
        address = in.readString();
    }

    @Override
    public String toString() {
        return "SmsEntity{" +
                "body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(date);
        dest.writeString(type);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SmsEntity> CREATOR = new Creator<SmsEntity>() {
        @Override
        public SmsEntity createFromParcel(Parcel in) {
            return new SmsEntity(in);
        }

        @Override
        public SmsEntity[] newArray(int size) {
            return new SmsEntity[size];
        }
    };
}
