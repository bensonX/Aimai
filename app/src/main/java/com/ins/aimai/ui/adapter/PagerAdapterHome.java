package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.BuildingFragment;
import com.ins.aimai.ui.fragment.LessonFragment;
import com.ins.aimai.ui.fragment.HomeFragment;
import com.ins.aimai.ui.fragment.MeFragment;
import com.ins.aimai.ui.fragment.StudyFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterHome extends FragmentPagerAdapter {

    public PagerAdapterHome(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position);
            case 1:
                return LessonFragment.newInstance(position);
            case 2:
                return StudyFragment.newInstance(position);
            case 3:
                return MeFragment.newInstance(position);
            default:
                return null;
        }
    }
}
