package com.example.dabutaizha.lines.mvp.presenter;

import android.os.Bundle;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.HotPageItemFragmentContract;
import com.example.dabutaizha.lines.mvp.model.HotpageItemModel;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 上午9:44.
 */
public class HotpageItemPresenter implements HotPageItemFragmentContract.Presenter {

    private HotPageItemFragmentContract.View mView;
    private HotPageItemFragmentContract.Model mModel;

    private int mCurrentPage;

    public HotpageItemPresenter(HotPageItemFragmentContract.View mView) {
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
        String category = bundle.getString(Constant.FRAGMENT_TITLE);
        mModel = new HotpageItemModel(this, category);
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
        List<SearchInfo.SentencesItem> sentencesItemList = searchInfo.getSentencesItems();
        for (SearchInfo.SentencesItem item : sentencesItemList) {
            item.setItemUIType(AppThemeUtils.getCurrentAppTheme());
        }
        mView.updateData(sentencesItemList);
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

    @Override
    public void notifyDataThemeChanged(List<SearchInfo.SentencesItem> itemList, int themeId) {
        for (SearchInfo.SentencesItem item : itemList) {
            if (item.getItemUIType() != themeId) {
                item.setItemUIType(themeId);
            }
        }
    }

}
