package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.shelwee.update.UpdateHelper;
import com.shelwee.update.utils.VersionUtil;

public class VersionActivity extends BaseAppCompatActivity {

    public static String versionUrl = NetApi.getBaseUrl() + AppData.Url.version;
    UpdateHelper updateHelper;

    TextView vname;
    TextView vcheck;

    public static void start(Context context) {
        Intent intent = new Intent(context, VersionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        setToolbar();
        initBase();
        initView();
        initCtrl();
    }

    private void initView() {
        vname = (TextView) findViewById(R.id.version_vname);
        vcheck = (TextView) findViewById(R.id.version_check);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateHelper != null) updateHelper.onDestory();
    }

    private void initBase() {
        updateHelper = new UpdateHelper.Builder(this).checkUrl(versionUrl).build();
        updateHelper.setOnMsgHandler(new UpdateHelper.OnMsgHandler() {
            @Override
            public void onMsg(String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    private void initCtrl() {
        String version = VersionUtil.getVersion(this);
        vname.setText(version);
        vcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHelper.check();
            }
        });
    }
}
