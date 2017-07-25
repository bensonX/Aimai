package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 视频
 * Created by Eric Xie on 2017/7/13 0013.
 */
public class Video implements Serializable {

    /** 视频ID */
    private int id;

    /** 视频名 */
    private String name;

    /** 高清视频地址 */
    private String highDefinition;

    /** 高清视频时间 单位 秒 */
    private int highDefinitionSeconds;

    /** 流畅视频地址 */
    private String lowDefinition;

    /** 流畅视频时间 单位 秒 */
    private int lowDefinitionSeconds;

    /** 课时ID */
    private int courseWareId;

    /** 当前用户对此视频的最新播放进度 */
    private VideoStatus videoStatus;

    //本地字段
    /** 课件名称 */
    private String courseWareName;
    private String ppt;
    private String cover;


    /** 获取 视频ID */
    public int getId() {
        return this.id;
    }

    /** 设置 视频ID */
    public void setId(int id) {
        this.id = id;
    }

    public String getCourseWareName() {
        return courseWareName;
    }

    public void setCourseWareName(String courseWareName) {
        this.courseWareName = courseWareName;
    }

    public String getPpt() {
        return ppt;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt;
    }

    public VideoStatus getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(VideoStatus videoStatus) {
        this.videoStatus = videoStatus;
    }

    /** 获取 高清视频地址 */
    public String getHighDefinition() {
        return this.highDefinition;
    }

    /** 设置 高清视频地址 */
    public void setHighDefinition(String highDefinition) {
        this.highDefinition = highDefinition;
    }

    /** 获取 高清视频时间 单位 秒 */
    public int getHighDefinitionSeconds() {
        return this.highDefinitionSeconds;
    }

    /** 设置 高清视频时间 单位 秒 */
    public void setHighDefinitionSeconds(int highDefinitionSeconds) {
        this.highDefinitionSeconds = highDefinitionSeconds;
    }

    /** 获取 流畅视频地址 */
    public String getLowDefinition() {
        return this.lowDefinition;
    }

    /** 设置 流畅视频地址 */
    public void setLowDefinition(String lowDefinition) {
        this.lowDefinition = lowDefinition;
    }

    /** 获取 流畅视频时间 单位 秒 */
    public int getLowDefinitionSeconds() {
        return this.lowDefinitionSeconds;
    }

    /** 设置 流畅视频时间 单位 秒 */
    public void setLowDefinitionSeconds(int lowDefinitionSeconds) {
        this.lowDefinitionSeconds = lowDefinitionSeconds;
    }

    /** 获取 课时ID */
    public int getCourseWareId() {
        return this.courseWareId;
    }

    /** 设置 课时ID */
    public void setCourseWareId(int courseWareId) {
        this.courseWareId = courseWareId;
    }

    /** 获取 视频名 */
    public String getName() {
        return this.name;
    }

    /** 设置 视频名 */
    public void setName(String name) {
        this.name = name;
    }
}
