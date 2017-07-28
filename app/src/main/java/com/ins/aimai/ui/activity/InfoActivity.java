package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.BannerView;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

public class InfoActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterHomeInfo adapter;

    private NetListHelper netListHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
        findViewById(R.id.btn_right).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterHomeInfo(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netListHelper.netQueryInfo(0);
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryInfo(1);
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryInfo(2);
            }
        });
        netListHelper = new NetListHelper<Info>().init(loadingLayout, springView, new TypeToken<List<Info>>() {
        }.getType(), new NetListHelper.OnListLoadCallback<Info>() {
            @Override
            public void onFreshSuccess(int status, List<Info> beans, String msg) {
                adapter.getResults().clear();
                adapter.getResults().addAll(beans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadSuccess(int status, List<Info> beans, String msg) {
                adapter.getResults().addAll(beans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        netListHelper.netQueryInfo(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Info info = adapter.getResults().get(viewHolder.getLayoutPosition());
        String url = NetApi.getBaseUrl() + AppData.Url.newsInfo + "?newsId=" + info.getId();
        WebActivity.start(this, info.getTitle(), url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                InfoSearchActivity.start(this);
                break;
        }
    }
}
