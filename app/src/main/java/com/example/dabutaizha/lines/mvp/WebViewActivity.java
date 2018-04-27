package com.example.dabutaizha.lines.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/5 下午5:32.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.web_view)
    public WebView mWebView;

    private String mUrl;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    // 清除缓存，在注册页面防止因为cookie自动登录
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearCache(true);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(Constant.WEBVIEW_URL, "");
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mUrl);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    protected void initViewListener() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void process() {
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_webview;
    }

}
