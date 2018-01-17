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
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.interfaces.PagerInter;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.ValiHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class EmailValiFragment extends BaseFragment implements View.OnClickListener {

    private ValiHelper valiHelper;
    private int position;
    private View rootView;
    private View btn_go;
    private TextView btn_vali;
    private EditText edit_vali_email;
    private EditText edit_vali_code;

    public static Fragment newInstance(int position) {
        EmailValiFragment fragment = new EmailValiFragment();
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
        rootView = inflater.inflate(R.layout.fragment_email_vali, container, false);
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
        btn_go = rootView.findViewById(R.id.btn_go);
        btn_vali = (TextView) rootView.findViewById(R.id.btn_vali);
        edit_vali_email = (EditText) rootView.findViewById(R.id.edit_vali_email);
        edit_vali_code = (EditText) rootView.findViewById(R.id.edit_vali_code);
        valiHelper = new ValiHelper(btn_vali);
        btn_vali.setOnClickListener(this);
        btn_go.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vali: {
                String emali = edit_vali_email.getText().toString();
                String msg = AppVali.email(emali);
                if (msg == null) {
                    netSendMessageEmail(emali);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
            }
            case R.id.btn_go: {
                String email = edit_vali_email.getText().toString();
                String code = edit_vali_code.getText().toString();
                String email_old = valiHelper.phone;

                String msg = AppVali.vali_email(email, email_old);
                if (msg == null) {
                    netUpdateEmail(email, code);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
            }
        }
    }

    public void next() {
        if (getActivity() instanceof PagerInter) {
            ((PagerInter) getActivity()).next();
        }
    }

    private void netSendMessageEmail(final String email) {
        Map<String, Object> param = new NetParam()
                .put("email", email)
                .put("flag", AppHelper.UserHelp.hasEmail() ? 1 : 0)//0:绑定邮箱 1:换绑
                .build();
        NetApi.NI().sendMessageEmail(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                valiHelper.phone = email;
                valiHelper.start();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    private void netUpdateEmail(final String email, final String code) {
        showLoadingDialog();
        Map<String, Object> param = new NetParam()
                .put("email", email)
                .put("code", code)
                .build();
        NetApi.NI().updateEmail(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                hideLoadingDialog();
                ToastUtil.showToastShort(msg);
                User user = AppData.App.getUser();
                user.setEmail(email);
                AppData.App.saveUser(user);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_VALI_EMAIL).put("email", email));
                next();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }

    private void showLoadingDialog() {
//        if (getActivity() instanceof BaseAppCompatActivity){
//            ((BaseAppCompatActivity) getActivity()).showLoadingDialog();
//        }
        btn_go.setEnabled(false);
    }

    private void hideLoadingDialog() {
//        if (getActivity() instanceof BaseAppCompatActivity){
//            ((BaseAppCompatActivity) getActivity()).hideLoadingDialog();
//        }
        btn_go.setEnabled(true);
    }
}
