package com.example.dabutaizha.lines.mvp.presenter;

import android.os.Bundle;

import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.HotPageFragmentContract;
import com.example.dabutaizha.lines.mvp.model.HotpageModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 上午9:44.
 */
public class HotpagePresenter implements HotPageFragmentContract.Presenter {

    private HotPageFragmentContract.View mView;
    private HotPageFragmentContract.Model mModel;

    private int mCurrentPage;

    public HotpagePresenter(HotPageFragmentContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     *Description: 初始化model
     */
    @Override
    public void initData(Bundle bundle, Bundle savedInstanceState) {
        mModel = new HotpageModel(this);
    }

    @Override
    public void process(Bundle savedInstanceState) {
        mModel.loadData(mCurrentPage);
        mModel.loadPictureData();
    }

    /**
     *Description: 得到数据调用view刷新
     */
    @Override
    public void loadData(SearchInfo searchInfo) {
        mView.updateData(searchInfo.getSentencesItems());
    }

    /**
     *Description: 得到数据调用view刷新
     */
    @Override
    public void loadPictureData(String url) {
        mView.updateHeaderView(url);
    }

    /**
     *Description: 根据手势调用model请求数据
     */
    @Override
    public void pullToRefresh(boolean isLoadMore) {
        mCurrentPage = isLoadMore ? ++mCurrentPage : 0;

        mModel.loadData(mCurrentPage);
        mModel.loadPictureData();
    }

    @Override
    public void refreshHeaderTag(String tag) {
        mView.refreshHeaderTag(tag);
    }

    @Override
    public void fail(String msg) {
        mView.showMessage(msg);
    }

    @Override
    public void requestError() {
        if (mCurrentPage != 0) {
            mView.showLoadMoreRequestError();
        }
        if (mCurrentPage == 0) {
            List<SearchInfo.SentencesItem> sentencesItemList = new ArrayList<>();
            mView.updateData(sentencesItemList);
            mView.showRequestError();
        }
    }
}
