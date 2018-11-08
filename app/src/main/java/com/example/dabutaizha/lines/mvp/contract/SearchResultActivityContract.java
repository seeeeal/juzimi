package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.info.SearchInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午6:51.
 */
public interface SearchResultActivityContract {

    interface View {

        void updateList(List<SearchInfo.SentencesItem> itemList);

        void showLoadMoreRequestError();

        void showRequestError();

        void showMessage(String msg);

    }

    interface Presenter {

        void initData();

        void process(String searchTag);

        void loadSearchData(String tag, int page);

        void showSearchData(List<SearchInfo.SentencesItem> itemList);

        void loadMore();

        void requestError();

        void fail(String msg);

    }

    interface Model {

        void loadSearchData(String tag, int page);

    }

}
