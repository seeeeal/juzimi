package com.example.dabutaizha.lines.mvp.presenter;

import android.os.Bundle;

import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.ArticleActivityContract;
import com.example.dabutaizha.lines.mvp.model.ArticleModel;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/30 下午5:31.
 */

public class ArticlePresenter implements ArticleActivityContract.Presenter {

    private ArticleActivityContract.View mView;
    private ArticleActivityContract.Model mModel;

    private String mArticleId;
    private int mCurrentPage;

    public ArticlePresenter(ArticleActivityContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initData() {
        mModel = new ArticleModel(this);
    }

    @Override
    public void process(String articleId) {
        mArticleId = articleId;
        loadArticleInfo(mArticleId, mCurrentPage);
    }

    @Override
    public void loadArticleInfo(String articleId, int page) {
        mModel.loadArticleInfo(articleId, page);
    }

    @Override
    public void showArticleHead(Bundle bundle) {
        mView.updateHead(bundle);
    }

    @Override
    public void showArticleData(List<SearchInfo.SentencesItem> itemList) {
        for (SearchInfo.SentencesItem item : itemList) {
            item.setItemUIType(AppThemeUtils.getCurrentAppTheme());
        }

        mView.updateList(itemList);
    }

    @Override
    public void loadMore() {
        mCurrentPage += 1;
        loadArticleInfo(mArticleId, mCurrentPage);
    }

    @Override
    public void requestError() {
        if (mCurrentPage != 0) {
            mView.showLoadMoreRequestError();
        }

        if (mCurrentPage == 0) {
            mView.showRequestError();
        }
    }

    @Override
    public void fail(String msg) {
        mView.showMessage(msg);
    }
}
