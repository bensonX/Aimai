package com.ins.aimai.net;

import com.ins.aimai.bean.eyekey.FaceAttrs;
import com.ins.aimai.bean.eyekey.MatchCompare;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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
    @POST
    Call<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part file);

    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////


    //##################################################################
    //#########               资讯
    //##################################################################

    /**
     * 资讯列表
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/news/queryNewsList")
    Call<ResponseBody> queryInfo(@FieldMap Map<String, Object> param);

    /**
     * 获取banner数据
     */
    @FormUrlEncoded
    @POST("/api/banner/queryBannerList")
    Call<ResponseBody> queryBanner(@FieldMap Map<String, Object> param);


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
     * 注销
     */
    @FormUrlEncoded
    @POST("/api/user/logout")
    Call<ResponseBody> loginout(@FieldMap Map<String, Object> param);

    /**
     * 自动登录
     */
    @FormUrlEncoded
    @POST("/api/user/getInfo")
    Call<ResponseBody> getInfo(@FieldMap Map<String, Object> param);

    /**
     * 更新用户信息
     * java.lang.String showName, java.lang.String avatar, java.lang.Integer cityId, java.lang.Integer definition
     */
    @FormUrlEncoded
    @POST("/api/user/updateUser")
    Call<ResponseBody> updateUser(@FieldMap Map<String, Object> param);

    /**
     * 更新用户信息
     * java.lang.String showName, java.lang.String avatar, java.lang.Integer cityId, java.lang.Integer definition
     */
    @FormUrlEncoded
    @POST("/api/user/updateUser")
    Call<ResponseBody> updateUserWithToken(@Header("token") String token, @FieldMap Map<String, Object> param);

    /**
     * 更新用户信息
     * 修改或忘记密码
     * 找回密码 | 修改密码,旧密码 和 手机号 互斥存在 手机号存在 则 不知道旧密码|忘记密码 修改密码
     * newPwd
     * oldPwd
     * phone
     */
    @FormUrlEncoded
    @POST("/api//user/setPwd")
    Call<ResponseBody> setPwd(@FieldMap Map<String, Object> param);

    /**
     * 获取验证码
     * String phone
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessage")
    Call<ResponseBody> sendMessage(@FieldMap Map<String, Object> param);

    /**
     * 获取验证码并验证手机号是否重复（注册时使用）
     * String phone
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessageRegister")
    Call<ResponseBody> sendMessageRegist(@FieldMap Map<String, Object> param);

    /**
     * 获取验证码并检查该账号是否存在（忘记密码时使用）
     * String phone
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessageForgetPwd")
    Call<ResponseBody> sendMessageForget(@FieldMap Map<String, Object> param);

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
    //#########               收藏
    //##################################################################

    /**
     * 添加收藏
     * type 收藏类型 参数类型:int  0:资讯 1:课程 2：考题
     * targetId
     */
    @FormUrlEncoded
    @POST("/api/collect/addCollect")
    Call<ResponseBody> addCollect(@FieldMap Map<String, Object> param);

    /**
     * 通过类型 获取收藏
     * type 收藏类型 参数类型:int  0:资讯 1:课程 2：考题
     */
    @FormUrlEncoded
    @POST("/api/collect/queryCollect")
    Call<ResponseBody> queryCollect(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               课时
    //##################################################################

    /**
     * 判断某课程是否购买
     * curriculumId
     */
    @FormUrlEncoded
    @POST("/api/curriculum/isBuy")
    Call<ResponseBody> lessonIsBuy(@FieldMap Map<String, Object> param);

    /**
     * 课程首页搜索获取
     * searchParam
     * pageNO    pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumBySearch")
    Call<ResponseBody> searchLesson(@FieldMap Map<String, Object> param);

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
     * 通过课程ID 查询课程详情
     * curriculumId
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumById")
    Call<ResponseBody> queryLessonDetail(@FieldMap Map<String, Object> param);

    /**
     * 通过orderID 查询课程详情
     * orderId
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumByOrder")
    Call<ResponseBody> queryLessonDetailByOrder(@FieldMap Map<String, Object> param);

    /**
     * 统计用户 最新一次 考题记录得分情况
     */
    @FormUrlEncoded
    @POST("/api/curriculum/statisticsCurriculum")
    Call<ResponseBody> statisLearn(@FieldMap Map<String, Object> param);

    /**
     * 获取学习列表
     * 企业用户获取课程下 已观看 和 已考核 人列表
     * orderId
     * flag   标记 0:查看已观看的人数 1:查看已考核人数
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/user/queryUserStudyStatus")
    Call<ResponseBody> queryLearnUser(@FieldMap Map<String, Object> param);

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
    @POST("/api/wxPay/sign")
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

    //##################################################################
    //#########               评论
    //##################################################################

    /**
     * 获取评论
     * curriculumId
     * pageNO    pageSize
     */
    @FormUrlEncoded
    @POST("/api/evaluate/queryEvaluate")
    Call<ResponseBody> queryComments(@FieldMap Map<String, Object> param);

    /**
     * 发布评论
     * curriculumId
     * content
     */
    @FormUrlEncoded
    @POST("/api/evaluate/addEvaluate")
    Call<ResponseBody> addComment(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               订单
    //##################################################################

    /**
     * 获取订单列表
     * payStatus 支付状态,如果不传则查询全部 参数类型:int
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/order/queryOrder")
    Call<ResponseBody> queryOrder(@FieldMap Map<String, Object> param);

    /**
     * 创建订单
     * curriculumId
     * number
     */
    @FormUrlEncoded
    @POST("/api/order/addOrder")
    Call<ResponseBody> addOrder(@FieldMap Map<String, Object> param);


    /**
     * 删除订单
     * orderId
     */
    @FormUrlEncoded
    @POST("/api/order/delOrder")
    Call<ResponseBody> delOrder(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               消息
    //##################################################################

    /**
     * 获取消息列表
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/systemInfo/getSystemInfo")
    Call<ResponseBody> queryMsg(@FieldMap Map<String, Object> param);

    /**
     * 查看消息
     * systemInfoId
     */
    @FormUrlEncoded
    @POST("/api/systemInfo/checkSystemInfo")
    Call<ResponseBody> checkMsg(@FieldMap Map<String, Object> param);


    /**
     * 查看消息是否有未阅读的消息 返回 0 不显示小红点 1 显示小红点
     */
    @FormUrlEncoded
    @POST("/api/systemInfo/countCheckInfo")
    Call<ResponseBody> hasNewMsg(@FieldMap Map<String, Object> param);


    /**
     * 意见反馈
     * content
     */
    @FormUrlEncoded
    @POST("/api/suggest/submitSuggest")
    Call<ResponseBody> suggest(@FieldMap Map<String, Object> param);


    //##################################################################
    //#########               人脸识别
    //##################################################################

    /**
     * 解析人像
     * app_id
     * app_key
     * url、img或者File
     */
    @Multipart
    @POST
    Call<FaceAttrs> eyeCheck(@Url String url, @Part("app_id") RequestBody request_api_id,
                             @Part("app_key") RequestBody request_api_key,
                             @Part MultipartBody.Part request_img_part);

    /**
     * 计算两个Face的相似度，分值百分制
     * app_id
     * app_key
     * face_id1
     * face_id2
     */
    @GET
    Call<MatchCompare> eyeCompare(@Url String url, @QueryMap Map<String, Object> param);

    /**
     * 新增人脸验证记录
     * videoId
     * status 状态，固定传值 1
     * videoSecond
     * faceImage
     */
    @FormUrlEncoded
    @POST("/api/faceRecord/addRecord")
    Call<ResponseBody> addFaceRecord(@FieldMap Map<String, Object> param);

    /**
     * 通过视频ID获取人脸验证记录
     * videoId
     */
    @FormUrlEncoded
    @POST("/api/faceRecord/queryFaceRecord")
    Call<ResponseBody> queryFaceRecord(@FieldMap Map<String, Object> param);


    //##################################################################
    //#########               学习
    //##################################################################

    /**
     * 学习模块，课程列表
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumStudy")
    Call<ResponseBody> queryStudy(@FieldMap Map<String, Object> param);


    /**
     * 获取练习题列表
     * orderId
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/courseWare/statisticsCourseWare")
    Call<ResponseBody> queryPracticeExams(@FieldMap Map<String, Object> param);

    /**
     * 获取 错题库课程 列表接口
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumError")
    Call<ResponseBody> queryErrorLesson(@FieldMap Map<String, Object> param);


    /**
     * 获取错题库列表
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/courseWare/queryCourseWareError")
    Call<ResponseBody> queryErrorWare(@FieldMap Map<String, Object> param);


    /**
     * 获取 模拟题列表接口
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumStudyForExamination")
    Call<ResponseBody> queryModelPapers(@FieldMap Map<String, Object> param);


    /**
     * 获取 考试题列表接口
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumPass")
    Call<ResponseBody> queryOffiPapers(@FieldMap Map<String, Object> param);


    //##################################################################
    //#########               考试
    //##################################################################

    /**
     * 获取试卷列表
     * paperId
     */
    @FormUrlEncoded
    @POST("/api/paper/queryPaperById")
    Call<ResponseBody> queryQuestions(@FieldMap Map<String, Object> param);

    /**
     * 获取试卷列表
     * answers     [ { "examinationId":1, "answers":[1,2]},{ "examinationId":2,"answers":[1] }]
     * paperId
     * orderId
     * seconds
     */
    @FormUrlEncoded
    @POST("/api/paper/submitPaper")
    Call<ResponseBody> submitExam(@FieldMap Map<String, Object> param);


    /**
     * 本人获取考试成绩记录
     * paperId
     * orderId
     */
    @FormUrlEncoded
    @POST("/api/paper/queryPaperRecord")
    Call<ResponseBody> queryPaperRecord(@FieldMap Map<String, Object> param);


    /**
     * 通过题目的ID集合查询题解
     * ids   试题ID集合 参数事例：[1,2,3]
     */
    @FormUrlEncoded
    @POST("/api/examination/queryExaminationKey")
    Call<ResponseBody> queryQuestionAnalysis(@FieldMap Map<String, Object> param);


    /**
     * 获取错题集 只有入口为没有错题ID集合的情况下调用
     * orderId
     * courseWareId
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/examination/queryErrorExamination")
    Call<ResponseBody> queryQuestionAnalysisByPage(@FieldMap Map<String, Object> param);


    //##################################################################
    //#########               课程分配
    //##################################################################

    /**
     * 公司用户获取公司下的用户列表
     * 如果是课程分配时 获取用户列表 则 isAllocation 值为 1同时 orderId不能为空,其他情况，此两参数 可为null
     * searchParam  搜索参数
     * orderId
     * isAllocation 是否是课程分配时获取的用户列表 0:否 1：是
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/user/queryUserForAllocation")
    Call<ResponseBody> queryUserAlloc(@FieldMap Map<String, Object> param);

    /**
     * 公司用户列表，获取用户详情接口
     * userId
     */
    @FormUrlEncoded
    @POST("/api/user/queryUserDetail")
    Call<ResponseBody> queryUserDetail(@FieldMap Map<String, Object> param);

    /**
     * 获取游离状态下的用户
     * searchParam  搜索参数
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/user/queryUserNoCompany")
    Call<ResponseBody> queryUserSearch(@FieldMap Map<String, Object> param);

    /**
     * 添加同事接口
     * 只有公司用户才能对此操作，并且是游离状态的用户
     * userId
     * departmentName
     * jobTitle
     */
    @FormUrlEncoded
    @POST("/api/user/addColleague")
    Call<ResponseBody> addEmployee(@FieldMap Map<String, Object> param);

    /**
     * 分配课程
     * 只有公司用户才能对此操作，并且是游离状态的用户
     * userIds  用户ID集合 逗号隔开 类型:string
     * orderId
     * number   分配的数量 一期功能 默认传 1 类型:int
     */
    @FormUrlEncoded
    @POST("/api/allocation/addAllocation")
    Call<ResponseBody> addAllocation(@FieldMap Map<String, Object> param);

    /**
     * 公司或者代理商 通过用户 获取待分配的课程列表
     * userId
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryWaitAllocationCurriculum")
    Call<ResponseBody> queryLessonAllocatList(@FieldMap Map<String, Object> param);

    /**
     * 给用户批量分配课程
     * userId
     * orderIds     订单ID集合 逗号隔开 类型:string
     * number       分配的数量 一期功能 默认传 1 类型:int
     */
    @FormUrlEncoded
    @POST("/api/allocation/batchAddAllocation")
    Call<ResponseBody> lessonAllocat(@FieldMap Map<String, Object> param);

    //##################################################################
    //#########               政府
    //##################################################################

    /**
     * 给用户批量分配课程
     * searchParams
     * cityId
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/user/queryCompanyList")
    Call<ResponseBody> queryComps(@FieldMap Map<String, Object> param);

    /**
     * 获取公司详情
     * companyId
     */
    @FormUrlEncoded
    @POST("/api/user/queryCompanyDetail")
    Call<ResponseBody> queryCompDetail(@FieldMap Map<String, Object> param);

    /**
     * 政府学习中心 统计接口
     * cityId
     * levelType  城市级别 1:省级,2:市级,3:区县级 当cityId不为空时,该字段必选
     */
    @FormUrlEncoded
    @POST("/api/user/statistics")
    Call<ResponseBody> govStatis(@FieldMap Map<String, Object> param);

    /**
     * 通过公司ID 获取 公司下的课程列表
     * companyId
     * pageNO
     * pageSize
     */
    @FormUrlEncoded
    @POST("/api/curriculum/queryCurriculumByCompany")
    Call<ResponseBody> queryLessonsByCompId(@FieldMap Map<String, Object> param);
}
