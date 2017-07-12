package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.dl7.player.media.IjkPlayerView;
import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterOrder;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class OrderActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterOrder adapterPager;

    private String[] titles = new String[]{"全部订单", "待付款", "已完成"};

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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
        adapterPager = new PagerAdapterOrder(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
    }

    private void initData() {
    }
}
