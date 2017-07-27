package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.EditTextUtil;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class PayDialogCountFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;
    private PayDialogActivity activity;

    private View btn_paydialog_sub;
    private View btn_paydialog_add;
    private EditText edit_paydialog_count;

    public static Fragment newInstance(int position) {
        PayDialogCountFragment fragment = new PayDialogCountFragment();
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
        rootView = inflater.inflate(R.layout.fragment_paydialogcount, container, false);
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
    }

    private void initView() {
        edit_paydialog_count = (EditText) rootView.findViewById(R.id.edit_paydialog_count);
        btn_paydialog_sub = rootView.findViewById(R.id.btn_paydialog_sub);
        btn_paydialog_add = rootView.findViewById(R.id.btn_paydialog_add);
        rootView.findViewById(R.id.btn_go).setOnClickListener(this);
        btn_paydialog_sub.setOnClickListener(this);
        btn_paydialog_add.setOnClickListener(this);
    }

    private void initCtrl() {
        edit_paydialog_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity.setCount(StrUtil.str2int(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        User user = AppData.App.getUser();
        if (user.getRoleId() == User.USER) {
            edit_paydialog_count.setText("1");
            EditTextUtil.disableEditText(edit_paydialog_count);
        }
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        User user = AppData.App.getUser();
        switch (v.getId()) {
            case R.id.btn_paydialog_sub: {
                if (user.getRoleId() == User.USER) {
                    ToastUtil.showToastShort("个人用户同一商品只能购买一件");
                    return;
                }
                int edit = StrUtil.str2int(edit_paydialog_count.getText().toString());
                if (edit == 0 || edit == 1) {
                    edit_paydialog_count.setText("");
                } else {
                    edit_paydialog_count.setText(edit - 1 + "");
                }
                activity.setCount(StrUtil.str2int(edit_paydialog_count.getText().toString()));
                break;
            }
            case R.id.btn_paydialog_add: {
                if (user.getRoleId() == User.USER) {
                    ToastUtil.showToastShort("个人用户只能购买一件商品");
                    return;
                }
                int edit = StrUtil.str2int(edit_paydialog_count.getText().toString());
                edit_paydialog_count.setText(edit + 1 + "");
                activity.setCount(StrUtil.str2int(edit_paydialog_count.getText().toString()));
                break;
            }
            case R.id.btn_go:
                if (activity.getOrderId() != 0) {
                    //已经生成订单，直接跳转
                    activity.next();
                } else {
                    //还未生成订单，则生成订单后跳转
                    String msg = AppVali.addOrderCount(activity.getLessonId(), activity.getCount());
                    if (msg != null) {
                        ToastUtil.showToastShort(msg);
                    } else {
                        netAddOrder();
                    }
                }
                break;
        }
    }

    private void netAddOrder() {
        Map<String, Object> param = new NetParam()
                .put("curriculumId", activity.getLessonId())
                .put("number", activity.getCount())
                .build();
        NetApi.NI().addOrder(param).enqueue(new BaseCallback<Integer>(Integer.class) {
            @Override
            public void onSuccess(int status, Integer orderId, String msg) {
                //保存orderId
                activity.setOrderId(orderId);
                activity.next();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
