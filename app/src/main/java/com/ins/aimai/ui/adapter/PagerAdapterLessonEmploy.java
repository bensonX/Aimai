package com.ins.aimai.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.ins.aimai.R;
import com.ins.aimai.ui.fragment.FavoFragment;
import com.ins.aimai.ui.fragment.InfoFragment;
import com.ins.aimai.ui.fragment.LessonEmployFragment;
import com.ins.common.utils.App;

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
