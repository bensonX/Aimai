package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.common.AppData;
import com.ins.aimai.ui.adapter.PagerAdapterFavo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;

public class FavoActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterFavo adapterPager;

    private String[] titles = new String[]{"课程", "考题", "资讯"};

    public static void start(Context context) {
        if (AppData.App.getUser() != null) {
            Intent intent = new Intent(context, FavoActivity.class);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favo);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterFavo(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
    }

    private void initData() {
    }
}
