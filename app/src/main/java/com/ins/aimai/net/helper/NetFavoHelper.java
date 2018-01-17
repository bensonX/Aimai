package com.ins.aimai.net.helper;

import android.content.Context;
import android.view.View;

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
        netAddCollect(id, type, null);
    }

    //添加或收藏，并设置favoButton收藏按钮的selected状态
    public void netAddCollect(int id, int type, final View favoButton) {
        favoButton.setEnabled(false);
        Map<String, Object> param = new NetParam()
                .put("type", type)
                .put("targetId", id)
                .build();
        NetApi.NI().addCollect(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg);
                favoButton.setEnabled(true);
                if (favoButton != null) {
                    favoButton.setSelected(!favoButton.isSelected());
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                favoButton.setEnabled(true);
            }
        });
    }

    //检查是否收藏，如果已收藏则把favoButton置为selected状态
    public void netIsCollect(int id, int type, final View favoButton) {
        favoButton.setEnabled(false);
        Map<String, Object> param = new NetParam()
                .put("type", type)
                .put("targetId", id)
                .build();
        NetApi.NI().isCollect(param).enqueue(new BaseCallback<Integer>(Integer.class) {
            @Override
            public void onSuccess(int status, Integer bean, String msg) {
                favoButton.setEnabled(true);
                if (favoButton != null) {
                    favoButton.setSelected((bean != null && bean == 1) ? true : false);
                }
            }

            @Override
            public void onError(int status, String msg) {
                favoButton.setEnabled(true);
            }
        });
    }
}
