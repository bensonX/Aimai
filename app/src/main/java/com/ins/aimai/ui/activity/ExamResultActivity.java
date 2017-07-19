package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterExamResult;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;

public class ExamResultActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener,View.OnClickListener {

    private View showin;
    private ViewGroup showingroup;
    private RecyclerView recycler;
    private RecycleAdapterExamResult adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExamResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        setToolbar();
//        StatusBarTextUtil.StatusBarLightMode(this);
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
        findViewById(R.id.btn_go_error).setOnClickListener(this);
        findViewById(R.id.btn_go_all).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterExamResult(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.getResults().clear();
                for (int i = 0; i < 100; i++) {
                    adapter.getResults().add(new TestBean());
                }
                adapter.notifyDataSetChanged();
                LoadingViewHelper.showout(showingroup, showin);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go_error:
                break;
            case R.id.btn_go_all:
                break;
        }
    }
}
