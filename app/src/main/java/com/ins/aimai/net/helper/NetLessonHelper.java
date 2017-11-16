package com.ins.aimai.net.helper;

import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;

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
//                if (callback != null) {
//                    if (isBuy == 1) callback.onYes();
//                    else callback.onNo();
//                }
                if (callback != null) {
                    switch (isBuy) {
                        case -1:
                            //有订单，该订单已经支付
                            callback.onYes();
                            break;
                        case -2:
                            //有订单，该订单未支付
                            callback.onNoPay();
                            break;
                        case 0:
                            //没有订单
                            callback.onNo();
                            break;
                        default:
                            callback.onNo();
                            break;
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onNo();
            }
        });
    }

    public interface IsBuyCallback {
        void onYes();

        void onNo();

        void onNoPay();
    }
}
