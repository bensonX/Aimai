package com.ins.aimai.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.ui.activity.MsgActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JGReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            processIDMessage(context, regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
            processNotifyClick(context, bundle);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //接受的设备ID
    private void processIDMessage(Context context, String id) {
        Log.e("push", "push id:" + id);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //发送新消息信息
        EventBus.getDefault().post(new EventBean(EventBean.EVENT_MSG_NEW));
        Log.e("push", "push message:" + message + extras);
    }

    private void processNotifyClick(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MsgActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        Log.e("push", "push message:" + extras);
//
//        if (!TextUtils.isEmpty(extras)) {
//            //有拖拽字段，跳转消息页面
//            try {
//                JSONObject datajson = new JSONObject(extras);
//                if (datajson.has("info_type")) {
//                    String type = datajson.getString("info_type");
//                    Intent intent = new Intent();
//                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    if ("2".equals(type)) {
//                        //跳转订单消息页面
//                    } else if ("1".equals(type)) {
//                        //系统消息
//                        context.startActivity(intent);
//                    } else if ("3".equals(type)) {
//                        //丁一定
//                    } else if ("4".equals(type)) {
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("startapp", "启动消息页面失败");
//            }
//        } else {
        //////////启动app
//            try {
//                String appPackageName = "com.ins.wojia";
//                boolean runningApp = AppUtils.isRunningApp(context, appPackageName);
//                if (!runningApp) {
//                    AppUtils.startAPP(context, appPackageName);
//                } else {
//                    Log.e("liao", "isRunning");
//                }
//            } catch (Exception e) {
//                Log.e("startapp", "启动app失败");
//                e.printStackTrace();
//            }
//        }
    }
}
