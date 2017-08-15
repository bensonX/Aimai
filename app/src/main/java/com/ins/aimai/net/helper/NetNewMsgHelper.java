package com.ins.aimai.net.helper;

import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class NetNewMsgHelper {
    private static NetNewMsgHelper instance = null;

    private NetNewMsgHelper() {
    }

    public static synchronized NetNewMsgHelper getInstance() {
        if (instance == null) {
            instance = new NetNewMsgHelper();
        }
        return instance;
    }


    public void netHasNewMsg() {
        Map<String, Object> param = new NetParam().build();
        NetApi.NI().hasNewMsg(param).enqueue(new BaseCallback<Integer>(Integer.class) {
            @Override
            public void onSuccess(int status, Integer hasNewMsg, String msg) {
                if (hasNewMsg != null && hasNewMsg == 1) {
                    //有新消息
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_MSG_NEW));
                } else {
                    //无新消息
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_MSG_NEW_NONE));
                }
            }

            @Override
            public void onError(int status, String msg) {
            }
        });
    }
}
