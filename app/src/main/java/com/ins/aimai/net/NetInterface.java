package com.ins.aimai.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * API接口
 */
public interface NetInterface {

    /**
     * 测试网络连接，无实际意义
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessage")
    Call<ResponseBody> netTest(@FieldMap Map<String, Object> param);

    /**
     * 上传资源文件
     */
    @Multipart
    @POST("/images/res/upload")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////


    //##################################################################
    //#########               登录注册个人中心
    //##################################################################

    /**
     * 登录
     * java.lang.String phone, java.lang.String password, java.lang.Integer deviceType, java.lang.Integer deviceToken, java.lang.Integer isWechat
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<ResponseBody> login(@FieldMap Map<String, Object> param);

    /**
     * 自动登录
     */
    @FormUrlEncoded
    @POST("/api/user/getInfo")
    Call<ResponseBody> getInfo(@FieldMap Map<String, Object> param);

    /**
     * java.lang.String showName, java.lang.String avatar, java.lang.Integer cityId, java.lang.Integer definition
     */
    @FormUrlEncoded
    @POST("/api/user/updateUser")
    Call<ResponseBody> updateUser(@FieldMap Map<String, Object> param);

    /**
     * 获取验证码
     * String phone
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessage")
    Call<ResponseBody> sendMessage(@FieldMap Map<String, Object> param);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/api/user/register")
    Call<ResponseBody> register(@FieldMap Map<String, Object> param);

    /**
     * 获取行业列表
     */
    @FormUrlEncoded
    @POST("/api/trade/queryTrade")
    Call<ResponseBody> queryTrade(@FieldMap Map<String, Object> param);

    /**
     * 省市区查询
     * levelType
     * cityId
     */
    @FormUrlEncoded
    @POST("/api/city/queryCityByParentId")
    Call<ResponseBody> queryCity(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               课时
    //##################################################################

    /**
     * 课程首页列表获取
     * pageNO    pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumByItems")
    Call<ResponseBody> queryLessonHome(@FieldMap Map<String, Object> param);

    /**
     * 通过课程类别id查询课程
     * pageNO    pageSize
     * curriculumStageId
     * isRecommend 如果查询推荐课程列表，固定传参:1,其他情况不传
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumByType")
    Call<ResponseBody> queryLessonByCate(@FieldMap Map<String, Object> param);

    /**
     * 获取评论
     * curriculumId
     * pageNO    pageSize
     */
    @FormUrlEncoded
    @POST("/api/evaluate/queryEvaluate")
    Call<ResponseBody> queryComments(@FieldMap Map<String, Object> param);

    /**
     * 通过课程ID 查询课程详情
     * curriculumId
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumById")
    Call<ResponseBody> queryLessonDetail(@FieldMap Map<String, Object> param);

    /**
     * 创建订单
     * curriculumId
     * number
     */
    @FormUrlEncoded
    @POST("/api/order/addOrder")
    Call<ResponseBody> addOrder(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               支付
    //##################################################################

    /**
     * 获取支付宝签名
     * orderId
     */
    @FormUrlEncoded
    @POST("/api/aliPay/sign")
    Call<ResponseBody> signZhifubao(@FieldMap Map<String, Object> param);

    /**
     * 获取微信支付签名
     * orderId
     */
    @FormUrlEncoded
    @POST("/api/jsPay/sign")
    Call<ResponseBody> signWeixin(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               视频播放记录
    //##################################################################

    /**
     * 新增 播放记录
     * videoId
     * status 1:未完成 2:播放完成
     * seconds 秒
     */
    @FormUrlEncoded
    @POST("/api/videoStatus/addVideoStatus")
    Call<ResponseBody> addVideoStatus(@FieldMap Map<String, Object> param);



}
