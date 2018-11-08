package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.AppThemeBean;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 上午10:11.
 */

public interface AppThemeContract {

    interface View {
        void showThemeList(List<AppThemeBean> themeList);

        void showMessage(String msg);
    }

    interface Presenter {

        void initData();

        void showMessage(String msg);

        void saveThemeId(int themeId);
    }

    interface Model {

        void saveThemeId(int themeId);

    }

}
