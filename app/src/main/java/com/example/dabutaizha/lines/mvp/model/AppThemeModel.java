package com.example.dabutaizha.lines.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.contract.AppThemeContract;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 上午10:19.
 */

public class AppThemeModel implements AppThemeContract.Model {

    private AppThemeContract.Presenter mPresenter;

    public AppThemeModel(AppThemeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void saveThemeId(int themeId) {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.APP_THEME_ID, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.THEME_SAVE, themeId);
        editor.apply();
    }
}
