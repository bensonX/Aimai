package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.helper.CropHelper;
import com.ins.common.ui.dialog.DialogPopupPhoto;
import com.ins.common.utils.GlideUtil;

public class MeDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelper cropHelper;

    private EditText edit_medetail_name;
    private ImageView img_medetail_header;
    private TextView text_medetail_phone;
    private TextView text_medetail_address;
    private TextView text_medetail_business;
    private TextView text_medetail_comp;
    private TextView text_medetail_department;
    private TextView text_medetail_job;
    private TextView text_medetail_identid;

    private DialogPopupPhoto popupPhoto;

    public static void start(Context context) {
        Intent intent = new Intent(context, MeDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medetail);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        cropHelper = new CropHelper(this, this);
        cropHelper.setNeedCrop(true);
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
        img_medetail_header = (ImageView) findViewById(R.id.img_medetail_header);
        text_medetail_phone = (TextView) findViewById(R.id.text_medetail_phone);
        text_medetail_address = (TextView) findViewById(R.id.text_medetail_address);
        text_medetail_business = (TextView) findViewById(R.id.text_medetail_business);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                break;
            case R.id.lay_medetail_header:
                popupPhoto.show();
                break;
            case R.id.lay_medetail_address:
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
        GlideUtil.loadCircleImg(img_medetail_header, R.drawable.default_bk_img, path);
    }

    @Override
    public void cancel() {
        //取消相机或相册
    }
}
