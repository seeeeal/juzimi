package com.example.dabutaizha.lines.mvp.view.dialog;

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
import com.example.dabutaizha.lines.bean.model.PremissionModel;
import com.example.dabutaizha.lines.database.PremissionObjectBox;
import com.example.dabutaizha.lines.mvp.view.WebViewActivity;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
    private CyclicBarrier mCyclicBarrier;

    public AboutDialog(@NonNull Context context, int themeResId, CyclicBarrier cyclicBarrier) {
        super(context, themeResId);
        mContext = context;
        mCyclicBarrier = cyclicBarrier;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getCurrentThemeLayout());
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

        mAboutMe.setOnLongClickListener(view -> {
            if (!Constant.INPUT_SENTENCE_PREMISSION) {
                Constant.INPUT_SENTENCE_PREMISSION = true;

                PremissionModel model = new PremissionModel();
                model.setInputSentencePremission(true);
                PremissionObjectBox.getInstance().addByRxJava(model).subscribe(aLong -> {});
                ResUtil.showToast(mContext, ResUtil.getString(R.string.input_sentence_premission_tip));
                try {
                    mCyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            } else {
                //No any tips
            }
            return true;
        });
    }

    private int getCurrentThemeLayout() {
        return (AppThemeUtils.getCurrentAppTheme() == Constant.DAY_TIME ?
                R.layout.dialog_about : R.layout.dialog_about_night);
    }
}
