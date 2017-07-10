package com.ins.common.net;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * API接口
 */
public interface NetInterface {

    /**
     * app端首页banner轮播
     */
    @FormUrlEncoded
    @POST("api/banner/listForApp")
    Call<ResponseBody> getBanners(@FieldMap Map<String, String> options);
}
