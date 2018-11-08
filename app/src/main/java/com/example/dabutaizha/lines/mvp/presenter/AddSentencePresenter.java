package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.bean.model.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.contract.AddSentenceContract;
import com.example.dabutaizha.lines.mvp.model.AddSentenceModel;

import io.reactivex.functions.Consumer;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/6/24 下午1:46.
 */

public class AddSentencePresenter implements AddSentenceContract.Presenter {

    private AddSentenceContract.View mView;
    private AddSentenceContract.Model mModel;

    public AddSentencePresenter(AddSentenceContract.View view) {
        mView = view;
    }

    @Override
    public void initData() {
        mModel = new AddSentenceModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void createSentence(String article, String author, String content) {
        SentencesModel model = new SentencesModel();
        model.setArticle(article);
        model.setWriter(author);
        model.setContent(content);
        model.setDate(System.currentTimeMillis());
        model.setPublisher("local");
        model.setLike("1");
        model.setCommentCount("0");

        mModel.addSentences(model);
    }

    @Override
    public void notifyView(long id) {
        SentencesObjectBox.getInstance().getSentenceByRxJava(id).subscribe(new Consumer<SentencesModel>() {
            @Override
            public void accept(SentencesModel sentencesModel) throws Exception {
                if (null != sentencesModel) {
                    mView.jumpToSharedCardActivity(sentencesModel);
                }
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        mView.showMessage(msg);
    }
}
