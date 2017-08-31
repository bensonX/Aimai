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
public class PhoneValiFragment extends BaseFragment implements View.OnClickListener, PagerFragmentInter {

    private ValiHelper valiHelper;
    private int position;
    private View rootView;
    private View btn_go;
    private TextView btn_vali;
    private EditText edit_vali_phone;
    private EditText edit_vali_code;
    private CheckBox check_vali_clause;

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
        btn_go = rootView.findViewById(R.id.btn_go);
        check_vali_clause = (CheckBox) rootView.findViewById(R.id.check_vali_clause);
        btn_vali = (TextView) rootView.findViewById(R.id.btn_vali);
        edit_vali_phone = (EditText) rootView.findViewById(R.id.edit_vali_phone);
        edit_vali_code = (EditText) rootView.findViewById(R.id.edit_vali_code);
        valiHelper = new ValiHelper(btn_vali);
        btn_vali.setOnClickListener(this);
        btn_go.setOnClickListener(this);
    }

    private void initCtrl() {
        SpannableString spannableString = SpannableStringUtil.create(getContext(), new String[]{"我已阅读并同意...", "《使用条款和隐私政策》"}, new int[]{R.color.com_text_dark, R.color.am_blue});
        SpannableStringUtil.makeClickStr(spannableString, "我已阅读并同意...".length(), spannableString.length(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(getActivity(), "使用条款和隐私政策", NetApi.getBaseUrl() + AppData.Url.clause);
            }
        });
        check_vali_clause.setMovementMethod(LinkMovementMethod.getInstance());
        check_vali_clause.setHighlightColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        check_vali_clause.setText(spannableString);

        //只有注册页面的号码验证有注册条款
        if (getActivity() instanceof RegistActivity) {
            check_vali_clause.setVisibility(View.VISIBLE);
        } else {
            check_vali_clause.setVisibility(View.GONE);
        }
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
            case R.id.btn_go:
                //如果是注册页面并且没有同意条款则提示
                if (!check_vali_clause.isChecked() && getActivity() instanceof RegistActivity) {
                    ToastUtil.showToastShort("请先阅读并同意《使用条款和隐私政策》");
                } else {
                    next();
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
                ToastUtil.showToastShort(bean);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
