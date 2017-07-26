package com.ins.aimai.net;

import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetHelper {
    private static NetHelper instance = null;

    private NetHelper() {
    }

    public static synchronized NetHelper getInstance() {
        if (instance == null) {
            instance = new NetHelper();
        }
        return instance;
    }

    //上传视频进度，并进行本地存储
    public void netAddVideoStatus(final int videoId, final int seconds, final boolean isFinish) {
        //只有播放进度比本地数据大时才上传
        if (seconds > AppData.App.getVideoTime(videoId)) {
            //本地存储
            AppData.App.saveVideoTime(videoId, seconds);
            Map map = new HashMap<String, Object>() {{
                put("videoId", videoId);
                put("status", isFinish ? 2 : 1);
                put("seconds", seconds);
            }};
            NetApi.NI().addVideoStatus(NetParam.newInstance().put(map).build()).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
                @Override
                public void onSuccess(int status, CommonBean commonBean, String msg) {
                }

                @Override
                public void onError(int status, String msg) {
                    ToastUtil.showToastShort("更新播放记录失败：" + msg);
                }
            });
        }
    }


    public void netEyeCheck(String path) {

        String app_id = "746ca1d1a4cf44378b2aefda6e69f31b";
        String app_key = "f9f483ec7aed4b1ca03328814b82127e";

//        RequestBody papp_id = RequestBody.create(MediaType.parse("multipart/form-data"), app_id);
//        RequestBody papp_key = RequestBody.create(MediaType.parse("multipart/form-data"), app_key);
//
//        File file = new File(path);
//        RequestBody requestImgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        // 创建MultipartBody.Part，用于封装文件数据
//        MultipartBody.Part requestImgPart = MultipartBody.Part.createFormData("File", file.getName(), requestImgFile);

        RequestBody bodyAppId = NetParam.buildRequestBody(app_id);
        RequestBody bodyAppKey = NetParam.buildRequestBody(app_key);
        MultipartBody.Part partFile = NetParam.buildFileBodyPart("File", path);


        NetApi.NI().eyeCheck(AppData.Url.eyeCheck, bodyAppId, bodyAppKey, partFile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ToastUtil.showToastShort("onResponse");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtil.showToastShort("onFailure");
            }
        });
    }
}
