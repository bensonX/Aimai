package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 订单 entity
 * Created by Eric Xie on 2017/7/14 0014.
 */
public class Order implements Serializable {

    /** 订单ID */
    private int id;

    /** 订单号 */
    private String orderNumber;

    /** 用户ID */
    private int userId;

    /** 用户 */
    private User user;

    /** 课程ID */
    private int curriculumId;

    /** 购买的数量 */
    private int number;

    /** 支付状态 0：未支付  1：已支付 */
    private int payStatus;

    /** 支付方式 0:支付宝 1:微信  2:公众号支付 3:线下支付 */
    private int payMethod;

    /** 第三方 支付 的订单号 */
    private String outTradeNO;

    /** 订单总价 */
    private double price;

    /** 订单创建时间 */
    private long createTime;

    /** 如果是线下支付 则 录入订单人 */
    private int createUserId;

    /** 如果是线下支付 则 录入订单人 */
    private String createUserName;

    /** 订单对用户 是否有效 0：无效 1：有效 缺省值 1
     * 用户删除订单字段 */
    private int userIsValid;

    /** 课程名称 */
    private String curriculumName;

    /** 课程封面 */
    private String cover;


    /** 获取 订单ID */
    public int getId() {
        return this.id;
    }

    /** 设置 订单ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 订单号 */
    public String getOrderNumber() {
        return this.orderNumber;
    }

    /** 设置 订单号 */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /** 获取 用户ID */
    public int getUserId() {
        return this.userId;
    }

    /** 设置 用户ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 获取 用户 */
    public User getUser() {
        return this.user;
    }

    /** 设置 用户 */
    public void setUser(User user) {
        this.user = user;
    }

    /** 获取 课程ID */
    public int getCurriculumId() {
        return this.curriculumId;
    }

    /** 设置 课程ID */
    public void setCurriculumId(int curriculumId) {
        this.curriculumId = curriculumId;
    }

    /** 获取 购买的数量 */
    public int getNumber() {
        return this.number;
    }

    /** 设置 购买的数量 */
    public void setNumber(int number) {
        this.number = number;
    }

    /** 获取 支付状态 0：未支付  1：已支付 */
    public int getPayStatus() {
        return this.payStatus;
    }

    /** 设置 支付状态 0：未支付  1：已支付 */
    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    /** 获取 支付方式 0:支付宝 1:微信  2:公众号支付 3:线下支付 */
    public int getPayMethod() {
        return this.payMethod;
    }

    /** 设置 支付方式 0:支付宝 1:微信  2:公众号支付 3:线下支付 */
    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    /** 获取 第三方 支付 的订单号 */
    public String getOutTradeNO() {
        return this.outTradeNO;
    }

    /** 设置 第三方 支付 的订单号 */
    public void setOutTradeNO(String outTradeNO) {
        this.outTradeNO = outTradeNO;
    }

    /** 获取 订单总价 */
    public double getPrice() {
        return this.price;
    }

    /** 设置 订单总价 */
    public void setPrice(double price) {
        this.price = price;
    }

    /** 获取 订单创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 订单创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /** 获取 如果是线下支付 则 录入订单人 */
    public int getCreateUserId() {
        return this.createUserId;
    }

    /** 设置 如果是线下支付 则 录入订单人 */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /** 获取 如果是线下支付 则 录入订单人 */
    public String getCreateUserName() {
        return this.createUserName;
    }

    /** 设置 如果是线下支付 则 录入订单人 */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /** 订单对用户 是否有效 0：无效 1：有效 缺省值 1
     * 用户删除订单字段 */
    public int getUserIsValid() {
        return this.userIsValid;
    }

    /** 订单对用户 是否有效 0：无效 1：有效 缺省值 1
     * 用户删除订单字段 */
    public void setUserIsValid(int userIsValid) {
        this.userIsValid = userIsValid;
    }

    /** 获取 课程名称 */
    public String getCurriculumName() {
        return this.curriculumName;
    }

    /** 设置 课程名称 */
    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    /** 获取 课程封面 */
    public String getCover() {
        return this.cover;
    }

    /** 设置 课程封面 */
    public void setCover(String cover) {
        this.cover = cover;
    }
}
