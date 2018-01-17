package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
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
public class EmailValiResultFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;
    private View btn_go;
    private TextView text_bindemailresult_email;

    public static Fragment newInstance(int position) {
        EmailValiResultFragment fragment = new EmailValiResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_VALI_EMAIL:
                setData();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_email_vali_result, container, false);
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
        text_bindemailresult_email = (TextView) rootView.findViewById(R.id.text_bindemailresult_email);
        btn_go.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
        setData();
    }

    private void setData() {
        User user = AppData.App.getUser();
        if (user != null && !TextUtils.isEmpty(user.getEmail())) {
            text_bindemailresult_email.setText(user.getEmail());
        } else {
            text_bindemailresult_email.setText("您还没有邮箱");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                if (getActivity() instanceof PagerInter) {
                    ((PagerInter) getActivity()).last();
                }
                break;
        }
    }
}
