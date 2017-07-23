package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.adapter.GridAdapterLessonComp;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.StatusBarTextUtil;

public class CompDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView grid;
    private GridAdapterLessonComp adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CompDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compdetail);
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
    }

    private void initView() {
        grid = (GridView) findViewById(R.id.grid);
        findViewById(R.id.lay_compdetail_header).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new GridAdapterLessonComp(this);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_compdetail_header:
                CompInfoActivity.start(this);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LessonEmployActivity.start(this);
    }
}
