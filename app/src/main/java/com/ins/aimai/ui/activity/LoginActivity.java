package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.CommonBean;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogIdentify;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.entity.Image;
import com.ins.aimai.net.NetApi;
import com.ins.common.utils.StatusBarTextUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private DialogIdentify dialogIdentify;
    private EditText edit_login_name;
    private EditText edit_login_psw;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        dialogIdentify = new DialogIdentify(this);
        dialogIdentify.setOnIdentifyListener(new DialogIdentify.OnIdentifyListener() {
            @Override
            public void onPersonClick(View v) {
                RegistActivity.start(LoginActivity.this);
            }

            @Override
            public void onCompClick(View v) {
                RegistActivity.start(LoginActivity.this);
            }

            @Override
            public void onGovClick(View v) {
                RegistActivity.start(LoginActivity.this);
            }
        });
    }

    private void initView() {
        edit_login_name = (EditText) findViewById(R.id.edit_login_name);
        edit_login_psw = (EditText) findViewById(R.id.edit_login_psw);
        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_login_forgetpsw).setOnClickListener(this);
        findViewById(R.id.btn_login_regist).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                String name = edit_login_name.getText().toString();
                String psw = edit_login_psw.getText().toString();
                String msg = AppVali.login(name, psw);
                if (msg == null) {
                    netLogin(name, psw);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
            case R.id.btn_login_forgetpsw:
                ForgetPswActivity.start(this);
                break;
            case R.id.btn_login_regist:
                dialogIdentify.show();
                break;
        }
    }

    private void netTest() {
//        Map<String, String> param = new HashMap<String, String>() {{
//            //无参数
//        }};
        Map<String, Object> param = new NetParam()
                .put("phone", "18002247238")
                .build();
        NetApi.NI().netTest(param).enqueue(new BaseCallback<TestBean>(TestBean.class) {
            @Override
            public void onSuccess(int status, TestBean images, String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    private void netLogin(String phone, String password) {
        Map<String, Object> param = new NetParam()
                .put("phone", phone)
                .put("password", password)
                .put("deviceType", 0)
                .put("deviceToken", 0)
                .put("isWechat", JPushInterface.getRegistrationID(this))
                .build();
        NetApi.NI().login(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
//    private void netUpload() {
//        File file = new File("");
//        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
//        NetApi.NI().uploadFile(part).enqueue(new BaseCallback<TestBean>(TestBean.class) {
//            @Override
//            public void onSuccess(int status, TestBean images, String msg) {
//                ToastUtil.showToastShort(msg);
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                ToastUtil.showToastShort(msg);
//            }
//        });
//    }
}
