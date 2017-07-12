package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dl7.player.media.IjkPlayerView;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.PagerAdapterLessonDetail;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.adapter.RecycleAdapterLable;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class LessonDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterLessonDetail adapterPager;

    private RecyclerView recycler;
    private RecycleAdapterLable adapter;

    private ImageView img_lessondetail_cover;
    private View btn_go;

    private String[] titles = new String[]{"介绍", "目录", "评论"};

    public static void start(Context context) {
        Intent intent = new Intent(context, LessonDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetail);
        setToolbar();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        img_lessondetail_cover = (ImageView) findViewById(R.id.img_lessondetail_cover);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLessonDetail(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);

        //设置lable
        adapter = new RecycleAdapterLable(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();

        GlideUtil.loadImgTest(img_lessondetail_cover);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                PayDialogActivity.start(this);
                break;
        }
    }
}
