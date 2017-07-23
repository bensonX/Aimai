package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.ValiHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class PhoneValiFragment extends BaseFragment implements View.OnClickListener, PagerFragmentInter {

    private ValiHelper valiHelper;
    private int position;
    private View rootView;
    private TextView btn_vali;
    private EditText edit_vali_phone;
    private EditText edit_vali_code;

    public static Fragment newInstance(int position) {
        PhoneValiFragment fragment = new PhoneValiFragment();
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
        rootView = inflater.inflate(R.layout.fragment_phone_vali, container, false);
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
        edit_vali_phone = (EditText) rootView.findViewById(R.id.edit_vali_phone);
        edit_vali_code = (EditText) rootView.findViewById(R.id.edit_vali_code);
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
                String phone = edit_vali_phone.getText().toString();
                String msg = AppVali.phone(phone);
                if (msg == null) {
                    netSendMessage(phone);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
        }
    }

    //页面进行下一步后会回调该方法，返回true，继续跳转，否则拦截
    public boolean next() {
        String phone = edit_vali_phone.getText().toString();
        String code = edit_vali_code.getText().toString();
        String phone_old = valiHelper.phone;
        String code_old = valiHelper.valicode;

        String msg = AppVali.regist_phone(phone, phone_old, code, code_old);
        if (msg == null) {
            EventBus.getDefault().post(new EventBean(EventBean.EVENT_REGIST_PHONE).put("phone", phone_old));
            return true;
        } else {
            ToastUtil.showToastShort(msg);
            return false;
        }
    }

    private void netSendMessage(final String phone) {
        Map<String, Object> param = new NetParam()
                .put("phone", phone)
                .build();
        NetApi.NI().sendMessage(param).enqueue(new BaseCallback<String>(String.class) {
            @Override
            public void onSuccess(int status, String bean, String msg) {
                valiHelper.phone = phone;
                valiHelper.valicode = bean;
                valiHelper.start();
                ToastUtil.showToastShortDebug(bean);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
