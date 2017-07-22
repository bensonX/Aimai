package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ins.aimai.R;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import com.ins.sharesdk.dialog.ShareDialog;
import com.ins.sharesdk.dialog.ShareHelper;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

public class WebActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private ProgressBar progressBar;

    private String url;
    private String title;

    public static void start(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
            setToolbar(title);
        }
        if (getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
        } else {
            url = "http://www.baidu.com";
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        findViewById(R.id.btn_right_share).setOnClickListener(this);
        findViewById(R.id.btn_right_favo).setOnClickListener(this);
    }

    private void initCtrl() {
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right_favo:
                ToastUtil.showToastShort("已收藏");
                break;
            case R.id.btn_right_share:
                new ShareDialog(this).show();
                break;
        }
    }
}
