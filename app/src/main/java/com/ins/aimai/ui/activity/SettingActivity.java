package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ClearCacheUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class SettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView text_setting_catchsize;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        text_setting_catchsize = (TextView) findViewById(R.id.text_setting_catchsize);
        findViewById(R.id.lay_setting_definition).setOnClickListener(this);
        findViewById(R.id.lay_setting_modifypsw).setOnClickListener(this);
        findViewById(R.id.lay_setting_clear).setOnClickListener(this);
        findViewById(R.id.lay_setting_about).setOnClickListener(this);
        findViewById(R.id.lay_setting_logout).setOnClickListener(this);
    }

    private void initCtrl() {
        text_setting_catchsize.setText(ClearCacheUtil.getAppCacheSize(this));
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_setting_definition:
                DefinitionActivity.start(this);
                break;
            case R.id.lay_setting_modifypsw:
                ModifySelectActivity.start(this);
                break;
            case R.id.lay_setting_clear:
                if (ClearCacheUtil.getAppCacheSizeValue(this) == 0) {
                    ToastUtil.showToastShort("没有需要清除的缓存");
                    return;
                }
                DialogSure.showDialog(this, "清除缓存将会删除您应用内的本地图片及数据且无法恢复，确认继续？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ClearCacheUtil.clearAPPCache(SettingActivity.this);
                        text_setting_catchsize.setText(ClearCacheUtil.getAppCacheSize(SettingActivity.this));
                    }
                });
                break;
            case R.id.lay_setting_about:
                WebActivity.start(this, "关于我们", "http://www.baidu.com");
                break;
            case R.id.lay_setting_logout:
                DialogSure.showDialog(this, "确定要退出登录？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        netLogout();
                    }
                });
                break;
        }
    }

    private void netLogout() {
        Map<String, Object> param = new NetParam().build();
        NetApi.NI().queryTrade(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                AppData.App.removeToken();
                AppData.App.removeUser();
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGOUT));
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
