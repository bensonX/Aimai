package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 评论 entity
 * Created by Eric Xie on 2017/7/17 0017.
 */

public class Comment implements Serializable {


    /** 评论ID */
    private int id;

    /** 用户ID */
    private int userId;

    /** 用户名 */
    private String userName;

    /** 用户头像 */
    private String avatar;

    /** 评论内容 */
    private String content;

    /** 是否有效  0:无效  1:有效  缺省值 1 */
    private int isValid;

    /** 创建时间 */
    private long createTime;

    /** 课程Id */
    private int curriculumId;


    /** 获取 评论ID */
    public int getId() {
        return this.id;
    }

    /** 设置 评论ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 用户ID */
    public int getUserId() {
        return this.userId;
    }

    /** 设置 用户ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 获取 用户名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 用户名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 用户头像 */
    public String getAvatar() {
        return this.avatar;
    }

    /** 设置 用户头像 */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /** 获取 评论内容 */
    public String getContent() {
        return this.content;
    }

    /** 设置 评论内容 */
    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 是否有效  0:无效  1:有效  缺省值 1 */
    public int getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效  0:无效  1:有效  缺省值 1 */
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    /** 获取 创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /** 获取 课程Id */
    public int getCurriculumId() {
        return this.curriculumId;
    }

    /** 设置 课程Id */
    public void setCurriculumId(int curriculumId) {
        this.curriculumId = curriculumId;
    }
}
