package com.example.dabutaizha.lines.mvp.contract;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午2:18.
 */

public interface MenuFragmentContract {

    interface View {

        void setSelectPage(int item);

        void setTab(List<Fragment> fragmentList, String[] titles);

        void showMessage(int resid);

    }

    interface Presenter {

        void initData();

        void process();

    }

}
