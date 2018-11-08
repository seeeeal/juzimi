package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.model.SentencesModel;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/6/24 下午1:41.
 */

public interface AddSentenceContract {

    interface View {
        void jumpToSharedCardActivity(SentencesModel model);

        void showMessage(String msg);
    }

    interface Presenter {
        void initData();

        void process();

        void createSentence(String production, String author, String content);

        void notifyView(long id);

        void showMessage(String msg);
    }

    interface Model {
        void addSentences(SentencesModel sentencesModel);
    }

}
