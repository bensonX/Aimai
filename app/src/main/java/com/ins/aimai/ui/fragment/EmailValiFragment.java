package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.interfaces.PagerFragmentInter;
import com.ins.aimai.interfaces.PagerInter;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.ForgetPswActivity;
import com.ins.aimai.ui.activity.RegistActivity;
import com.ins.aimai.ui.activity.WebActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.SpannableStringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by liaoinstan
 */
public class EmailValiFragment extends BaseFragment implements View.OnClickListener, PagerFragmentInter {

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
            case R.id.btn_vali:
                String emali = edit_vali_email.getText().toString();
                String msg = AppVali.email(emali);
                if (msg == null) {
                    netSendMessage(emali);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
            case R.id.btn_go:
                if (getActivity() instanceof PagerInter) {
                    ((PagerInter) getActivity()).next();
                }
//                next();
                break;
        }
    }

    //页面进行下一步后会回调该方法，返回true，继续跳转，否则拦截
    public boolean next() {
        String email = edit_vali_email.getText().toString();
        String code = edit_vali_code.getText().toString();
        String email_old = valiHelper.phone;
        String code_old = valiHelper.valicode;

        String msg = AppVali.vali_email(email, email_old, code, code_old);
        if (msg == null) {
            EventBus.getDefault().post(new EventBean(EventBean.EVENT_VALI_EMAIL).put("email", email_old));
            if (getActivity() instanceof PagerInter) {
                ((PagerInter) getActivity()).next();
            }
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
        Call<ResponseBody> call;
        if (getActivity() instanceof RegistActivity) {
            call = NetApi.NI().sendMessageRegist(param);
        } else if (getActivity() instanceof ForgetPswActivity) {
            call = NetApi.NI().sendMessageForget(param);
        } else {
            call = NetApi.NI().sendMessage(param);
        }
        call.enqueue(new BaseCallback<String>(String.class) {
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
