package com.ins.aimai.net;

import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

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
}
