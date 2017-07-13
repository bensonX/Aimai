package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.ui.activity.ForgetPswActivity;
import com.ins.aimai.ui.base.BaseFragment;

/**
 * Created by liaoinstan
 */
public class ForgetPswSecondFragment extends BaseFragment implements View.OnClickListener{

    private int position;
    private View rootView;
    private ForgetPswActivity activity;

    public static Fragment newInstance(int position) {
        ForgetPswSecondFragment fragment = new ForgetPswSecondFragment();
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
        rootView = inflater.inflate(R.layout.fragment_forgetpsw_second, container, false);
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
        activity = (ForgetPswActivity) getActivity();
    }

    private void initView() {
//        rootView.findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go:
                break;
        }
    }
}
