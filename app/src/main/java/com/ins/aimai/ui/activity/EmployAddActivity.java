package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.Map;

public class EmployAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ImageView img_employadd_header;
    private TextView text_employadd_name;
    private EditText edit_employadd_department;
    private EditText edit_employadd_job;

    private View btn_right;

    private User user;

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, EmployAddActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employadd);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        }
    }

    private void initView() {
        img_employadd_header = (ImageView) findViewById(R.id.img_employadd_header);
        text_employadd_name = (TextView) findViewById(R.id.text_employadd_name);
        edit_employadd_department = (EditText) findViewById(R.id.edit_employadd_department);
        edit_employadd_job = (EditText) findViewById(R.id.edit_employadd_job);
        btn_right = findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_right.setEnabled(false);
    }

    private void initCtrl() {
        setData(user);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (AppVali.addEmployee(user, edit_employadd_department.getText().toString(), edit_employadd_job.getText().toString()) == null) {
                    btn_right.setEnabled(true);
                } else {
                    btn_right.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edit_employadd_department.addTextChangedListener(watcher);
        edit_employadd_job.addTextChangedListener(watcher);
    }

    private void initData() {
    }

    private void setData(User user) {
        if (user != null) {
            GlideUtil.loadCircleImg(img_employadd_header, R.drawable.default_header, user.getAvatar());
            text_employadd_name.setText(user.getShowName());
            edit_employadd_department.setText(user.getDepartmentName());
            edit_employadd_job.setText(user.getJobTitle());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                String department = edit_employadd_department.getText().toString();
                String job = edit_employadd_job.getText().toString();
                netAddEmployee(department, job);
                break;
        }
    }

    private void netAddEmployee(String departmentName, String jobTitle) {
        Map<String, Object> param = new NetParam()
                .put("userId", user.getId())
                .put("departmentName", departmentName)
                .put("jobTitle", jobTitle)
                .build();
        showLoadingDialog();
        NetApi.NI().addEmployee(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort("添加成功", true);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_EMPLOY_ADD));
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
