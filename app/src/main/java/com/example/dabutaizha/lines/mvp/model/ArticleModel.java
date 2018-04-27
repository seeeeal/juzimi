package com.example.dabutaizha.lines.mvp.model;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.ArticleInfo;
import com.example.dabutaizha.lines.net.ApiServices;
import com.example.dabutaizha.lines.mvp.contract.ArticleActivityContract;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/30 下午5:34.
 */

public class ArticleModel implements ArticleActivityContract.Model {

    private ArticleActivityContract.Presenter mPresenter;

    public ArticleModel(ArticleActivityContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void loadArticleInfo(String articleId, int page) {
        ApiServices.getAPIs().getArticlePage(articleId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleInfo articleInfo) {
                        if (articleInfo == null) {
                            mPresenter.fail(ResUtil.getString(R.string.load_fail));
                        }

                        if (articleInfo != null && articleInfo.getSentencesItems() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.ARTICLE_PAGE_URL, articleInfo.getTitlePageUrl());
                            bundle.putString(Constant.ARTICLE_PAGE_INTRO, articleInfo.getIntro());

                            bundle.putParcelableArrayList(Constant.ARTICLE_PAGE_RELATED,
                                    (ArrayList<? extends Parcelable>) articleInfo.getRelatedWorks());
                            mPresenter.showArticleHead(bundle);

                            mPresenter.showArticleData(articleInfo.getSentencesItems());
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
                });
    }

}
