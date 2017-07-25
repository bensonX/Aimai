package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 视频状态 记录 entity
 * Created by Eric Xie on 2017/7/17 0017.
 */
public class VideoStatus implements Serializable {

    /** 记录ID */
    private int id;

    /** 视频ID */
    private int videoId;

    /** 用户ID */
    private int userId;

    /**  播放状态 0:开始播放  1:其他异常状态暂停 2:播放完成 */
    private int status;

    /** 视频播放的秒 */
    private int seconds;

    /** 创建时间 */
    private long createTime;

    public VideoStatus() {
    }

    public VideoStatus(int status) {
        this.status = status;
    }

    /** 获取 记录ID */
    public int getId() {
        return this.id;
    }

    /** 设置 记录ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 视频ID */
    public int getVideoId() {
        return this.videoId;
    }

    /** 设置 视频ID */
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    /** 获取 用户ID */
    public int getUserId() {
        return this.userId;
    }

    /** 设置 用户ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 获取  播放状态 0:开始播放  1:其他异常状态暂停 2:播放完成 */
    public int getStatus() {
        return this.status;
    }

    /** 设置  播放状态 0:开始播放  1:其他异常状态暂停 2:播放完成 */
    public void setStatus(int status) {
        this.status = status;
    }

    /** 获取 视频播放的秒 */
    public int getSeconds() {
        return this.seconds;
    }

    /** 设置 视频播放的秒 */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /** 获取 创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
