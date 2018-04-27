package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.OpenSourceInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/2 上午11:57.
 */

public interface OpenSourceContract {

    interface View {

        void updateOpenSourceList(List<OpenSourceInfo> list);

        void showMessage(String msg);

    }

    interface Presenter {

        void initData();

        void process();

        void showMessage(String msg);

    }

}
