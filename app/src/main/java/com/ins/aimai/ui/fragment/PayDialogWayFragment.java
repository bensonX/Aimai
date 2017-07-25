package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;

import paytest.ins.com.helper.PayHelper;

import com.ins.aimai.common.PayHelperEx;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class PayDialogWayFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;
    private PayDialogActivity activity;
    private PayHelperEx payHelperEx;

    public static Fragment newInstance(int position) {
        PayDialogWayFragment fragment = new PayDialogWayFragment();
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
        rootView = inflater.inflate(R.layout.fragment_paydialogway, container, false);
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
        activity = (PayDialogActivity) getActivity();
        payHelperEx = new PayHelperEx(getActivity());
    }

    private void initView() {
        rootView.findViewById(R.id.btn_wx).setOnClickListener(this);
        rootView.findViewById(R.id.btn_zfb).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wx:
                payHelperEx.startPayWeixin(activity.getOrderId());
                break;
            case R.id.btn_zfb:
                payHelperEx.startPayZhifubao(activity.getOrderId());
                break;
        }
    }
}
