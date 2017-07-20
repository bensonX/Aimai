package com.ins.aimai.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterEmploySearch;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

public class EmploySearchActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private View showin;
    private ViewGroup showingroup;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterEmploySearch adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, EmploySearchActivity.class);
        context.startActivity(intent);
        if (context instanceof Activity) ((Activity) context).overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ_search);
        setToolbar(null, false);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        showingroup = (ViewGroup) findViewById(R.id.showingroup);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
        findViewById(R.id.btn_right).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterEmploySearch(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getResults().add(new TestBean());
                        adapter.getResults().add(new TestBean());
                        adapter.getResults().add(new TestBean());
                        adapter.notifyItemRangeChanged(adapter.getItemCount() - 1 - 3, adapter.getItemCount() - 1);
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.getResults().clear();
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.notifyDataSetChanged();
                LoadingViewHelper.showout(showingroup, showin);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        WebActivity.start(this, "http://http://cn.bing.com");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                finish();
                break;
        }
    }
}
