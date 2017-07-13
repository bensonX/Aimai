package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterForgetPsw;
import com.ins.aimai.ui.adapter.PagerAdapterRegist;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.ViewPagerUtil;

public class RegistActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ViewPager pager;
    private PagerAdapterRegist adapterPager;
    private View btn_right;

    private String[] titles = new String[]{"注册", "设置密码","个人信息"};

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
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
        btn_right = findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterRegist(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setToolbar(titles[position]);
                if (position == titles.length - 1) {
                    btn_right.setVisibility(View.GONE);
                } else {
                    btn_right.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                ViewPagerUtil.next(pager);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() != 0) {
            ViewPagerUtil.last(pager);
        } else {
            finish();
        }
    }
}
