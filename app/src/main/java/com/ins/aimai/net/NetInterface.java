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
     * 测试网络连接，无意义
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessage")
    Call<ResponseBody> netTest(@FieldMap Map<String, Object> param);

    /**
     * 上传资源文件
     */
    @Multipart
    @POST("images/res/upload")
    Call<ResponseBody> upload(
            @Part("picture\"; filename=\"picture.jpg") RequestBody file);

    @Multipart
    @POST("fileService")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////


    /**
     * 登录
     * java.lang.String phone, java.lang.String password, java.lang.Integer deviceType, java.lang.Integer deviceToken, java.lang.Integer isWechat
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<ResponseBody> login(@FieldMap Map<String, Object> param);
}
