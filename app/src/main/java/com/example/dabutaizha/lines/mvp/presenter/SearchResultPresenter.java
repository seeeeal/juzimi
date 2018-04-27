package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.SearchResultActivityContract;
import com.example.dabutaizha.lines.mvp.model.SearchResultModel;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午7:19.
 */

public class SearchResultPresenter implements SearchResultActivityContract.Presenter {

    private SearchResultActivityContract.View mView;
    private SearchResultActivityContract.Model mModel;

    private String mSearchTag;
    private int mCurrentPage = 0;

    public SearchResultPresenter(SearchResultActivityContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void initData() {
        mModel = new SearchResultModel(this);
    }

    @Override
    public void process(String searchTag) {
        mSearchTag = searchTag;
        loadSearchData(mSearchTag, mCurrentPage);
    }

    @Override
    public void loadSearchData(String tag, int page) {
        mModel.loadSearchData(tag, page);
    }

    @Override
    public void showSearchData(List<SearchInfo.SentencesItem> itemList) {
        mView.updateList(itemList);
    }

    @Override
    public void loadMore() {
        mCurrentPage += 1;
        loadSearchData(mSearchTag, mCurrentPage);
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
