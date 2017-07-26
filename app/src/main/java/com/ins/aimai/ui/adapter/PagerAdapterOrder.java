package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ins.aimai.ui.fragment.OrderFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterOrder extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterOrder(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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
                return OrderFragment.newInstance(position);
            case 1:
                return OrderFragment.newInstance(position);
            case 2:
                return OrderFragment.newInstance(position);
            default:
                return null;
        }
    }
}
