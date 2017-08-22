package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * 资讯
 * Created by Eric Xie on 2017/7/13 0013.
 */
public class Info implements Serializable {

    /** 主键ID */
    private int id;

    /** 标题 */
    private String title;

    /** 图片地址 */
    private String image;

    /** 摘要 */
    private String digest;

    /** 富文本内容 */
    private String content;

    /** 来源 */
    private String source;

    /** 责任编辑人 */
    private String editor;

    /** 创建者 */
    private int createUserId;

    /** 创建者名字 */
    private String createUserName;

    /** 创建时间 */
    private long createTime;

    /** 被收藏数量 */
    private int collectNum;

    //新增字段
    //是否是外链消息
    private int isLink;
    //外链地址
    private String linkUrl;

    ////////////////////  业务方法 ////////////////

    public boolean isLink() {
        return isLink == 1;
    }

    ///////////////////////////////////////////////

    public int getIsLink() {
        return isLink;
    }

    public void setIsLink(int isLink) {
        this.isLink = isLink;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /** 获取 主键ID */
    public int getId() {
        return this.id;
    }

    /** 设置 主键ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 获取 标题 */
    public String getTitle() {
        return this.title;
    }

    /** 设置 标题 */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 获取 图片地址 */
    public String getImage() {
        return this.image;
    }

    /** 设置 图片地址 */
    public void setImage(String image) {
        this.image = image;
    }

    /** 获取 摘要 */
    public String getDigest() {
        return this.digest;
    }

    /** 设置 摘要 */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /** 获取 富文本内容 */
    public String getContent() {
        return this.content;
    }

    /** 设置 富文本内容 */
    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 来源 */
    public String getSource() {
        return this.source;
    }

    /** 设置 来源 */
    public void setSource(String source) {
        this.source = source;
    }

    /** 获取 责任编辑人 */
    public String getEditor() {
        return this.editor;
    }

    /** 设置 责任编辑人 */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /** 获取 创建者 */
    public int getCreateUserId() {
        return this.createUserId;
    }

    /** 设置 创建者 */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /** 获取 创建者名字 */
    public String getCreateUserName() {
        return this.createUserName;
    }

    /** 设置 创建者名字 */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /** 获取 创建时间 */
    public long getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /** 获取 被收藏数量 */
    public int getCollectNum() {
        return this.collectNum;
    }

    /** 设置 被收藏数量 */
    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }
}
