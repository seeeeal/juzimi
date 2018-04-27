package com.example.dabutaizha.lines.provider;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.BaseApplication;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/27 下午1:39.
 */

public class WidgetModel {

    public static void saveWidgetTheme(int themeType) {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.WIDGET_THEME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.WIDGET_THEME_TYPE, themeType);
        editor.apply();
    }

    public static int getWidgetTheme() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.WIDGET_THEME, Context.MODE_PRIVATE);

        return sp.getInt(Constant.WIDGET_THEME_TYPE, Constant.THEME_DEFAULT);
    }

}
