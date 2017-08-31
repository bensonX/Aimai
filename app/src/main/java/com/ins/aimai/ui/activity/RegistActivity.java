package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.interfaces.PagerInter;
import com.ins.aimai.interfaces.RegisterInter;
import com.ins.aimai.ui.adapter.PagerAdapterRegist;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.ViewPagerUtil;

//type: 0 个人 1：公司 2：政府
public class RegistActivity extends BaseAppCompatActivity implements RegisterInter, PagerInter {

    private ViewPager pager;
    private PagerAdapterRegist adapterPager;

    private String[] titles = new String[]{"注册", "设置密码", "个人信息"};
    private int type;
    private User register = new User();

    public int getType() {
        return type;
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, RegistActivity.class);
        intent.putExtra("type", type);
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
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterRegist(getSupportFragmentManager(), titles);
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
    }

    private void initData() {
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() != 0) {
            ViewPagerUtil.last(pager);
        } else {
            finish();
        }
    }

    ////////////////////////////////

    @Override
    public User getRegister() {
        return register;
    }

    @Override
    public void saveRegister(User register) {
        this.register = register;
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
