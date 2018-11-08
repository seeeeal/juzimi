package com.example.dabutaizha.lines.wxapi;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 主题管理工具类
 * Created by dabutaizha on 2018/10/12 下午2:32.
 */

public class AppThemeUtils {

    public static int getCurrentAppTheme() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.APP_THEME_ID, Context.MODE_PRIVATE);

        return sp.getInt(Constant.THEME_SAVE, 0);
    }

}
