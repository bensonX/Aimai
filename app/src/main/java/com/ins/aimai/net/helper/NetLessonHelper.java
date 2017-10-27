package com.ins.aimai.net.helper;

import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetLessonHelper {
    private static NetLessonHelper instance = null;

    private NetLessonHelper() {
    }

    public static synchronized NetLessonHelper getInstance() {
        if (instance == null) {
            instance = new NetLessonHelper();
        }
        return instance;
    }


    public void netLessonIsBuy(int lessonId, final IsBuyCallback callback) {
        Map<String, Object> param = new NetParam().put("curriculumId", lessonId).build();
        NetApi.NI().lessonIsBuy(param).enqueue(new BaseCallback<Integer>(Integer.class) {
            @Override
            public void onSuccess(int status, Integer isBuy, String msg) {
                if (callback != null) {
                    if (isBuy == 1) callback.onYes();
                    else callback.onNo();
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError();
            }
        });
    }

    public interface IsBuyCallback {
        void onYes();

        void onNo();

        void onError();
    }
}
