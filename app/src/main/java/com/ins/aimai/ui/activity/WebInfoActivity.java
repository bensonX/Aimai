package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetFavoHelper;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.sharesdk.dialog.ShareDialog;

public class WebInfoActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private ProgressBar progressBar;

    private int id;
    private String title;
    private String digest;  //摘要（用于分享）
    private String img;  //图片（用于分享）
    private String url;

    public static void start(Context context, Info info) {
        Intent intent = new Intent(context, WebInfoActivity.class);
        intent.putExtra("id", info.getId());
        intent.putExtra("title", info.getTitle());
        intent.putExtra("digest", info.getDigest());
        intent.putExtra("img", info.getImage());
        intent.putExtra("url", NetApi.getBaseUrl() + AppData.Url.newsInfo + "?newsId=" + info.getId());
        context.startActivity(intent);
    }

    public static void start(Context context, Image img) {
        Intent intent = new Intent(context, WebInfoActivity.class);
        intent.putExtra("id", img.getId());
        intent.putExtra("title", img.getTitle());
        intent.putExtra("digest", img.getTitle());
        intent.putExtra("img", img.getImg());
        intent.putExtra("url", NetApi.getBaseUrl() + AppData.Url.bannerInfo + "?bannerId=" + img.getId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webinfo);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", 0);
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
            setToolbar(title);
        }
        if (getIntent().hasExtra("digest")) {
            digest = getIntent().getStringExtra("digest");
        }
        if (getIntent().hasExtra("img")) {
            img = getIntent().getStringExtra("img");
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

        ///
        setting.setAllowFileAccess(true);
        setting.setAllowFileAccessFromFileURLs(true);
        setting.setAllowUniversalAccessFromFileURLs(true);
        setting.setAppCacheEnabled(true);
        setting.setDatabaseEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setting.setAllowFileAccessFromFileURLs(true);
        }
        ///

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
                NetFavoHelper.getInstance().netAddCollect(id, 0);
                break;
            case R.id.btn_right_share:
                new ShareDialog(this)
                        .setShareData(title, digest, url, GlideUtil.getRealImgPath(img))
                        .show();
                break;
        }
    }
}
