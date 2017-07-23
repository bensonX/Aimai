package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.ExamFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterExam extends FragmentPagerAdapter {

    public PagerAdapterExam(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Fragment getItem(int position) {
        return ExamFragment.newInstance(position);
    }
}
