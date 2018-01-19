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
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogIdentify;
import com.ins.aimai.ui.fragment.InfoFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.App;
import com.ins.common.utils.MD5Util;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ValiHelper valiHelper;

    private DialogIdentify dialogIdentify;
    private EditText edit_login_name;
    private EditText edit_login_psw;
    private EditText edit_login_code;
    private View img_login_name_del;
    private View img_login_psw_del;
    private TextView btn_vali;

    public static void start() {
        Intent intent = new Intent(App.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        dialogIdentify = new DialogIdentify(this);
        dialogIdentify.setOnIdentifyListener(new DialogIdentify.OnIdentifyListener() {
            @Override
            public void onPersonClick(View v) {
                RegistActivity.start(LoginActivity.this, 0);
            }

            @Override
            public void onCompClick(View v) {
                RegistActivity.start(LoginActivity.this, 1);
            }

            @Override
            public void onGovClick(View v) {
                RegistActivity.start(LoginActivity.this, 2);
            }
        });
    }

    private void initView() {
        edit_login_name = (EditText) findViewById(R.id.edit_login_name);
        edit_login_psw = (EditText) findViewById(R.id.edit_login_psw);
        edit_login_code = (EditText) findViewById(R.id.edit_login_code);
        img_login_name_del = findViewById(R.id.img_login_name_del);
        img_login_psw_del = findViewById(R.id.img_login_psw_del);
        btn_vali = findViewById(R.id.btn_vali);
        findViewById(R.id.img_login_name_del).setOnClickListener(this);
        findViewById(R.id.img_login_psw_del).setOnClickListener(this);
        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_vali).setOnClickListener(this);
        findViewById(R.id.btn_login_forgetpsw).setOnClickListener(this);
        findViewById(R.id.btn_login_regist).setOnClickListener(this);
        img_login_name_del.setVisibility(View.INVISIBLE);
        img_login_psw_del.setVisibility(View.INVISIBLE);
        valiHelper = new ValiHelper(btn_vali);
    }

    private void initCtrl() {
        edit_login_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                img_login_name_del.setVisibility(!StrUtil.isEmpty(s.toString()) ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edit_login_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                img_login_psw_del.setVisibility(!StrUtil.isEmpty(s.toString()) ? View.VISIBLE : View.INVISIBLE);
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
            case R.id.btn_go: {
                String name = edit_login_name.getText().toString();
                String psw = edit_login_psw.getText().toString();
                String code = edit_login_code.getText().toString();
                String msg = AppVali.login(name, psw, code);
                if (msg == null) {
                    netLogin(name, psw, code);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
            }
            case R.id.btn_login_forgetpsw:
                ForgetPswActivity.start(this);
                break;
            case R.id.btn_login_regist:
                dialogIdentify.show();
                break;
            case R.id.img_login_name_del:
                edit_login_name.setText("");
                break;
            case R.id.img_login_psw_del:
                edit_login_psw.setText("");
                break;
            case R.id.btn_vali:
                String phone = edit_login_name.getText().toString();
                String msg = AppVali.phone(phone);
                if (msg == null) {
                    netSendMessageLogin(phone);
                } else {
                    ToastUtil.showToastShort(msg);
                }
                break;
        }
    }

    private void netLogin(String phone, String password, String code) {
        Map<String, Object> param = new NetParam()
                .put("phone", phone)
                .put("password", MD5Util.md5(password))
                .put("deviceType", 0)
                .put("deviceToken", JPushInterface.getRegistrationID(this))
                .put("isWechat", 0)
                .put("code", code)
                .build();
        showLoadingDialog();
        NetApi.NI().login(param).enqueue(new BaseCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                if (!AppHelper.LoginHelp.isNeedRecord(user)) {
                    AppData.App.saveUser(user);
                    AppData.App.saveToken(user.getToken());
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGIN));
                    hideLoadingDialog();
                    HomeActivity.start(LoginActivity.this);
                } else {
                    ToastUtil.showToastShort("您必须完善个人资料后才能登陆");
                    hideLoadingDialog();
                    FaceResordActivity.start(LoginActivity.this, user);
                }
                if (user.getIsException()==1){
                    ToastUtil.showToastShort("本次登录信息异常");
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }

    private void netSendMessageLogin(final String phone) {
        Map<String, Object> param = new NetParam()
                .put("phone", phone)
                .build();
        NetApi.NI().sendMessageLogin(param).enqueue(new BaseCallback<String>(String.class) {
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
