package com.ins.aimai.bean.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/27.
 */

public class FaceRecord implements Serializable {
    private int id;
    private long createTime;
    private String faceImage;
    private int orderId;
    private int status;
    private int userId;
    private int videoId;
    private int videoSecond;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getVideoSecond() {
        return videoSecond;
    }

    public void setVideoSecond(int videoSecond) {
        this.videoSecond = videoSecond;
    }
}
