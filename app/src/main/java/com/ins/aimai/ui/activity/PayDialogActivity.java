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
import com.ins.aimai.ui.fragment.BuildingFragment;
import com.ins.aimai.ui.fragment.PayDialogCountFragment;
import com.ins.aimai.ui.fragment.PayDialogWayFragment;

public class PayDialogActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PayDialogActivity.class);
        context.startActivity(intent);
    }

    private ViewPager viewPager;
    private PayDialogActivity.MyPagerAdapter pagerAdapter;
    private String[] title = new String[]{"购买数量", "选择支付方式"};

    public void next() {
        int position = viewPager.getCurrentItem();
        int count = viewPager.getChildCount();
        if (position < count - 1) {
            setPage(position + 1);
        }
    }

    public void last() {
        int position = viewPager.getCurrentItem();
        if (position > 0) {
            setPage(position - 1);
        } else if (position == 0) {
            finish();
        }
    }

    public void goPosition(int position) {
        if (position >= 0 && position < title.length) {
            setPage(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paydialog);

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
    }

    private void initBase() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                last();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean back2first = false;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewPager.getCurrentItem() != 0) {
                if (back2first) {
                    goPosition(0);
                } else {
                    last();
                }
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
