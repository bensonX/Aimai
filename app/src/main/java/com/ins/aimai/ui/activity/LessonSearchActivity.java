package com.ins.aimai.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class LessonSearchActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private LoadingLayout loadingLayout;
    private EditText edit_query;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLesson adapter;

    private NetListHelper netListHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, LessonSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_search);
        setToolbar(false);
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
        edit_query = (EditText) findViewById(R.id.edit_query);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLesson(this, OrientationHelper.HORIZONTAL);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netListHelper.netQueryList(0);
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryList(1);
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryList(2);
            }
        });
        netListHelper = new NetListHelper<Lesson>().init(loadingLayout, springView,
                new TypeToken<List<Lesson>>() {
                }.getType(),
                new NetListHelper.CallHander() {
                    @Override
                    public Call getCall(int type) {
                        String searchParam = edit_query.getText().toString();
                        Map param = netListHelper.getParam(type);
                        param.put("searchParam", searchParam);
                        return NetApi.NI().queryInfo(param);
                    }
                },
                new NetListHelper.OnListLoadCallback<Lesson>() {
                    @Override
                    public void onFreshSuccess(int status, List<Lesson> beans, String msg) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoadSuccess(int status, List<Lesson> beans, String msg) {
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }
                });
        edit_query.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    netListHelper.netQueryList(0);
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        netListHelper.netQueryList(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                netListHelper.netQueryList(0);
                break;
            case R.id.btn_left:
                onBackPressed();
                break;
        }
    }
}
