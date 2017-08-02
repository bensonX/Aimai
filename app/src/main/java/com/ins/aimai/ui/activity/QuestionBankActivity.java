package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.interfaces.PagerInter;
import com.ins.aimai.ui.adapter.PagerAdapterQuestionBank;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.ViewPagerUtil;

public class QuestionBankActivity extends BaseAppCompatActivity implements View.OnClickListener{

    private ViewPager pager;
    private PagerAdapterQuestionBank adapterPager;
    private int type;
    private String[] titles;

    public int getType() {
        return type;
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, QuestionBankActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionbank);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (type == 0) {
            titles = new String[]{"练习题库", "练习列表"};
        } else {
            titles = new String[]{"错题库", "错题列表"};
        }
        setToolbar(titles[0]);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterQuestionBank(getSupportFragmentManager(), titles);
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

    public void next() {
        ViewPagerUtil.next(pager);
    }

    public void last() {
        ViewPagerUtil.last(pager);
    }

    public void goPosition(int position) {
        ViewPagerUtil.goPosition(pager, position);
    }
}
