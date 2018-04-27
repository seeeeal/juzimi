package com.example.dabutaizha.lines.mvp.contract;

import android.os.Bundle;

import com.example.dabutaizha.lines.bean.SearchInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/30 下午5:06.
 */

public interface ArticleActivityContract {

    interface View {
        void showLoadMoreRequestError();

        void showRequestError();

        void updateHead(Bundle bundle);

        void updateList(List<SearchInfo.SentencesItem> itemList);

        void showMessage(String msg);
    }

    interface Presenter {
        void initData();

        void process(String articleId);

        void loadArticleInfo(String articleId, int page);

        void showArticleHead(Bundle bundle);

        void showArticleData(List<SearchInfo.SentencesItem> itemList);

        void loadMore();

        void requestError();

        void fail(String msg);
    }

    interface Model {
        void loadArticleInfo(String articleId, int page);
    }

}
