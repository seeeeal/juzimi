package com.example.dabutaizha.lines.mvp.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.provider.WidgetModel;

import butterknife.ButterKnife;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/9/28 下午4:20.
 */

public class WidgetThemeDialog extends Dialog {

    private Context mContext;
    private LinearLayout mLayoutThemeDefault;
    private LinearLayout mLayoutThemeTranslate;

    public WidgetThemeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        setContentView(R.layout.dialog_widget_theme);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        mLayoutThemeDefault = findViewById(R.id.widget_theme_default);
        mLayoutThemeTranslate = findViewById(R.id.widget_theme_translate);
    }

    private void initListener() {
        mLayoutThemeDefault.setOnClickListener(view -> {
            WidgetModel.saveWidgetTheme(Constant.THEME_DEFAULT);

            if (!((Activity)mContext).isFinishing() && !((Activity)mContext).isDestroyed()) {
                ResUtil.showToast(mContext, ResUtil.getString(R.string.theme_default_tip));
            }

            dismiss();
        });

        mLayoutThemeTranslate.setOnClickListener(view -> {
            WidgetModel.saveWidgetTheme(Constant.TRANSPARENT);

            if (!((Activity)mContext).isFinishing() && !((Activity)mContext).isDestroyed()) {
                ResUtil.showToast(mContext, ResUtil.getString(R.string.theme_translate_tip));
            }

            dismiss();
        });
    }

}
