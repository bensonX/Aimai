package com.ins.common.utils;

import android.support.design.widget.TabLayout;

/**
 * Created by Administrator on 2017/7/18.
 */

public class TabLayoutUtil {
    public static void setTab(TabLayout tab, int position, String name) {
        setTab(tab, position, name, false);
    }

    public static void setTab(TabLayout tab, int position, String name, boolean isSelect) {
        TabLayout.Tab t;
        if (tab.getTabAt(position) != null) {
            t = tab.getTabAt(position);
            t.setText(name);
            if (isSelect) t.select();
        } else {
            t = tab.newTab();
            t.setText(name);
            tab.addTab(t, position);
            if (isSelect) t.select();
        }
    }

    public static void setTabCurrent(TabLayout tab, int position) {
        if (tab.getTabAt(position) != null) {
            tab.getTabAt(position).select();
        }
    }

    public static void removeTabAt(TabLayout tab, int position) {
        if (tab.getTabAt(position) != null) {
            tab.removeTabAt(position);
        }
    }
}
