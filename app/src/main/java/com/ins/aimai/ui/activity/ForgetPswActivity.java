package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.ui.adapter.PagerAdapterForgetPsw;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.ViewPagerUtil;

public class ForgetPswActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ViewPager pager;
    private PagerAdapterForgetPsw adapterPager;
    private View btn_right;

    private String[] titles = new String[]{"忘记密码", "设置新密码"};

    public static void start(Context context) {
        Intent intent = new Intent(context, ForgetPswActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpsw);
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
        adapterPager = new PagerAdapterForgetPsw(getSupportFragmentManager(), titles);
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
                Fragment currentFragment = adapterPager.getCurrentFragment();
                if (currentFragment instanceof PagerFragmentInter) {
                    if (((PagerFragmentInter) currentFragment).next()) {
                        ViewPagerUtil.next(pager);
                    }
                } else {
                    ViewPagerUtil.next(pager);
                }
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
