package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;

public class CompInfoActivity extends BaseAppCompatActivity {

    private ImageView img_compinfo_yyzz;
    private TextView text_compinfo_name;
    private TextView text_compinfo_yyzz;
    private TextView text_compinfo_trade;
    private TextView text_compinfo_address;

    private User comp;

    public static void start(Context context, User comp) {
        Intent intent = new Intent(context, CompInfoActivity.class);
        intent.putExtra("comp", comp);
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
        if (getIntent().hasExtra("comp")) {
            comp = (User) getIntent().getSerializableExtra("comp");
        }
    }

    private void initView() {
        img_compinfo_yyzz = (ImageView) findViewById(R.id.img_compinfo_yyzz);
        text_compinfo_name = (TextView) findViewById(R.id.text_compinfo_name);
        text_compinfo_yyzz = (TextView) findViewById(R.id.text_compinfo_yyzz);
        text_compinfo_trade = (TextView) findViewById(R.id.text_compinfo_trade);
        text_compinfo_address = (TextView) findViewById(R.id.text_compinfo_address);
    }

    private void initCtrl() {
    }

    private void initData() {
        setData(comp);
    }

    private void setData(User comp) {
        if (comp != null) {
            GlideUtil.loadImg(img_compinfo_yyzz, R.drawable.default_bk_img, comp.getLicenseFile());
            text_compinfo_name.setText(comp.getShowName());
            text_compinfo_yyzz.setText(comp.getPid());
            text_compinfo_trade.setText(comp.getTradeName());
            text_compinfo_address.setText(comp.getCity().getMergerName());
        }
    }
}
