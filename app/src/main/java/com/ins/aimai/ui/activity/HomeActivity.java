package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterHome;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StatusBarTextUtil;

public class HomeActivity extends BaseAppCompatActivity {

    private RadioGroup group_tab;
    private ViewPager pager;
    private PagerAdapterHome pagerAdapter;
    private int[] tabsId = new int[]{R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4};

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNeedDoubleClickExit(true);

//        PermissionsUtil.checkAndRequestPermissions(this);

        StatusBarTextUtil.transparencyBar(HomeActivity.this);
        StatusBarTextUtil.StatusBarLightMode(HomeActivity.this);

        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
//        TypedArray actionbarSizeTypedArray = this.obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
//        float result = actionbarSizeTypedArray.getDimension(0, 0);
//        Toast.makeText(HomeActivity.this,result+":"+ DensityUtil.px2dp(this,result),Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager_home);
        group_tab = (RadioGroup) findViewById(R.id.group_tab);
//        pager.setOffscreenPageLimit(4);
    }

    private void initCtrl() {
        pagerAdapter = new PagerAdapterHome(getSupportFragmentManager());
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
                        pager.setCurrentItem(i, false);
                    }
                }
            }
        });
    }

    private void initData() {
    }
}
