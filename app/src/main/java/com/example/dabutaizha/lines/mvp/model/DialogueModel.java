package com.example.dabutaizha.lines.mvp.model;

import android.util.Log;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.DialogueInfo;
import com.example.dabutaizha.lines.mvp.contract.DialogueFragmentContract;
import com.example.dabutaizha.lines.net.ApiServices;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/20 下午11:40.
 */

public class DialogueModel implements DialogueFragmentContract.Model {

    private DialogueFragmentContract.Presenter mPresenter;

    public DialogueModel(DialogueFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadData(int page) {
        ApiServices.getAPIs().getDialoguePage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DialogueInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DialogueInfo dialogueInfo) {
                        if (dialogueInfo == null) {
                            mPresenter.fail(ResUtil.getString(R.string.load_fail));
                        }
                        if (dialogueInfo != null && dialogueInfo.getmDialogueItemList() != null) {
                            mPresenter.loadData(dialogueInfo);
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
