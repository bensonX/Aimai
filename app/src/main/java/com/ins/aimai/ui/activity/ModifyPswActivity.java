package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ins.aimai.R;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.MD5Util;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class ModifyPswActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private EditText edit_modifypsw_oldpsw;
    private EditText edit_modifypsw_newpsw;
    private EditText edit_modifypsw_newpsw_repeat;

    public static void start(Context context) {
        Intent intent = new Intent(context, ModifyPswActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypsw);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        edit_modifypsw_oldpsw = (EditText) findViewById(R.id.edit_modifypsw_oldpsw);
        edit_modifypsw_newpsw = (EditText) findViewById(R.id.edit_modifypsw_newpsw);
        edit_modifypsw_newpsw_repeat = (EditText) findViewById(R.id.edit_modifypsw_newpsw_repeat);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                String oldPsw = edit_modifypsw_oldpsw.getText().toString();
                String newPsw = edit_modifypsw_newpsw.getText().toString();
                String newPsw_repeat = edit_modifypsw_newpsw_repeat.getText().toString();
                String msg = AppVali.modifyPsw(oldPsw, newPsw, newPsw_repeat);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    netSetPsw(oldPsw, newPsw);
                }
                break;
        }
    }

    private void netSetPsw(String oldPwd, String newPwd) {
        Map<String, Object> param = new NetParam()
                .put("oldPwd", MD5Util.md5(oldPwd))
                .put("newPwd", MD5Util.md5(newPwd))
                .build();
        showLoadingDialog();
        NetApi.NI().setPwd(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg);
                AppData.App.removeToken();
                AppData.App.removeUser();
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGOUT));
                finish();
                LoginActivity.start(ModifyPswActivity.this);
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
