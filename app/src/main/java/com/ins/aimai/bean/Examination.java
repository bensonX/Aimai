package com.ins.aimai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 考题库  entity
 * Created by Eric Xie on 2017/7/21 0021.
 */
public class Examination implements Serializable {

    /** 考题ID */
    private int id;

    /** 考试题目 */
    private String title;

    /** 试题类型 0:单选题 1:多选题  2:判断题 */
    private int type;

    /** 题解 */
    private String examinationKey;

    /** 考点 */
    private String emphasis;

    /** 该题的分数 */
    private int score;

    /** 创建时间 */
    private long createTime;

    /** 题目下的选项 以及答案 */
    private List<ExaminationItems> examinationItemsList;

    /** 课时名称 */
    private String courseWareName;


    // 以下业务字段 用于筛选 无其他业务逻辑
    /** 0：练习题  1：模拟题 2：考试题 只用于筛选 */
    private int category;

    /** 行业ID 只用于筛选 */
    private int tradeId;

    private String tradeName;

    /** 课程ID  用于筛选 */
    private int curriculumId;

    private String curriculumName;

    /** 城市ID 筛选用 */
    private int cityId;

    private String cityName;

    /** 公司ID 用于筛选用 */
    private int companyId;

    private String companyName;

    public String getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(String emphasis) {
        this.emphasis = emphasis;
    }

    /** 获取 考题ID */
    public int getId() {
        return this.id;
    }

    /** 设置 考题ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 考试题目 */
    public String getTitle() {
        return this.title;
    }

    /** 设置 考试题目 */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 获取 考试题类型 0:练习题 1:模拟题  2:考试题 */
    public int getType() {
        return this.type;
    }

    /** 设置 考试题类型 0:练习题 1:模拟题  2:考试题 */
    public void setType(int type) {
        this.type = type;
    }

    /** 获取 题解 */
    public String getExaminationKey() {
        return this.examinationKey;
    }

    /** 设置 题解 */
    public void setExaminationKey(String examinationKey) {
        this.examinationKey = examinationKey;
    }

    /** 获取 创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /** 获取 题目下的选项 以及答案 */
    public List<ExaminationItems> getExaminationItemsList() {
        return this.examinationItemsList;
    }

    /** 设置 题目下的选项 以及答案 */
    public void setExaminationItemsList(List<ExaminationItems> examinationItemsList) {
        this.examinationItemsList = examinationItemsList;
    }

    /** 获取 0：练习题  1：模拟题 2：考试题 只用于筛选 */
    public int getCategory() {
        return this.category;
    }

    /** 设置 0：练习题  1：模拟题 2：考试题 只用于筛选 */
    public void setCategory(int category) {
        this.category = category;
    }

    /** 获取 行业ID 只用于筛选 */
    public int getTradeId() {
        return this.tradeId;
    }

    /** 设置 行业ID 只用于筛选 */
    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    /** 获取 课程ID  用于筛选 */
    public int getCurriculumId() {
        return this.curriculumId;
    }

    /** 设置 课程ID  用于筛选 */
    public void setCurriculumId(int curriculumId) {
        this.curriculumId = curriculumId;
    }

    /** 获取 城市ID 筛选用 */
    public int getCityId() {
        return this.cityId;
    }

    /** 设置 城市ID 筛选用 */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /** 获取 公司ID 用于筛选用 */
    public int getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司ID 用于筛选用 */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getTradeName() {
        return this.tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCurriculumName() {
        return this.curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /** 获取 该题的分数 */
    public int getScore() {
        return this.score;
    }

    /** 设置 该题的分数 */
    public void setScore(int score) {
        this.score = score;
    }

    /** 获取 课时名称 */
    public String getCourseWareName() {
        return this.courseWareName;
    }

    /** 设置 课时名称 */
    public void setCourseWareName(String courseWareName) {
        this.courseWareName = courseWareName;
    }
}
