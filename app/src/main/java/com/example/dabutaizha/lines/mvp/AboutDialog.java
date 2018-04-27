package com.example.dabutaizha.lines.mvp;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/7 上午9:35.
 */

public class AboutDialog extends Dialog {

    @BindView(R.id.about_me)
    public TextView mAboutMe;
    @BindView(R.id.about_app_version)
    public TextView mAppVersion;

    private Context mContext;

    public AboutDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about);
        ButterKnife.bind(this);

        initView();
        initViewListener();
    }

    private void initView() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            String appVersion = info.versionName;
            mAppVersion.setText(String.format("version: %s", appVersion));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initViewListener() {
        mAboutMe.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.WEBVIEW_URL, ResUtil.getString(R.string.web_about));
            WebViewActivity.startActivity(mContext, bundle);
            AboutDialog.this.dismiss();
        });
    }
}
