package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.mvp.contract.SplashActivityContract;
import com.example.dabutaizha.lines.mvp.model.SplashModel;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/7 下午4:02.
 */

public class SplashPresenter implements SplashActivityContract.Presenter {

    private SplashActivityContract.Model mModel;
    private SplashActivityContract.View mView;

    public SplashPresenter(SplashActivityContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initData() {
        mModel = new SplashModel(this);
    }

    @Override
    public void process() {

        boolean isFirst = mModel.checkIsFirstLaunch();

        if (isFirst) {
            mView.refreshGuidePage();
        } else {
            mView.enterMainActivity();
        }

        mModel.saveLaunchTag();
    }

}
