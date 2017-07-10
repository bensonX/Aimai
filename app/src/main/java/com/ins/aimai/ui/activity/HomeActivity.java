package com.ins.aimai.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterHome;
import com.ins.aimai.ui.base.BaseAppCompatActivity;

public class HomeActivity extends BaseAppCompatActivity {

    private RadioGroup group_tab;
    private ViewPager pager;
    private PagerAdapterHome pagerAdapter;
    private int[] tabsId = new int[]{R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4, R.id.tab_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager_home);
        group_tab = (RadioGroup) findViewById(R.id.group_tab);
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
                        pager.setCurrentItem(i);
                    }
                }
            }
        });
    }

    private void initData() {
    }
}
