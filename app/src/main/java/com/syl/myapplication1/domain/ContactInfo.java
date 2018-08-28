package com.syl.myapplication1.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bright on 2018/7/15.
 *
 * @Describe 联系人实体类
 * @Called
 */

public class ContactInfo implements Parcelable{
    public String name;
    public String email;
    public String phone ;
    public String im ;

    protected ContactInfo(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        im = in.readString();
    }

    public ContactInfo() {
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", im='" + im + '\'' +
                '}';
    }

    public ContactInfo(String name, String email, String phone, String im) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.im = im;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(im);
    }
}
