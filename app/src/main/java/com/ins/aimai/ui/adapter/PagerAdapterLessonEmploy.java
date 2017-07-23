package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.LessonEmployFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterLessonEmploy extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterLessonEmploy(FragmentManager fm, String[] titles) {
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
                return LessonEmployFragment.newInstance(position);
            case 1:
                return LessonEmployFragment.newInstance(position);
            default:
                return null;
        }
    }
}
