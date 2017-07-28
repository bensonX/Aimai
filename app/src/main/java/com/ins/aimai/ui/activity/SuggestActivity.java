package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;

import java.util.Map;

public class SuggestActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView btn_right;
    private EditText edit_suggest;


    public static void start(Context context) {
        if (AppData.App.getUser() != null) {
            Intent intent = new Intent(context, SuggestActivity.class);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        btn_right = (TextView) findViewById(R.id.btn_right);
        edit_suggest = (EditText) findViewById(R.id.edit_suggest);
        btn_right.setOnClickListener(this);
        btn_right.setEnabled(false);
    }

    private void initCtrl() {
        edit_suggest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (AppVali.content(s.toString()) == null) {
                    btn_right.setEnabled(true);
                } else {
                    btn_right.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                String content = edit_suggest.getText().toString();
                netCommit(content);
                break;
        }
    }

    private void netCommit(String content) {
        Map<String, Object> param = new NetParam()
                .put("content", content)
                .build();
        showLoadingDialog();
        NetApi.NI().suggest(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort("感谢您的建议");
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
