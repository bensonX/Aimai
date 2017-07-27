package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.ui.fragment.BuildingFragment;
import com.ins.aimai.ui.fragment.LearnCompFragment;
import com.ins.aimai.ui.fragment.LearnLessonFragment;
import com.ins.aimai.ui.fragment.LearnEmployFragment;
import com.ins.aimai.ui.fragment.LearnTestFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterLearn extends FragmentStatePagerAdapter {

    private String[] titles_user = new String[]{"课程学习", "考题",};
    private String[] titles_comp = new String[]{"课程学习", "人员列表"};
    private String[] titles_gov = new String[]{"政府"};

    public PagerAdapterLearn(FragmentManager fm, String[] titles) {
        super(fm);
        //this.titles_user = titles;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (getRoleId()) {
            case User.USER:
                return titles_user[position];
            case User.COMPANY_USER:
                return titles_comp[position];
            case User.GOVERNMENT_USER:
                return titles_gov[position];
            default:
                return "";
        }
    }

    @Override
    public int getCount() {
        switch (getRoleId()) {
            case User.USER:
                return titles_user.length;
            case User.COMPANY_USER:
                return titles_comp.length;
            case User.GOVERNMENT_USER:
                return titles_gov.length;
            default:
                return 0;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (getRoleId()) {
            case User.USER:
                if (position == 0) {
                    return LearnLessonFragment.newInstance(position);
                } else if (position == 1) {
                    return LearnTestFragment.newInstance(position);
                }
            case User.COMPANY_USER:
                if (position == 0) {
                    return LearnLessonFragment.newInstance(position);
                } else if (position == 1) {
                    return LearnEmployFragment.newInstance(position);
                }
            case User.GOVERNMENT_USER:
                return LearnCompFragment.newInstance(position);
            default:
                return BuildingFragment.newInstance(position);
        }
    }

    private int getRoleId() {
        User user = AppData.App.getUser();
        int roleId;
        if (user == null) {
            roleId = User.USER;
        } else {
            roleId = user.getRoleId();
        }
        return roleId;
    }
}
