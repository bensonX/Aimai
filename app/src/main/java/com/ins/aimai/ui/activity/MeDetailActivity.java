package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetUploadHelper;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.ui.dialog.DialogPopupPhoto;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.PermissionsUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class MeDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelper cropHelper;

    private EditText edit_medetail_name;
    private EditText edit_medetail_introduce;
    private ImageView img_medetail_header;
    private TextView text_medetail_phone;
    private TextView text_medetail_address;
    private TextView text_medetail_trade;
    private TextView text_medetail_comp;
    private TextView text_medetail_department;
    private TextView text_medetail_job;
    private TextView text_medetail_identid;

    private DialogPopupPhoto popupPhoto;

    private Address address;
    private String path;

    public static void start(Context context) {
        if (AppData.App.getUser() != null) {
            Intent intent = new Intent(context, MeDetailActivity.class);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_SELECT_ADDRESS) {
            address = (Address) event.get("address");
            setAddressData(address);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medetail);
        setToolbar();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        cropHelper = new CropHelper(this, this);
        cropHelper.setNeedCrop(false);
        popupPhoto = new DialogPopupPhoto(this);
        popupPhoto.setOnStartListener(new DialogPopupPhoto.OnStartListener() {
            @Override
            public void onPhoneClick(View v) {
                cropHelper.startPhoto();
            }

            @Override
            public void onCameraClick(View v) {
                cropHelper.startCamera();
            }
        });
    }

    private void initView() {
        edit_medetail_name = (EditText) findViewById(R.id.edit_medetail_name);
        edit_medetail_introduce = (EditText) findViewById(R.id.edit_medetail_introduce);
        img_medetail_header = (ImageView) findViewById(R.id.img_medetail_header);
        text_medetail_phone = (TextView) findViewById(R.id.text_medetail_phone);
        text_medetail_address = (TextView) findViewById(R.id.text_medetail_address);
        text_medetail_trade = (TextView) findViewById(R.id.text_medetail_trade);
        text_medetail_comp = (TextView) findViewById(R.id.text_medetail_comp);
        text_medetail_department = (TextView) findViewById(R.id.text_medetail_department);
        text_medetail_job = (TextView) findViewById(R.id.text_medetail_job);
        text_medetail_identid = (TextView) findViewById(R.id.text_medetail_identid);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.lay_medetail_header).setOnClickListener(this);
        findViewById(R.id.lay_medetail_address).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
        setUserData();
    }

    private void setUserData() {
        User user = AppData.App.getUser();
        if (user != null) {
            GlideUtil.loadCircleImg(img_medetail_header, R.drawable.default_header, user.getAvatar());
            edit_medetail_name.setText(user.getShowName());
            edit_medetail_introduce.setText(user.getIntroduce());
            text_medetail_phone.setText(user.getPhone());
            text_medetail_address.setText(user.getCity() != null ? user.getCity().getMergerName() : "");
            text_medetail_trade.setText(user.getTradeName());
            text_medetail_comp.setText(user.getCompanyName());
            text_medetail_department.setText(user.getDepartmentName());
            text_medetail_job.setText(user.getJobTitle());
            text_medetail_identid.setText(user.getPid());
        } else {
        }
    }

    private void setAddressData(Address address) {
        text_medetail_address.setText(address.getMergerName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                final String name = edit_medetail_name.getText().toString();
                final String introduce = edit_medetail_introduce.getText().toString();
                String msg = AppVali.updateUser(AppData.App.getUser(), name, introduce, path, address);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    uploadAndCommit(name, introduce);
                }
                break;
            case R.id.lay_medetail_header:
                if (PermissionsUtil.checkCamera(this)) {
                    popupPhoto.show();
                }
                break;
            case R.id.lay_medetail_address:
                AddressActivity.start(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void cropResult(String path) {
        this.path = path;
        GlideUtil.loadCircleImg(img_medetail_header, R.drawable.default_header_edit, path);
    }

    @Override
    public void cancel() {
        //取消相机或相册
    }

    private void uploadAndCommit(final String showName, final String introduce) {
        if (!TextUtils.isEmpty(path)) {
            NetUploadHelper.newInstance().netUpload(path, new NetUploadHelper.UploadCallback() {
                @Override
                public void uploadfinish(String url) {
                    netCommit(showName, introduce, url);
                }
            });
        } else {
            netCommit(showName, introduce, null);
        }
    }

    private void netCommit(final String showName, final String introduce, final String avatar) {
        NetParam netParam = new NetParam();
        netParam.put("introduce", introduce);
        if (!TextUtils.isEmpty(showName)) netParam.put("showName", showName);
        if (!TextUtils.isEmpty(avatar)) netParam.put("avatar", avatar);
        if (address != null) netParam.put("cityId", address.getId());
        Map<String, Object> param = netParam.build();
        showLoadingDialog();
        NetApi.NI().updateUser(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean bean, String msg) {
                ToastUtil.showToastShort(msg);
                User user = AppData.App.getUser();
                user.setIntroduce(introduce);
                if (!TextUtils.isEmpty(avatar)) user.setAvatar(avatar);
                if (!TextUtils.isEmpty(showName)) user.setShowName(showName);
                if (address != null) {
                    user.setCity(address);
                    user.setCityId(address.getId());
                }
                AppData.App.saveUser(user);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_USER_UPDATE));
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
