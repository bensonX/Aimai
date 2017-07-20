package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;

public class CompInfoActivity extends BaseAppCompatActivity{

    private ImageView img_compinfo_yyzz;

    public static void start(Context context) {
        Intent intent = new Intent(context, CompInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compinfo);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        img_compinfo_yyzz = (ImageView) findViewById(R.id.img_compinfo_yyzz);
    }

    private void initCtrl() {
        GlideUtil.loadImgTest(img_compinfo_yyzz);
    }

    private void initData() {
    }
}
