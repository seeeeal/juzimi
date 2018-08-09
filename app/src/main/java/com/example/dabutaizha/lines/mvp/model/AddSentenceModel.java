package com.example.dabutaizha.lines.mvp.model;


import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.contract.AddSentenceContract;

import io.reactivex.functions.Consumer;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/6/24 下午5:29.
 */

public class AddSentenceModel implements AddSentenceContract.Model {

    private AddSentenceContract.Presenter mPresenter;

    public AddSentenceModel(AddSentenceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void addSentences(SentencesModel sentencesModel) {
        SentencesObjectBox.getInstance().addByRxJava(sentencesModel).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long id) throws Exception {
                mPresenter.notifyView(id);
            }
        });
    }

}
