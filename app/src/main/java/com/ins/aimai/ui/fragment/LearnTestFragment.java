package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.activity.ModelActivity;
import com.ins.aimai.ui.activity.OfficialActivity;
import com.ins.aimai.ui.activity.PracticeActivity;
import com.ins.aimai.ui.base.BaseFragment;

/**
 * Created by liaoinstan
 */
public class LearnTestFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;

    public static Fragment newInstance(int position) {
        LearnTestFragment fragment = new LearnTestFragment();
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
        rootView = inflater.inflate(R.layout.fragment_learn_test, container, false);
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
        rootView.findViewById(R.id.lay_learntest_practice).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_error).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_model).setOnClickListener(this);
        rootView.findViewById(R.id.lay_learntest_official).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_learntest_practice:
                PracticeActivity.start(getContext());
                break;
            case R.id.lay_learntest_error:
                PracticeActivity.start(getContext());
                break;
            case R.id.lay_learntest_model:
                ModelActivity.start(getContext());
                break;
            case R.id.lay_learntest_official:
                OfficialActivity.start(getContext());
                break;
        }
    }
}
