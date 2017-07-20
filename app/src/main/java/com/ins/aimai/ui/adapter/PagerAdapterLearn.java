package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.LearnLessonFragment;
import com.ins.aimai.ui.fragment.LearnEmployFragment;
import com.ins.aimai.ui.fragment.LearnTestFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterLearn extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterLearn(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LearnLessonFragment.newInstance(position);
            case 1:
                return LearnTestFragment.newInstance(position);
            case 2:
                return LearnEmployFragment.newInstance(position);
            default:
                return null;
        }
    }
}
