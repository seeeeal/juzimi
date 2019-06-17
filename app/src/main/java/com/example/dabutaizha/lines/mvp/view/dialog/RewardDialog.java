package com.example.dabutaizha.lines.mvp.view.dialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/23 下午11:10.
 */

public class RewardDialog extends Dialog {

    @BindView(R.id.dialog_reward_tv)
    public TextView mRewardTv;
    @BindView(R.id.dialog_reward_btn)
    public Button mRewardBtn;

    private Context mContext;

    public RewardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getCurrentThemeLayout());

        ButterKnife.bind(this);

        initData();
        initView();
        initViewListener();
    }

    private int getCurrentThemeLayout() {
        return (AppThemeUtils.getCurrentAppTheme() == Constant.DAY_TIME ?
                R.layout.dialog_reward : R.layout.dialog_reward_night);
    }

    private void initData() {
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("ZFB", "dabutaizha@163.com");
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(clipData);
    }

    private void initView() {
        mRewardTv.setText(ResUtil.getString(R.string.reward_default_tip));
    }

    private void initViewListener() {
        mRewardBtn.setOnClickListener(view -> {
            String intentFullUrl = Constant.ZFB_QR_CODE;
            try {
                Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME );
                mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                ResUtil.showToast(mContext, "尚未安装支付宝");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            RewardDialog.this.dismiss();
        });
    }

}
