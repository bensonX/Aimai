package com.ins.aimai.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.ui.fragment.ExamFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterExam extends FragmentStatePagerAdapter {

    private List<QuestionBean> results = new ArrayList<>();

    public List<QuestionBean> getResults() {
        return results;
    }

    public PagerAdapterExam(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ExamFragment.newInstance(position);
    }
}
