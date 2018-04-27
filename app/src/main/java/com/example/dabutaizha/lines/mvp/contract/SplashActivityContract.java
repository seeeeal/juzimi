package com.example.dabutaizha.lines.mvp.contract;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/7 下午2:03.
 */

public interface SplashActivityContract {

    interface View {

        void refreshGuidePage();

        void enterMainActivity();

    }

    interface Presenter {

        void initData();

        void process();

    }

    interface Model {

        void saveLaunchTag();

        boolean checkIsFirstLaunch();

    }

}
