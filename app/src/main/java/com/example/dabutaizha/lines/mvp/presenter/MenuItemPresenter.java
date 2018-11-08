package com.example.dabutaizha.lines.mvp.presenter;

import android.os.Bundle;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.bean.info.BlockInfo;
import com.example.dabutaizha.lines.bean.info.BlockInfoItem;
import com.example.dabutaizha.lines.mvp.contract.MenuItemFragmentContract;
import com.example.dabutaizha.lines.mvp.model.MenuItemModel;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午3:35.
 */
public class MenuItemPresenter implements MenuItemFragmentContract.Presenter {

    private MenuItemFragmentContract.View mView;
    private MenuItemFragmentContract.Model mModel;

    private int mCurrentPage;

    public MenuItemPresenter(MenuItemFragmentContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void initData(Bundle bundle, Bundle savedInstanceState) {

        String category = bundle.getString(Constant.FRAGMENT_TITLE);
        mModel = new MenuItemModel(this, category);
    }

    @Override
    public void process(Bundle savedInstanceState) {
        mModel.loadData(mCurrentPage);
    }

    @Override
    public void loadData(BlockInfo blockInfo) {
        List<BlockInfo.BlockItemPage> pages = blockInfo.getItemsPage();
        List<BlockInfo.BlockItemContent> contents = blockInfo.getItemsContent();

        int pagesSize = pages.size();
        if (!(pagesSize == contents.size())) {
            return;
        }

        List<BlockInfoItem> blockInfoItemList = new ArrayList<>();

        for (int i = 0; i < pagesSize; i ++) {
            BlockInfoItem infoItem = new BlockInfoItem(pages.get(i), contents.get(i));
            infoItem.setmItemUIType(AppThemeUtils.getCurrentAppTheme());
            blockInfoItemList.add(infoItem);
        }

        mView.updateList(blockInfoItemList);
    }

    @Override
    public void pullToRefresh(boolean isLoadMore) {
        mCurrentPage = isLoadMore ? ++mCurrentPage : 0;
        mModel.loadData(mCurrentPage);
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
            List<BlockInfoItem> blockInfoItemList = new ArrayList<>();
            mView.updateList(blockInfoItemList);
            mView.showRequestError();
        }
    }

    @Override
    public void notifyDataThemeChanged(List<BlockInfoItem> data, int themeId) {
        for (BlockInfoItem infoItem : data) {
            if (infoItem.getItemType() != themeId) {
                infoItem.setmItemUIType(themeId);
            }
        }
    }

}
