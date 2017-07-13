package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.ui.activity.ForgetPswActivity;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.helper.ValiHelper;

/**
 * Created by liaoinstan
 */
public class ForgetPswFirstFragment extends BaseFragment implements View.OnClickListener {

    private ValiHelper valiHelper;
    private int position;
    private View rootView;
    private TextView btn_vali;

    public static Fragment newInstance(int position) {
        ForgetPswFirstFragment fragment = new ForgetPswFirstFragment();
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
        rootView = inflater.inflate(R.layout.fragment_forgetpsw_first, container, false);
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
        btn_vali = (TextView) rootView.findViewById(R.id.btn_vali);
        valiHelper = new ValiHelper(btn_vali);
        btn_vali.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vali:
                valiHelper.start();
                break;
        }
    }
}
