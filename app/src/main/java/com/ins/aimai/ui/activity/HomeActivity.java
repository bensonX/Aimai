package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.ui.adapter.PagerAdapterHome;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.PermissionsUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.shelwee.update.UpdateHelper;
import com.tencent.bugly.crashreport.CrashReport;

public class HomeActivity extends BaseAppCompatActivity {

    private UpdateHelper updateHelper;
    private RadioGroup group_tab;
    private ViewPager pager;
    private PagerAdapterHome pagerAdapter;
    private int[] tabsId = new int[]{R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4};

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_HOME_TAB_LESSON) {
            //切换到我的课程
            group_tab.check(tabsId[2]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNeedDoubleClickExit(true);
        registEventBus();

        //权限检查
        PermissionsUtil.checkAndRequestPermissions(this);
        //版本更新检查
        //检查更新
        updateHelper = new UpdateHelper.Builder(this).checkUrl(VersionActivity.versionUrl).isHintNewVersion(false).build();
        updateHelper.check();

        StatusBarTextUtil.transparencyBar(HomeActivity.this);
        StatusBarTextUtil.StatusBarLightMode(HomeActivity.this);

        initBase();
        initView();
        initCtrl();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateHelper != null) updateHelper.onDestory();
    }

    private void initBase() {
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager_home);
        group_tab = (RadioGroup) findViewById(R.id.group_tab);
    }

    private void initCtrl() {
        pagerAdapter = new PagerAdapterHome(getSupportFragmentManager());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                group_tab.check(tabsId[position]);
                switch (position) {
                    case 0:
                        StatusBarTextUtil.StatusBarLightMode(HomeActivity.this);
                        break;
                    case 1:
                        StatusBarTextUtil.StatusBarLightMode(HomeActivity.this);
                        break;
                    case 2:
                        StatusBarTextUtil.StatusBarLightMode(HomeActivity.this);
                        break;
                    case 3:
                        StatusBarTextUtil.StatusBarDarkMode(HomeActivity.this);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        group_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < tabsId.length; i++) {
                    if (tabsId[i] == checkedId) {
                        if (checkedId == R.id.tab_3) {
                            //未登录点击学习要弹出登录页面
                            if (AppData.App.getUser() == null) {
                                LoginActivity.start(HomeActivity.this);
                                group_tab.check(tabsId[pager.getCurrentItem()]);
                            } else {
                                pager.setCurrentItem(i, false);
                            }
                        } else {
                            pager.setCurrentItem(i, false);
                        }
                    }
                }
            }
        });
    }

    private void initData() {
    }
}
