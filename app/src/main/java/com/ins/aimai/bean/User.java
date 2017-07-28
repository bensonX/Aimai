package com.ins.aimai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户 entity
 * Created by Eric Xie on 2017/7/12 0012.
 */
public class User implements Serializable {

    public static final int NONE = 0;
    public static final int ADMIN_USER = 1;              // 平台用户
    public static final int GOVERNMENT_USER = 2;        // 政府用户 （政府）
    public static final int COMPANY_USER = 3;           // 企业用户 （企业）
    public static final int USER = 4;                    // 普通用户 (个人)
    public static final int BUSINESS_USER = 5;          // 经销商用户


    /**
     * 主键ID
     */
    private int id;

    /**
     * 角色ID
     */
    private int roleId;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 展示名称 个人：姓名 政府：机构名称 企业：企业名称
     */
    private String showName;

    /**
     * 个人：身份证 政府：机构代码 企业：营业执照编码
     */
    private String pid;

    /**
     * 所在地
     */
    private int cityId;

    /**
     * 城市对象
     */
    @SerializedName("city")
    private Address city;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人用户  人脸识别采集图像地址
     */
    private String veriFaceImages;

    /**
     * 行业分类ID  企业用户
     */
    private int tradeId;

    /**
     * 企业：营业执照 政府：介绍信
     */
    private String licenseFile;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 用户 - 积分
     */
    private int accumulate;

    /**
     * 状态 企业、政府用户注册 后台审核状态
     * 0：未通过 1：审核通过  2：审核中
     */
    private int status;

    /**
     * 父级ID-用户字段 所属的企业用户ID
     */
    private int parentId;

    /**
     * 账户是否有效 0：无效 1：有效  缺省值：1
     */
    private int isValid;

    /**
     * 请求令牌
     */
    private String token;

    /**
     * 设备类型 0：android 1:ios
     */
    private int deviceType;

    /**
     * 设备注册ID
     */
    private String deviceToken;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 最后登录时间
     */
    private long lastLoginTime;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 清晰度 0：流畅  1：高清 缺省值 0
     */
    private int definition;

    /**
     * 微信登录时候 的openId
     */
    private String openId;


    //新增字段

    //部门
    private String departmentName;
    //职位
    private String jobTitle;
    //公司
    private String companyName;
    //行业名称
    private String tradeName;
    //人脸id
    private String faceId;

    //####################### 逻辑方法 ##########################

    public boolean isUser() {
        return roleId == USER;
    }

    public boolean isCompUser() {
        return roleId == COMPANY_USER;
    }

    public boolean isGovUser() {
        return roleId == GOVERNMENT_USER;
    }


    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    /**
     * 获取 主键ID
     */
    public int getId() {
        return this.id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 设置 主键ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取 角色ID
     */
    public int getRoleId() {
        return this.roleId;
    }

    /**
     * 设置 角色ID
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取 电话号码
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 密码
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     * 设置 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 获取 昵称
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * 设置 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取 展示名称 个人：姓名 政府：机构名称 企业：企业名称
     */
    public String getShowName() {
        return this.showName;
    }

    /**
     * 设置 展示名称 个人：姓名 政府：机构名称 企业：企业名称
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * 获取 个人：身份证 政府：机构代码 企业：营业执照编码
     */
    public String getPid() {
        return this.pid;
    }

    /**
     * 设置 个人：身份证 政府：机构代码 企业：营业执照编码
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 获取 所在地
     */
    public int getCityId() {
        return this.cityId;
    }

    /**
     * 设置 所在地
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * 获取 城市对象
     */
    public Address getCity() {
        return this.city;
    }

    /**
     * 设置 城市对象
     */
    public void setCity(Address city) {
        this.city = city;
    }

    /**
     * 获取 头像
     */
    public String getAvatar() {
        return this.avatar;
    }

    /**
     * 设置 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取 个人用户  人脸识别采集图像地址
     */
    public String getVeriFaceImages() {
        return this.veriFaceImages;
    }

    /**
     * 设置 个人用户  人脸识别采集图像地址
     */
    public void setVeriFaceImages(String veriFaceImages) {
        this.veriFaceImages = veriFaceImages;
    }

    /**
     * 获取 行业分类ID  企业用户
     */
    public int getTradeId() {
        return this.tradeId;
    }

    /**
     * 设置 行业分类ID  企业用户
     */
    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * 获取 企业：营业执照 政府：介绍信
     */
    public String getLicenseFile() {
        return this.licenseFile;
    }

    /**
     * 设置 企业：营业执照 政府：介绍信
     */
    public void setLicenseFile(String licenseFile) {
        this.licenseFile = licenseFile;
    }

    /**
     * 获取 介绍
     */
    public String getIntroduce() {
        return this.introduce;
    }

    /**
     * 设置 介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取 用户 - 积分
     */
    public int getAccumulate() {
        return this.accumulate;
    }

    /**
     * 设置 用户 - 积分
     */
    public void setAccumulate(int accumulate) {
        this.accumulate = accumulate;
    }

    /**
     * 状态 企业、政府用户注册 后台审核状态
     * 0：未通过 1：审核通过  2：审核中
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * 状态 企业、政府用户注册 后台审核状态
     * 0：未通过 1：审核通过  2：审核中
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取 父级ID-用户字段 所属的企业用户ID
     */
    public int getParentId() {
        return this.parentId;
    }

    /**
     * 设置 父级ID-用户字段 所属的企业用户ID
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取 账户是否有效 0：无效 1：有效  缺省值：1
     */
    public int getIsValid() {
        return this.isValid;
    }

    /**
     * 设置 账户是否有效 0：无效 1：有效  缺省值：1
     */
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    /**
     * 获取 请求令牌
     */
    public String getToken() {
        return this.token;
    }

    /**
     * 设置 请求令牌
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取 设备类型 0：android 1:ios
     */
    public int getDeviceType() {
        return this.deviceType;
    }

    /**
     * 设置 设备类型 0：android 1:ios
     */
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取 设备注册ID
     */
    public String getDeviceToken() {
        return this.deviceToken;
    }

    /**
     * 设置 设备注册ID
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * 获取 创建时间
     */
    public long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 最后登录时间
     */
    public long getLastLoginTime() {
        return this.lastLoginTime;
    }

    /**
     * 设置 最后登录时间
     */
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取 更新时间
     */
    public long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间
     */
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 清晰度 0：流畅  1：高清 缺省值 0
     */
    public int getDefinition() {
        return this.definition;
    }

    /**
     * 设置 清晰度 0：流畅  1：高清 缺省值 0
     */
    public void setDefinition(int definition) {
        this.definition = definition;
    }

    /**
     * 获取 微信登录时候 的openId
     */
    public String getOpenId() {
        return this.openId;
    }

    /**
     * 设置 微信登录时候 的openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
