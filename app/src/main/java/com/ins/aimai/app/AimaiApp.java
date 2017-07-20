package com.ins.aimai.app;

import android.app.Application;

import com.ins.aimai.BuildConfig;
import com.ins.aimai.R;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.App;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.SharedPrefUtilV2;
import com.ins.domain.launcher.DomainLauncher;

import cn.jpush.android.api.JPushInterface;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AimaiApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSetting();
        initLauncher();
        initFonts();
        initJpush();
    }

    private void initSetting() {
        SharedPrefUtilV2.init(this);
        App.saveApplication(this);
        L.setDEBUG(BuildConfig.DEBUG);
        ToastUtil.init(this);
        ToastUtil.setDebug(BuildConfig.DEBUG);
        ToastUtil.setStyle(R.layout.lay_toast,R.id.toast_tv);
        GlideUtil.init(this);
    }

    private void initJpush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);            // 初始化 JPush
        JPushInterface.getRegistrationID(this); //在这里获取一次JpushID
    }

    private void initLauncher() {
        DomainLauncher.getInstance().setSettingChangeCallback(new DomainLauncher.SettingChangeCallback() {
            @Override
            public void onDomainChange(String domain) {
                NetApi.setBaseUrl("http://" + domain + "/");
            }
        });
        DomainLauncher.getInstance()
                .addDomain("192.168.118.206:8080", "(Web开发服务器)")
                .addDomain("192.168.1.166", "(开发服务器：谢启谋)")
                .addDomain("192.168.118.110:8080", "(测试服务器)")
                .addDomain("139.129.111.76:8102", "(远程测试服务器)")
                .addDomain("tiger.magic-beans.cn", "(远程测试服务器)");
    }

    private void initFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ltx.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
