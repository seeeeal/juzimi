package com.example.dabutaizha.lines.mvp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/26 下午4:24.
 */

public class UpdateDialog extends Dialog {

    @BindView(R.id.dialog_update_message)
    public TextView mUpdateMessageTv;
    @BindView(R.id.dialog_update_download)
    public TextView mWebDownlodadTv;
    
    private Context mContext;

    private String mLocalVersion;
    private String mRecentVersion;
    private String mAPKSize;

    public UpdateDialog(@NonNull Context context, int themeResId, Bundle bundle) {
        super(context, themeResId);
        mContext = context;

        mLocalVersion = bundle.getString(Constant.UPDATE_LOCAL_VERSION, "0.0.0");
        mRecentVersion = bundle.getString(Constant.UPDATE_RECENT_VERSION, "0.0.0");
        mAPKSize = bundle.getString(Constant.UPDATE_APK_SIZE, "0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        ButterKnife.bind(this);

        initView();
        initViewListener();
    }

    private void initView() {
        mUpdateMessageTv.setText(String.format("当前版本: %s\n最新版本: %s\n安装包大小: %s",
                mLocalVersion, mRecentVersion, mAPKSize));
    }

    private void initViewListener() {
        mWebDownlodadTv.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri url = Uri.parse(Constant.DOWNLOAD_URL);
            intent.setData(url);
            mContext.startActivity(intent);
        });
    }

}
