package com.ins.aimai.net.helper;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetFavoHelper {
    private static NetFavoHelper instance = null;

    private NetFavoHelper() {
    }

    public static synchronized NetFavoHelper getInstance() {
        if (instance == null) {
            instance = new NetFavoHelper();
        }
        return instance;
    }


    public void netAddCollect(int id, int type) {
        Map<String, Object> param = new NetParam()
                .put("type", type)
                .put("targetId", id)
                .build();
        NetApi.NI().addCollect(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
