package com.example.dabutaizha.lines.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.BaseApplication;

import java.util.HashSet;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class LoginUtil {

    private static SharedPreferences mSp = BaseApplication.getInstance()
            .getSharedPreferences(Constant.USER_STATE, Context.MODE_PRIVATE);

    public static void initLoginState() {
        boolean isLogin = mSp.getBoolean(Constant.SAVE_IS_LOGINED, false);
        if (isLogin) {
            //如果已经登陆过更新本地状态
            Constant.IS_LOGINED = true;
            Constant.USER_ID = mSp.getString(Constant.SAVE_USER_ID, "");
        }
    }

    /**
     *Description: 退出登录
     */
    public static void loginOut() {
        if (getCookies() != null) {
            setCookies(new HashSet<>());
        }
        Constant.IS_LOGINED = false;
        Constant.USER_ID = "";
        saveUserState(false, "");
    }

    public static HashSet<String> getCookies() {
        return (HashSet) mSp.getStringSet(Constant.COOKIE_INTERCEPTOR_KEY, new HashSet<>());
    }

    public static void setCookies(HashSet<String> cookies) {
        mSp.edit().putStringSet(Constant.COOKIE_INTERCEPTOR_KEY, cookies).apply();
    }

    public static void saveUserState(boolean isLogined, String userId) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(Constant.SAVE_IS_LOGINED, isLogined);
        editor.putString(Constant.SAVE_USER_ID, userId);
        editor.apply();
    }

    public static String getUserName() {
        return mSp.getString(Constant.SAVE_USER_NAME, "default error name");
    }

    public static void saveUserName(String userName) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(Constant.SAVE_USER_NAME, userName);
        editor.apply();
    }

}
