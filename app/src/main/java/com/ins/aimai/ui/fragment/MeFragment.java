package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.ui.activity.FavoActivity;
import com.ins.aimai.ui.activity.LoginActivity;
import com.ins.aimai.ui.activity.MeDetailActivity;
import com.ins.aimai.ui.activity.MsgActivity;
import com.ins.aimai.ui.activity.OrderActivity;
import com.ins.aimai.ui.activity.SettingActivity;
import com.ins.aimai.ui.activity.SuggestActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.utils.GlideUtil;

/**
 * Created by liaoinstan
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;

    private View btn_right;
    private ImageView img_me_header;
    private TextView text_me_name;
    private TextView text_me_order;
    private TextView text_me_msg;
    private TextView text_me_favo;
    private TextView text_me_grade;
    private TextView text_me_safe;
    private TextView text_me_suggest;
    private TextView text_me_setting;

    public static Fragment newInstance(int position) {
        MeFragment fragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LOGIN) {
            setUserData();
        } else if (event.getEvent() == EventBean.EVENT_USER_UPDATE) {
            setUserData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
        toolbar.bringToFront();
        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        img_me_header = (ImageView) rootView.findViewById(R.id.img_me_header);
        text_me_name = (TextView) rootView.findViewById(R.id.text_me_name);
        text_me_grade = (TextView) rootView.findViewById(R.id.text_me_grade);

        rootView.findViewById(R.id.btn_right).setOnClickListener(this);
        rootView.findViewById(R.id.lay_me_header).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_order).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_msg).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_favo).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_grade).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_safe).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_suggest).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_setting).setOnClickListener(this);
    }

    private void initData() {
    }

    private void initCtrl() {
        setUserData();
    }

    private void setUserData() {
        User user = AppData.App.getUser();
        if (user != null) {
            GlideUtil.loadCircleImg(img_me_header, R.drawable.default_header, user.getAvatar());
            text_me_name.setText(user.getShowName());
            text_me_grade.setText(user.getAccumulate() + "积分");
            text_me_grade.setVisibility(View.VISIBLE);
        } else {
            img_me_header.setImageResource(R.drawable.default_header);
            text_me_name.setText("登录");
            text_me_grade.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                if (AppData.App.getUser() != null) {
                    MeDetailActivity.start(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.lay_me_header:
                if (AppData.App.getUser() != null) {
                    MeDetailActivity.start(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.text_me_order:
                OrderActivity.start(getContext());
                break;
            case R.id.text_me_msg:
                MsgActivity.start(getContext());
                break;
            case R.id.text_me_favo:
                FavoActivity.start(getContext());
                break;
            case R.id.text_me_grade:
                break;
            case R.id.text_me_safe:
                break;
            case R.id.text_me_suggest:
                SuggestActivity.start(getContext());
                break;
            case R.id.text_me_setting:
                SettingActivity.start(getContext());
                break;
        }
    }
}
