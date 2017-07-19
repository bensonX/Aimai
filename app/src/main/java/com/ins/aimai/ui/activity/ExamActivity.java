package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterExam;
import com.ins.aimai.ui.adapter.PagerAdapterFavo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.PopTextSize;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ViewPagerUtil;

public class ExamActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ViewPager pager;
    private PagerAdapterExam adapterPager;
    private View btn_right_textsize;
    private View btn_last;
    private View btn_next;
    private TextView text_exam_num;
    private TextView text_exam_count;
    private PopTextSize popTextSize;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExamActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        StatusBarTextUtil.setWindowStatusBarColor(this, R.color.am_bk);
        setToolbar(false);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        popTextSize = new PopTextSize(this);
        popTextSize.setNeedanim(false);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        btn_right_textsize = findViewById(R.id.btn_right_textsize);
        btn_last = findViewById(R.id.btn_last);
        btn_next = findViewById(R.id.btn_next);
        text_exam_num = (TextView) findViewById(R.id.text_exam_num);
        text_exam_count = (TextView) findViewById(R.id.text_exam_count);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right_favo).setOnClickListener(this);
        findViewById(R.id.btn_right_answerboard).setOnClickListener(this);
        btn_right_textsize.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterExam(getSupportFragmentManager());
        pager.setAdapter(adapterPager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                text_exam_num.setText(position + 1 + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        text_exam_num.setText("1");
        text_exam_count.setText("/" + adapterPager.getCount());
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                onBackPressed();
                break;
            case R.id.btn_last:
                ViewPagerUtil.last(pager);
                break;
            case R.id.btn_next:
                ViewPagerUtil.next(pager);
                break;
            case R.id.btn_right_answerboard:
                break;
            case R.id.btn_right_textsize:
                popTextSize.showPopupWindow(btn_right_textsize);
                break;
            case R.id.btn_right_favo:
                break;
        }
    }
}
