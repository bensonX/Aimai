package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;

public class DefinitionActivity extends BaseAppCompatActivity{

    public static void start(Context context) {
        Intent intent = new Intent(context, DefinitionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
    }
}
