package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.bean.info.DialogueInfo;
import com.example.dabutaizha.lines.mvp.contract.DialogueFragmentContract;
import com.example.dabutaizha.lines.mvp.model.DialogueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/20 下午11:29.
 */

public class DialoguePresenter implements DialogueFragmentContract.Presenter {

    private DialogueFragmentContract.View mView;
    private DialogueFragmentContract.Model mModel;

    private int mCurrentPage = 0;

    public DialoguePresenter(DialogueFragmentContract.View view) {
        mView = view;
    }

    @Override
    public void initData() {
        mModel = new DialogueModel(this);
    }

    @Override
    public void process() {
        mModel.loadData(mCurrentPage);
    }

    @Override
    public void loadData(DialogueInfo dialogueInfo) {
        mView.updateList(dialogueInfo.getmDialogueItemList());
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
            List<DialogueInfo.DialogueItem> dialogueItemList = new ArrayList<>();
            mView.updateList(dialogueItemList);
            mView.showRequestError();
        }
    }
}
