package com.ins.aimai.net.helper;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.bean.common.VideoFinishStatus;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.L;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void netAddVideoStatus(VideoStatus videoStatus, final int orderId, final int videoId, final int seconds, final boolean isFinish) {
        //只有播放进度比本地数据大时才上传
        L.e("second", seconds + ":" + videoStatus.getSeconds());
        if (seconds >= videoStatus.getSeconds()) {
            //更新记录
            videoStatus.setSeconds(seconds);
            Map map = new HashMap<String, Object>() {{
                put("orderId", orderId);
                put("videoId", videoId);
                put("status", isFinish ? 2 : 1);
                put("seconds", seconds);
            }};
            NetApi.NI().addVideoStatus(NetParam.newInstance().put(map).build()).enqueue(new BaseCallback<VideoFinishStatus>(VideoFinishStatus.class) {
                @Override
                public void onSuccess(int status, VideoFinishStatus videoFinishStatus, String msg) {
                    //如果是完成状态，把返回值post出去
                    if (isFinish) {
                        EventBean eventBean = new EventBean(EventBean.EVENT_VIDEO_FINISH_STATUS);
                        eventBean.put("videoFinishStatus", videoFinishStatus);
                        EventBus.getDefault().post(eventBean);
                    }
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

    //查询课程详情
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
