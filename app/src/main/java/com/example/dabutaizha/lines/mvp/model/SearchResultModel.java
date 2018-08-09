package com.example.dabutaizha.lines.mvp.model;

import android.util.Log;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.net.ApiServices;
import com.example.dabutaizha.lines.mvp.contract.SearchResultActivityContract;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午7:22.
 */

public class SearchResultModel implements SearchResultActivityContract.Model {

    private SearchResultActivityContract.Presenter mPresenter;

    public SearchResultModel(SearchResultActivityContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadSearchData(String tag, int page) {
        if (page == 0) {
            ApiServices.getAPIs().searchSentences(tag)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observable);
        } else {
            ApiServices.getAPIs().searchSentences(tag, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observable);
        }

    }

    private Observer<SearchInfo> observable = new Observer<SearchInfo>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(SearchInfo searchInfo) {

            if (searchInfo == null) {
                mPresenter.fail(ResUtil.getString(R.string.load_fail));
            }

            if (searchInfo != null && searchInfo.getSentencesItems() == null) {
                mPresenter.requestError();
            }

            if (searchInfo != null && searchInfo.getSentencesItems() != null) {
                mPresenter.showSearchData(searchInfo.getSentencesItems());
            }
        }

        @Override
        public void onError(Throwable e) {
            String message = e.getMessage();

            if (message.contains("404")) {
                mPresenter.fail(ResUtil.getString(R.string.load_end));
            } else {
                mPresenter.requestError();
                mPresenter.fail(e.getMessage());
            }
        }

        @Override
        public void onComplete() {

        }
    };
}
