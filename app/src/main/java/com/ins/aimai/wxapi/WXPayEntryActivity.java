package com.ins.aimai.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.ui.adapter.PagerAdapterLessonDetail;
import com.ins.aimai.ui.adapter.RecycleAdapterLable;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;

public class WXPayEntryActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, WXPayEntryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payresult);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
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
