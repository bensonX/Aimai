package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.fragment.PayDialogCountFragment;
import com.ins.aimai.ui.fragment.PayDialogWayFragment;
import com.ins.common.utils.ViewPagerUtil;

import org.greenrobot.eventbus.EventBus;

public class PayDialogActivity extends BaseAppCompatActivity {

    //课程ID
    private int lessonId;
    //购买数量
    private int count;
    //订单id
    private int orderId;
    //是否重新支付
    private boolean isRepay;

    public static void startRepay(Context context, int orderId) {
        Intent intent = new Intent(context, PayDialogActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    public static void start(Context context, int lessonId) {
        Intent intent = new Intent(context, PayDialogActivity.class);
        intent.putExtra("lessonId", lessonId);
        context.startActivity(intent);
    }

    private ViewPager viewPager;
    private PayDialogActivity.MyPagerAdapter pagerAdapter;
    private String[] title = new String[]{"购买数量", "选择支付方式"};

    public void next() {
        ViewPagerUtil.next(viewPager);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_CLOSE_PAYWAY) {
            finish();
            EventBus.getDefault().cancelEventDelivery(event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paydialog);
        registEventBus();

        //设置从下方弹起，铺满宽度
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        initBase();
        initView();
        initData();
        initCtrl();

        //重新支付，直接到支付页面
        if (isRepay) {
            viewPager.setCurrentItem(1, false);
        }
    }

    private void initBase() {
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
            isRepay = true;
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initData() {
    }

    private void initCtrl() {
        pagerAdapter = new PayDialogActivity.MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private void setPage(int pos) {
        viewPager.setCurrentItem(pos);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return PayDialogCountFragment.newInstance(position);
            } else if (position == 1) {
                return PayDialogWayFragment.newInstance(position);
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isRepay) {
            if (viewPager.getCurrentItem() != 0) {
                ViewPagerUtil.last(viewPager);
            } else {
                finish();
            }
        }else {
            finish();
        }
    }

    /////////////////// get & set /////////////////

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
