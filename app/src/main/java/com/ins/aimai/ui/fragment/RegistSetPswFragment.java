package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.ui.activity.RegistActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liaoinstan
 */
public class RegistSetPswFragment extends BaseFragment implements PagerFragmentInter {

    private int position;
    private View rootView;

    private EditText edit_regist_newpsw;
    private EditText edit_regist_newpsw_repeat;

    private String phone;

    private RegistActivity activity;

    public static Fragment newInstance(int position) {
        RegistSetPswFragment fragment = new RegistSetPswFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_REGIST_PHONE) {
            phone = (String)event.get("phone");
            ToastUtil.showToastShortDebug(phone);
            EventBus.getDefault().cancelEventDelivery(event);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_regist_setpsw, container, false);
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
        activity = (RegistActivity) getActivity();
    }

    private void initView() {
        edit_regist_newpsw = (EditText) rootView.findViewById(R.id.edit_regist_newpsw);
        edit_regist_newpsw_repeat = (EditText) rootView.findViewById(R.id.edit_regist_newpsw_repeat);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public boolean next() {
        String psw = edit_regist_newpsw.getText().toString();
        String psw_repeat = edit_regist_newpsw_repeat.getText().toString();
        String msg = AppVali.regist_psw(psw, psw_repeat);
        if (msg == null) {
            User register = activity.getRegister();
            register.setPhone(phone);
            register.setPwd(psw);
            return true;
        } else {
            ToastUtil.showToastShort(msg);
            return false;
        }
    }
}
