package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.utils.StatusBarTextUtil;

/**
 * Created by liaoinstan
 */
public class StudyFragment extends BaseFragment {

    private int position;
    private View rootView;

    public static Fragment newInstance(int position) {
        StudyFragment fragment = new StudyFragment();
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        StatusBarTextUtil.StatusBarLightMode(getActivity());
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_study, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initData() {
    }

    private void initCtrl() {
    }
}
