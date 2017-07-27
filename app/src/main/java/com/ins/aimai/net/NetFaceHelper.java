package com.ins.aimai.net;

import android.widget.Toast;

import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.bean.eyekey.FaceAttrs;
import com.ins.aimai.bean.eyekey.MatchCompare;
import com.ins.aimai.common.AppData;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StrUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetFaceHelper {

    private static final String app_id = "746ca1d1a4cf44378b2aefda6e69f31b";
    private static final String app_key = "f9f483ec7aed4b1ca03328814b82127e";
    //相识度大于多少才算对比成功
    private static final float similarityValue = 0.7f;

    //请求参数
    //图片路径
    private String path;
    //视频id
    private int videoId;
    //视频播放时间
    private int videoSecond;

    private VideoActivity activity;

    private OnFaceCheckCallback callback;

    private static NetFaceHelper instance = null;

    private NetFaceHelper() {
    }

    public static synchronized NetFaceHelper getInstance() {
        if (instance == null) {
            instance = new NetFaceHelper();
        }
        return instance;
    }

    public NetFaceHelper init(VideoActivity activity, String path, int videoId, int videoSecond) {
        this.activity = activity;
        this.path = path;
        this.videoId = videoId;
        this.videoSecond = videoSecond;
        return this;
    }

    public void netEyeCheck(OnFaceCheckCallback callback) {
        this.callback = callback;
        RequestBody bodyAppId = NetParam.buildRequestBody(app_id);
        RequestBody bodyAppKey = NetParam.buildRequestBody(app_key);
        MultipartBody.Part partFile = NetParam.buildFileBodyPart("File", path);
        NetApi.NI().eyeCheck(AppData.Url.eyeCheck, bodyAppId, bodyAppKey, partFile).enqueue(new Callback<FaceAttrs>() {
            @Override
            public void onResponse(Call<FaceAttrs> call, Response<FaceAttrs> response) {
                FaceAttrs faceAttrs = response.body();
                if (faceAttrs != null && "0000".equals(faceAttrs.getRes_code())) {
                    if (!StrUtil.isEmpty(faceAttrs.getFace())) {
                        netEyeCompare(faceAttrs.getFace().get(0).getFace_id());
                    }
                } else {
                    ToastUtil.showToastShort("人脸检测失败");
                }
            }

            @Override
            public void onFailure(Call<FaceAttrs> call, Throwable t) {
                ToastUtil.showToastShort("网络出错");
            }
        });
    }

    //上传视频进度，并进行本地存储
    private void netEyeCompare(final String faceId2) {
        User user = AppData.App.getUser();
        if (user == null) return;
        if (StrUtil.isEmpty(user.getFaceId())) {
            ToastUtil.showToastShort("错误：您还没有采集头像");
            return;
        }
        Map<String, Object> param = new NetParam()
                .put("app_id", app_id)
                .put("app_key", app_key)
                .put("face_id1", user.getFaceId())
                .put("face_id2", faceId2)
                .build();
        NetApi.NI().eyeCompare(AppData.Url.eyeCompare, param).enqueue(new Callback<MatchCompare>() {
            @Override
            public void onResponse(Call<MatchCompare> call, Response<MatchCompare> response) {
                MatchCompare compare = response.body();
                if (compare != null && "0000".equals(compare.getRes_code())) {
                    if (compare.getSimilarity() >= similarityValue) {
                        //对比成功，是本人
                        activity.getFaceRecords().add(new FaceRecord());
                        if (callback != null) callback.onFaceCheckSuccess();
                        NetUploadHelper.newInstance().netUpload(path, new NetUploadHelper.UploadCallback() {
                            @Override
                            public void uploadfinish(String url) {
                                netAddFaceRecord(url);
                            }
                        });
                    } else {
                        //对比失败，不是本人
                        if (callback != null) callback.onFaceCheckFailed();
                    }
                } else {
                    ToastUtil.showToastShort("人脸检测失败");
                }
            }

            @Override
            public void onFailure(Call<MatchCompare> call, Throwable t) {
                ToastUtil.showToastShort("网络出错");
            }
        });
    }

    private void netAddFaceRecord(String faceImage) {
        Map<String, Object> param = new NetParam()
                .put("videoId", videoId)
                .put("status", 1)
                .put("videoSecond", videoSecond)
                .put("faceImage", faceImage)
                .build();
        NetApi.NI().addFaceRecord(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                NetHelper.getInstance().netQueryFaceRecord(activity, videoId, null);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public interface OnFaceCheckCallback {
        void onFaceCheckSuccess();

        void onFaceCheckFailed();
    }
}
