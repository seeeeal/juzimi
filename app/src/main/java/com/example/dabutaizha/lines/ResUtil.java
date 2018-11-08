package com.example.dabutaizha.lines;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.example.dabutaizha.lines.mvp.view.BaseApplication;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 资源获取工具类
 * Created by dabutaizha on 2018/1/12 下午5:41.
 */
public class ResUtil {

    private static Context mContext;

    public static Context getApplicationContext() {
        return BaseApplication.getInstance();
    }

    public static void init() {
        mContext = getApplicationContext();
    }

    public static String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static String[] getStringArray(int array) {
        return mContext.getResources().getStringArray(array);
    }

    public static int getColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        return mContext.getResources().getDrawable(drawableId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return mContext.getResources().getColorStateList(resId);
    }

    public static void showToast(Context context, CharSequence charSequence) {
        if (context != null) {
            Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
        }
    }
}
