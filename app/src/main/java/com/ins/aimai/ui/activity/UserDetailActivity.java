package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.GridAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;

public class UserDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private GridView grid;
    private GridAdapterLesson adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
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
    }

    private void initCtrl() {
        adapter = new GridAdapterLesson(this);
        grid.setAdapter(adapter);
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
            case R.id.btn_right:
                break;
        }
    }
}
