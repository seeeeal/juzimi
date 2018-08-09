package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.mvp.contract.ExplorePhotoActivityContract;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/8/8 下午3:34.
 */

public class ExplorePhotoActivityPresenter implements ExplorePhotoActivityContract.Presenter {

    private ExplorePhotoActivityContract.View mView;

    public ExplorePhotoActivityPresenter(ExplorePhotoActivityContract.View view) {
        mView = view;
    }

}
