package com.ins.aimai.common;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.utils.ToastUtil;
import com.ins.aimai.wxapi.WXPayEntryActivity;

import java.util.LinkedHashMap;
import java.util.Map;

import paytest.ins.com.helper.PayHelper;

/**
 * Created by liaoinstan on 2017/7/25.
 * PayHelper 的拓展类，在原有基础上增加获取签名的功能
 * 使用方法：
 * {@link #startPayZhifubao(int)}
 * {@link #startPayWeixin(int)}
 */

public class PayHelperEx extends PayHelper {

    public static int orderId;

    public PayHelperEx(Activity activity) {
        super(activity);
    }

    public static PayHelperEx newInstance(Activity activity){
        return new PayHelperEx(activity);
    }

    public void startPayZhifubao(int orderId) {
        PayHelperEx.orderId = orderId;
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .build();
        NetApi.NI().signZhifubao(param).enqueue(new BaseCallback<LinkedHashMap<String, String>>(new TypeToken<LinkedHashMap<String, String>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, LinkedHashMap<String, String> map, String msg) {
                callZhifubao(map);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public void startPayWeixin(int orderId) {
        PayHelperEx.orderId = orderId;
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .build();
        NetApi.NI().signWeixin(param).enqueue(new BaseCallback<LinkedHashMap<String, String>>(new TypeToken<LinkedHashMap<String, String>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, LinkedHashMap<String, String> map, String msg) {
                callWeixin(map);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    @Override
    protected void onPayStart() {
    }

    @Override
    protected void onPaySuccess() {
        WXPayEntryActivity.startPaySuccess(activity);
//        Intent intent = new Intent(activity, WXPayEntryActivity.class);
//        intent.putExtra("type", 0);
//        activity.startActivity(intent);
    }

    @Override
    protected void onPayFail() {
        WXPayEntryActivity.startPayFail(activity);
//        Intent intent = new Intent(activity, WXPayEntryActivity.class);
//        intent.putExtra("type", -1);
//        activity.startActivity(intent);
    }

    @Override
    protected void onPayCancel() {
        WXPayEntryActivity.startPayCancel(activity);
//        Intent intent = new Intent(activity, WXPayEntryActivity.class);
//        intent.putExtra("type", -2);
//        activity.startActivity(intent);
    }

}
