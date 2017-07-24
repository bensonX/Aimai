package com.ins.aimai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 课时、课件  entity
 * Created by Eric Xie on 2017/7/13 0013.
 */
public class CourseWare implements Serializable {

    /** 课件ID */
    private int id;

    /** 课件名称 */
    private String courseWareName;

    /** 是否有效 0：无效 1：有效 缺省值 1 */
    private int isValid;

    /** 创建者ID */
    private int createUserId;

    /** 创建者 */
    private String createUserName;

    /** 讲义地址 以逗号隔开 */
    private String ppt;

    /** 创建时间 */
    private long createTime;

    /** 课程ID */
    private int curriculumId;

    /** 课时下的 视频 */
    private List<Video> videos;

    /** 讲师名称 */
    private String teacherName;

    /** 讲师名称介绍 */
    private String teacherIntroduce;

    /** 封面图 */
    private String cover;

    /** 课程名 */
    private String curriculumName;

    /** 行业名 */
    private String tradeName;

    /** 获取 课件ID */
    public int getId() {
        return this.id;
    }

    /** 设置 课件ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 课件名称 */
    public String getCourseWareName() {
        return this.courseWareName;
    }

    /** 设置 课件名称 */
    public void setCourseWareName(String courseWareName) {
        this.courseWareName = courseWareName;
    }

    /** 获取 是否有效 0：无效 1：有效 缺省值 1 */
    public int getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0：无效 1：有效 缺省值 1 */
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    /** 获取 创建者ID */
    public int getCreateUserId() {
        return this.createUserId;
    }

    /** 设置 创建者ID */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /** 获取 创建者 */
    public String getCreateUserName() {
        return this.createUserName;
    }

    /** 设置 创建者 */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /** 获取 讲义地址 以逗号隔开 */
    public String getPpt() {
        return this.ppt;
    }

    /** 设置 讲义地址 以逗号隔开 */
    public void setPpt(String ppt) {
        this.ppt = ppt;
    }

    /** 获取 创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /** 获取 课程ID */
    public int getCurriculumId() {
        return this.curriculumId;
    }

    /** 设置 课程ID */
    public void setCurriculumId(int curriculumId) {
        this.curriculumId = curriculumId;
    }

    /** 获取 课时下的 视频 */
    public List<Video> getVideos() {
        return this.videos;
    }

    /** 设置 课时下的 视频 */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }


    /** 获取 讲师名称 */
    public String getTeacherName() {
        return this.teacherName;
    }

    /** 设置 讲师名称 */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /** 获取 讲师名称介绍 */
    public String getTeacherIntroduce() {
        return this.teacherIntroduce;
    }

    /** 设置 讲师名称介绍 */
    public void setTeacherIntroduce(String teacherIntroduce) {
        this.teacherIntroduce = teacherIntroduce;
    }

    /** 获取 封面图 */
    public String getCover() {
        return this.cover;
    }

    /** 设置 封面图 */
    public void setCover(String cover) {
        this.cover = cover;
    }


    /** 获取 课程名 */
    public String getCurriculumName() {
        return this.curriculumName;
    }

    /** 设置 课程名 */
    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    /** 获取 行业名 */
    public String getTradeName() {
        return this.tradeName;
    }

    /** 设置 行业名 */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
