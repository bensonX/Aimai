package com.ins.aimai.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
    public void netAddVideoStatus(final int orderId, final int videoId, final int seconds, final boolean isFinish) {
        //只有播放进度比本地数据大时才上传
        if (seconds > AppData.App.getVideoTime(videoId)) {
            //本地存储
            AppData.App.saveVideoTime(videoId, seconds);
            Map map = new HashMap<String, Object>() {{
                put("orderId", orderId);
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

    //获取视频播放验证记录
    public void netQueryFaceRecord(final VideoActivity activity, final int orderId, final int videoId, final OnFaceRecordCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .put("videoId", videoId)
                .build();
        NetApi.NI().queryFaceRecord(param).enqueue(new BaseCallback<List<FaceRecord>>(new TypeToken<List<FaceRecord>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<FaceRecord> faceRecords, String msg) {
                activity.setFaceRecords(faceRecords);
                if (callback != null) callback.onSuccess(faceRecords);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public void netQueryLessonDetail(int type, int lessonId, int orderId, final OnLessonCallback callback) {
        if (type == 0) {
            Map<String, Object> param = new NetParam()
                    .put("curriculumId", lessonId)
                    .build();
            if (callback != null) callback.onStart();
            NetApi.NI().queryLessonDetail(param).enqueue(new BaseCallback<Lesson>(Lesson.class) {
                @Override
                public void onSuccess(int status, Lesson lesson, String msg) {
                    if (callback != null) callback.onSuccess(status, lesson, msg);
                }

                @Override
                public void onError(int status, String msg) {
                    if (callback != null) callback.onError(status, msg);
                }
            });
        } else if (type == 1) {
            Map<String, Object> param = new NetParam()
                    .put("orderId", orderId)
                    .build();
            if (callback != null) callback.onStart();
            NetApi.NI().queryLessonDetailByOrder(param).enqueue(new BaseCallback<Lesson>(Lesson.class) {
                @Override
                public void onSuccess(int status, Lesson lesson, String msg) {
                    if (callback != null) callback.onSuccess(status, lesson, msg);
                }

                @Override
                public void onError(int status, String msg) {
                    if (callback != null) callback.onError(status, msg);
                }
            });
        }
    }

    public interface OnFaceRecordCallback {
        void onSuccess(List<FaceRecord> faceRecords);
    }

    public interface OnLessonCallback {
        void onStart();

        void onSuccess(int status, Lesson lesson, String msg);

        void onError(int status, String msg);
    }
}
