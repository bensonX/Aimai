package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.ForgetPswActivity;
import com.ins.aimai.ui.activity.LoginActivity;
import com.ins.aimai.ui.activity.ModifyPswActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.MD5Util;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class ForgetPswSecondFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;
    private String phone;

    private EditText edit_modifypsw_newpsw;
    private EditText edit_modifypsw_newpsw_repeat;

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
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_REGIST_PHONE) {
            phone = (String) event.get("phone");
            EventBus.getDefault().cancelEventDelivery(event);
        }
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
    }

    private void initView() {
        edit_modifypsw_newpsw = (EditText) rootView.findViewById(R.id.edit_modifypsw_newpsw);
        edit_modifypsw_newpsw_repeat = (EditText) rootView.findViewById(R.id.edit_modifypsw_newpsw_repeat);
        rootView.findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                String newPsw = edit_modifypsw_newpsw.getText().toString();
                String newPsw_repeat = edit_modifypsw_newpsw_repeat.getText().toString();
                String msg = AppVali.forgetPsw(newPsw, newPsw_repeat);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    netSetPsw(newPsw);
                }
                break;
        }
    }

    private void netSetPsw(String newPwd) {
        Map<String, Object> param = new NetParam()
                .put("phone", phone)
                .put("newPwd", MD5Util.md5(newPwd))
                .build();
        AppHelper.showLoadingDialog(getActivity());
        NetApi.NI().setPwd(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg);
                AppData.App.removeToken();
                AppData.App.removeUser();
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGOUT));
                getActivity().finish();
                LoginActivity.start(getActivity());
                AppHelper.hideLoadingDialog(getActivity());
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                AppHelper.hideLoadingDialog(getActivity());
            }
        });
    }
}
