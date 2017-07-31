package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class DefinitionActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radiogroup;

    public static void start(Context context) {
        if (AppData.App.getUser() != null) {
            Intent intent = new Intent(context, DefinitionActivity.class);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
    }

    private void initCtrl() {
        User user = AppData.App.getUser();
        if (user.getDefinition() == 0) {
            radiogroup.check(R.id.radio_definition_low);
        } else if (user.getDefinition() == 1) {
            radiogroup.check(R.id.radio_definition_hight);
        }
        radiogroup.setOnCheckedChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_definition_low:
                netCommit(0);
                break;
            case R.id.radio_definition_hight:
                netCommit(1);
                break;
        }
    }

    private void netCommit(final int definition) {
        NetParam netParam = new NetParam();
        netParam.put("definition", definition);
        Map<String, Object> param = netParam.build();
        showLoadingDialog();
        NetApi.NI().updateUser(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                ToastUtil.showToastShort(msg);
                User user = AppData.App.getUser();
                user.setDefinition(definition);
                AppData.App.saveUser(user);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
