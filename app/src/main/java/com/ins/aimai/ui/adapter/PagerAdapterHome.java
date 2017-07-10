package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ins.aimai.ui.fragment.BuildingFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterHome extends FragmentPagerAdapter {

    public PagerAdapterHome(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BuildingFragment.newInstance(position);
            case 1:
                return BuildingFragment.newInstance(position);
            case 2:
                return BuildingFragment.newInstance(position);
            case 3:
                return BuildingFragment.newInstance(position);
            case 4:
                return BuildingFragment.newInstance(position);
            default:
                return null;
        }
    }
}
