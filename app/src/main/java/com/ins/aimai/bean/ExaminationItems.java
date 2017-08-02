package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/7/21 0021.
 */
public class ExaminationItems implements Serializable {

    /** 选项ID */
    private int id;

    /** 选项名称 */
    private String itemTitle;

    /** 该选项是否是正确的 0:不是   1:是 该正确答案 */
    private int isCorrect;

    /** 试题ID */
    private int examinationId;

    /** 试题名称 */
    private String examinationName;

    /** 排序序号 升序 */
    private int  sortNum;


    /** 获取 选项ID */
    public int getId() {
        return this.id;
    }

    /** 设置 选项ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 选项名称 */
    public String getItemTitle() {
        return this.itemTitle;
    }

    /** 设置 选项名称 */
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /** 获取 该选项是否是正确的 0:不是   1:是 该正确答案 */
    public int getIsCorrect() {
        return this.isCorrect;
    }

    /** 设置 该选项是否是正确的 0:不是   1:是 该正确答案 */
    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    /** 获取 试卷ID */
    public int getExaminationId() {
        return this.examinationId;
    }

    /** 设置 试卷ID */
    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    /** 获取 排序序号 升序 */
    public int getSortNum() {
        return this.sortNum;
    }

    /** 设置 排序序号 升序 */
    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    /** 获取 试题名称 */
    public String getExaminationName() {
        return this.examinationName;
    }

    /** 设置 试题名称 */
    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
