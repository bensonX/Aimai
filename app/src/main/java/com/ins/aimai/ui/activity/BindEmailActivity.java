package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.interfaces.PagerInter;
import com.ins.aimai.ui.adapter.PagerAdapterBindEmail;
import com.ins.aimai.ui.adapter.PagerAdapterForgetPsw;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.ViewPagerUtil;

public class BindEmailActivity extends BaseAppCompatActivity implements PagerInter {

    private ViewPager pager;
    private PagerAdapterBindEmail adapterPager;

    private String[] titles = new String[]{"绑定邮箱", "绑定邮箱"};

    public static void start(Context context) {
        Intent intent = new Intent(context, BindEmailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindemail);
        setToolbar(titles[0]);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterBindEmail(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setToolbar(titles[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (AppHelper.UserHelp.hasEmail()){
            pager.setCurrentItem(1,false);
        }
    }

    private void initData() {
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

    @Override
    public void next() {
        ViewPagerUtil.next(pager);
    }

    @Override
    public void last() {
        ViewPagerUtil.last(pager);
    }

    @Override
    public void goPosition(int position) {
        ViewPagerUtil.goPosition(pager, position);
    }
}
