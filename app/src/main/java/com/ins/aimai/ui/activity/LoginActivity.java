package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogIdentify;
import com.ins.common.utils.StatusBarTextUtil;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private DialogIdentify dialogIdentify;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                break;
            case R.id.btn_login_forgetpsw:
                ForgetPswActivity.start(this);
                break;
            case R.id.btn_login_regist:
                dialogIdentify.show();
                break;
        }
    }
}
