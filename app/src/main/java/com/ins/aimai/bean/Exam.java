package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/8/2 0002.
 */
public class Exam implements Serializable {

    /** 课件ID */
    private int courseWareId;

    /** 课件名称 */
    private String courseWareName;

    /** 练习题数量 */
    private int examinationNum;

    /** 是否练习 0:否  1:是 */
    private int isExamination;


    /** 是否学习完 0:否  1:是 */
    private int isStudy;

    //新增字段
    //试卷id
    private int paperId;
    private int orderId;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /** 获取 课件ID */
    public int getCourseWareId() {
        return this.courseWareId;
    }

    /** 设置 课件ID */
    public void setCourseWareId(int courseWareId) {
        this.courseWareId = courseWareId;
    }

    /** 获取 课件名称 */
    public String getCourseWareName() {
        return this.courseWareName;
    }

    /** 设置 课件名称 */
    public void setCourseWareName(String courseWareName) {
        this.courseWareName = courseWareName;
    }

    /** 获取 练习题数量 */
    public int getExaminationNum() {
        return this.examinationNum;
    }

    /** 设置 练习题数量 */
    public void setExaminationNum(int examinationNum) {
        this.examinationNum = examinationNum;
    }

    /** 获取 是否练习 0:否  1:是 */
    public int getIsExamination() {
        return this.isExamination;
    }

    /** 设置 是否练习 0:否  1:是 */
    public void setIsExamination(int isExamination) {
        this.isExamination = isExamination;
    }

    /** 获取 是否学习完 0:否  1:是 */
    public int getIsStudy() {
        return this.isStudy;
    }

    /** 设置 是否学习完 0:否  1:是 */
    public void setIsStudy(int isStudy) {
        this.isStudy = isStudy;
    }
}
