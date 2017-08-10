package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 学习模块 课程列表 entity
 * Created by Eric Xie on 2017/7/25 0025.
 */
public class Study implements Serializable {

    private static final long serialVersionUID = -7543006557054465977L;

    /**
     * 课程ID
     */
    private int id;

    /**
     * 订单ID
     */
    private int orderId;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 课程名称
     */
    private String curriculumName;

    /**
     * 课程习题数量
     */
    private int examinationNum;

    /**
     * 课程视频数量
     */
    private int videoNum;

    /**
     * 课程PPT数量
     */
    private int pptNum;

    /**
     * 课程下视频的总时间 单位:秒
     */
    private int videoSeconds;

    /**
     * 该课程下 已经学习视频的时间  单位：秒
     */
    private int finishSeconds;

    /**
     * 购买的总份数
     */
    private int number;

    /**
     * 已分配的数量
     */
    private int allocationNum;

    /**
     * 视频购买时的单价
     */
    private double price;

    //新增字段
    /**
     * 课程错题数量
     */
    private int errorNum;

    //新增字段
    //是否通过
    private int isPass;

    ///////////////////////  业务方法 /////////////////////

    public boolean isPass() {
        return isPass == 1;
    }

    ///////////////////////////////////////////////////////

    /**
     * 获取 课程ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * 设置 课程ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取 课程封面
     */
    public String getCover() {
        return this.cover;
    }

    /**
     * 设置 课程封面
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 获取 课程名称
     */
    public String getCurriculumName() {
        return this.curriculumName;
    }

    /**
     * 设置 课程名称
     */
    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    /**
     * 获取 课程习题数量
     */
    public int getExaminationNum() {
        return this.examinationNum;
    }

    /**
     * 设置 课程习题数量
     */
    public void setExaminationNum(int examinationNum) {
        this.examinationNum = examinationNum;
    }

    /**
     * 获取 课程视频数量
     */
    public int getVideoNum() {
        return this.videoNum;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    /**
     * 设置 课程视频数量
     */
    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    /**
     * 获取 课程PPT数量
     */
    public int getPptNum() {
        return this.pptNum;
    }

    /**
     * 设置 课程PPT数量
     */
    public void setPptNum(int pptNum) {
        this.pptNum = pptNum;
    }

    /**
     * 获取 课程下视频的总时间 单位:秒
     */
    public int getVideoSeconds() {
        return this.videoSeconds;
    }

    /**
     * 设置 课程下视频的总时间 单位:秒
     */
    public void setVideoSeconds(int videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    /**
     * 获取 该课程下 已经学习视频的时间  单位：秒
     */
    public int getFinishSeconds() {
        return this.finishSeconds;
    }

    /**
     * 设置 该课程下 已经学习视频的时间  单位：秒
     */
    public void setFinishSeconds(int finishSeconds) {
        this.finishSeconds = finishSeconds;
    }

    /**
     * 获取 购买的总份数
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * 设置 购买的总份数
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 获取 已分配的数量
     */
    public int getAllocationNum() {
        return this.allocationNum;
    }

    /**
     * 设置 已分配的数量
     */
    public void setAllocationNum(int allocationNum) {
        this.allocationNum = allocationNum;
    }

    /**
     * 获取 视频购买时的单价
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * 设置 视频购买时的单价
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 获取 订单ID
     */
    public int getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
