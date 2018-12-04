package com.syl.coolwater.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2018/11/24.
 *
 * @Describe
 * @Called
 */
public class WarnMessage implements Serializable{

    /**
     * id : 72703693b88343baadaf72fe862835ad
     * warn_type : 未穿戴工服工帽
     * camera_id : C16937513
     * camera_name :
     * warn_picture : http://llyc.oss-cn-beijing.aliyuncs.com/upload/zhineng/C16937513_201811231350540420.jpg
     * warn_video : http://llyc.oss-cn-beijing.aliyuncs.com/upload/zhineng/
     * is_processed : 0
     * processed_person_id : null
     * processed_time : null
     * processed_content : null
     * processed_image : null
     * processed_video : null
     * create_time : 2018-11-23 16:40:51
     * person_id : null
     */

    private String id;
    private String warn_type;
    private String camera_id;
    private String camera_name;
    private String warn_picture;
    private String warn_video;
    private String is_processed;
    private String processed_person_id;
    private String processed_time;
    private String processed_content;
    private String processed_image;
    private String processed_video;
    private String create_time;
    private String person_id;

    @Override
    public String toString() {
        return "WarnMessage{" +
                "id='" + id + '\'' +
                ", warn_type='" + warn_type + '\'' +
                ", camera_id='" + camera_id + '\'' +
                ", camera_name='" + camera_name + '\'' +
                ", warn_picture='" + warn_picture + '\'' +
                ", warn_video='" + warn_video + '\'' +
                ", is_processed='" + is_processed + '\'' +
                ", processed_person_id='" + processed_person_id + '\'' +
                ", processed_time='" + processed_time + '\'' +
                ", processed_content='" + processed_content + '\'' +
                ", processed_image='" + processed_image + '\'' +
                ", processed_video='" + processed_video + '\'' +
                ", create_time='" + create_time + '\'' +
                ", person_id='" + person_id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarn_type() {
        return warn_type;
    }

    public void setWarn_type(String warn_type) {
        this.warn_type = warn_type;
    }

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public String getCamera_name() {
        return camera_name;
    }

    public void setCamera_name(String camera_name) {
        this.camera_name = camera_name;
    }

    public String getWarn_picture() {
        return warn_picture;
    }

    public void setWarn_picture(String warn_picture) {
        this.warn_picture = warn_picture;
    }

    public String getWarn_video() {
        return warn_video;
    }

    public void setWarn_video(String warn_video) {
        this.warn_video = warn_video;
    }

    public String getIs_processed() {
        return is_processed;
    }

    public void setIs_processed(String is_processed) {
        this.is_processed = is_processed;
    }

    public String getProcessed_person_id() {
        return processed_person_id;
    }

    public void setProcessed_person_id(String processed_person_id) {
        this.processed_person_id = processed_person_id;
    }

    public String getProcessed_time() {
        return processed_time;
    }

    public void setProcessed_time(String processed_time) {
        this.processed_time = processed_time;
    }

    public String getProcessed_content() {
        return processed_content;
    }

    public void setProcessed_content(String processed_content) {
        this.processed_content = processed_content;
    }

    public String getProcessed_image() {
        return processed_image;
    }

    public void setProcessed_image(String processed_image) {
        this.processed_image = processed_image;
    }

    public String getProcessed_video() {
        return processed_video;
    }

    public void setProcessed_video(String processed_video) {
        this.processed_video = processed_video;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
