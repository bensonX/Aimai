package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterLearn;
import com.ins.aimai.ui.base.BaseFragment;

/**
 * Created by liaoinstan
 */
public class LearnFragment extends BaseFragment {

    private int position;
    private View rootView;

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterLearn adapterPager;

    private String[] titles = new String[]{"课程学习", "考题"};

    public static Fragment newInstance(int position) {
        LearnFragment fragment = new LearnFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_learn, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        tab = (TabLayout) rootView.findViewById(R.id.tab);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLearn(getActivity().getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
    }

    private void initData() {
    }
}
