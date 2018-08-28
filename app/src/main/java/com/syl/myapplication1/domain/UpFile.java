package com.syl.myapplication1.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Bright on 2018/7/5.
 *
 * @Describe 封装上传文件的实体类
 * @Called
 */

public class UpFile implements Serializable{
    int id;
    String uuidname;
    String filename;
    String savepath;
    Timestamp uploadtime;
    String username;

    public UpFile() {
    }

    public UpFile(int id, String uuidname, String filename, String savepath, Timestamp uploadtime, String username) {
        this.id = id;
        this.uuidname = uuidname;
        this.filename = filename;
        this.savepath = savepath;
        this.uploadtime = uploadtime;
        this.username = username;
    }

    @Override
    public String toString() {
        return "UpFile{" +
                "id=" + id +
                ", uuidname='" + uuidname + '\'' +
                ", filename='" + filename + '\'' +
                ", savepath='" + savepath + '\'' +
                ", uploadtime=" + uploadtime +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpFile)) return false;

        UpFile upFile = (UpFile) o;

        if (getId() != upFile.getId()) return false;
        if (getUuidname() != null ? !getUuidname().equals(upFile.getUuidname()) : upFile.getUuidname() != null)
            return false;
        if (getFilename() != null ? !getFilename().equals(upFile.getFilename()) : upFile.getFilename() != null)
            return false;
        if (getSavepath() != null ? !getSavepath().equals(upFile.getSavepath()) : upFile.getSavepath() != null)
            return false;
        if (getUploadtime() != null ? !getUploadtime().equals(upFile.getUploadtime()) : upFile.getUploadtime() != null)
            return false;
        return getUsername() != null ? getUsername().equals(upFile.getUsername()) : upFile.getUsername() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getUuidname() != null ? getUuidname().hashCode() : 0);
        result = 31 * result + (getFilename() != null ? getFilename().hashCode() : 0);
        result = 31 * result + (getSavepath() != null ? getSavepath().hashCode() : 0);
        result = 31 * result + (getUploadtime() != null ? getUploadtime().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuidname() {
        return uuidname;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public Timestamp getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Timestamp uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
