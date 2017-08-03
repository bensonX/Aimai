package com.ins.common.utils;

import android.support.v4.view.ViewPager;

/**
 * Created by liaoinstan on 2017/7/13.
 * ViewPager常用方法
 */

public class ViewPagerUtil {

    public static void next(ViewPager viewPager) {
        int position = viewPager.getCurrentItem();
        goPosition(viewPager, position + 1);
    }

    public static void last(ViewPager viewPager) {
        int position = viewPager.getCurrentItem();
        goPosition(viewPager, position - 1);
    }

    public static void goPosition(ViewPager viewPager, int position) {
        goPosition(viewPager, position, true);
    }

    public static void goPosition(ViewPager viewPager, int position, boolean needAnim) {
        int count = viewPager.getAdapter().getCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, needAnim);
        }
    }


}
