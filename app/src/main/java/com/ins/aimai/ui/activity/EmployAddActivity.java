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

public class EmployAddActivity extends BaseAppCompatActivity implements View.OnClickListener, CropHelper.CropInterface {

    private CropHelperEx cropHelperEx;

    private ImageView img_employadd_header;

    public static void start(Context context) {
        Intent intent = new Intent(context, EmployAddActivity.class);
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
        cropHelperEx = new CropHelperEx(this, this);
        cropHelperEx.setNeedCrop(true);
    }

    private void initView() {
        img_employadd_header = (ImageView) findViewById(R.id.img_employadd_header);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.lay_employadd_header).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                ToastUtil.showToastShort("添加成功", true);
                break;
            case R.id.lay_employadd_header:
                cropHelperEx.showDefaultDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelperEx.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void cropResult(String path) {
        GlideUtil.loadCircleImg(img_employadd_header, R.drawable.default_bk_img, path);
    }

    @Override
    public void cancel() {
        //取消相机或相册
    }
}
