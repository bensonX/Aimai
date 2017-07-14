package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ins.aimai.ui.fragment.LessonFragment;
import com.ins.aimai.ui.fragment.HomeFragment;
import com.ins.aimai.ui.fragment.MeFragment;
import com.ins.aimai.ui.fragment.LearnFragment;

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
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position);
            case 1:
                return LessonFragment.newInstance(position);
            case 2:
                return LearnFragment.newInstance(position);
            case 3:
                return MeFragment.newInstance(position);
            default:
                return null;
        }
    }
}
