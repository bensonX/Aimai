package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;

public class ModifySelectActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, ModifySelectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyselect);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        findViewById(R.id.lay_modify_forget).setOnClickListener(this);
        findViewById(R.id.lay_modify_remenber).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_modify_forget:
                ForgetPswActivity.start(this);
                break;
            case R.id.lay_modify_remenber:
                ModifyPswActivity.start(this);
                break;
        }
    }
}
