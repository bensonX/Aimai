package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ins.aimai.ui.fragment.VideoCommentFragment;
import com.ins.aimai.ui.fragment.VideoDirectotyFragment;
import com.ins.aimai.ui.fragment.VideoIntroFragment;
import com.ins.aimai.ui.fragment.VideoNoteFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterVideo extends FragmentPagerAdapter {

    private int lessonId;
    private String[] titles;

    public PagerAdapterVideo(FragmentManager fm, String[] titles, int lessonId) {
        super(fm);
        this.lessonId = lessonId;
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
                return VideoIntroFragment.newInstance(position);
            case 1:
                return VideoDirectotyFragment.newInstance(position);
            case 2:
                return VideoNoteFragment.newInstance(position);
            case 3:
                return VideoCommentFragment.newInstance(position, lessonId);
            default:
                return null;
        }
    }
}
