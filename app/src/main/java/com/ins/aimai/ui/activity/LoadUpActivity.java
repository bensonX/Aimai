package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.ins.aimai.R;
import com.ins.aimai.bean.EventBean;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.MD5Util;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class LoadUpActivity extends BaseAppCompatActivity {

    private long lasttime;
    private String token;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoadUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadup);

        lasttime = System.currentTimeMillis();

        //获取token
        token = AppData.App.getToken();
        if (TextUtils.isEmpty(token)) {
            //无token 等待2秒 去首页页
            checkTimeGoHomeActivity();
        } else {
            //有token 执行登录
            netLogin();
        }
    }

    private void goHomeActivity() {
        HomeActivity.start(LoadUpActivity.this);
        finish();
    }

    private void netLogin() {
        Map<String, Object> param = new NetParam().build();
        NetApi.NI().getInfo(param).enqueue(new BaseCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                AppData.App.saveUser(user);
                AppData.App.saveToken(user.getToken());
                checkTimeGoHomeActivity();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                AppData.App.removeUser();
                AppData.App.removeToken();
                checkTimeGoHomeActivity();
            }
        });
    }

    private void checkTimeGoHomeActivity() {
        long time = System.currentTimeMillis() - lasttime;
        if (time < 2000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goHomeActivity();
                }
            }, 2000 - time);
        } else {
            goHomeActivity();
        }
    }

}
