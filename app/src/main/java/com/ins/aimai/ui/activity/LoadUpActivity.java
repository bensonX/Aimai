package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;

public class LoadUpActivity extends BaseAppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoadUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
