package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.PracticeCateFragment;
import com.ins.aimai.ui.fragment.PracticeListFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterPractice extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterPractice(FragmentManager fm, String[] titles) {
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
                return PracticeCateFragment.newInstance(position);
            case 1:
                return PracticeListFragment.newInstance(position);
            default:
                return null;
        }
    }
}
