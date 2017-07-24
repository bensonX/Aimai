package com.ins.aimai.bean.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/24.
 */

public class VideoDirectiry implements Serializable{
    private int id;
    private String course;
    private String videoName;
    private long time;

    public VideoDirectiry() {
    }

    public VideoDirectiry(String course, String videoName, long time) {
        this.course = course;
        this.videoName = videoName;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
