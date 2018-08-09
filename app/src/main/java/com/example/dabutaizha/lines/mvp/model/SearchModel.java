package com.example.dabutaizha.lines.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;
import com.example.dabutaizha.lines.mvp.contract.SearchFragmentContract;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/26 下午5:24.
 */
public class SearchModel implements SearchFragmentContract.Model {

    private SearchFragmentContract.Presenter mPresenter;

    public SearchModel(SearchFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getSearchTags() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.FRAGMENT_SEARCH, Context.MODE_PRIVATE);

        String tags;
        tags = sp.getString(Constant.SEARCH_TAG, "");
        return tags;
    }

    @Override
    public void addSearchTag(String searchTag) {
        //得到存储的tags
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.FRAGMENT_SEARCH, Context.MODE_PRIVATE);
        String tags;
        tags = sp.getString(Constant.SEARCH_TAG, "");

        tags = tags + Constant.SPLIT + searchTag;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SEARCH_TAG, tags);
        editor.apply();

        mPresenter.refreshSearchTag(tags);
    }

    @Override
    public void clearSearchTags() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.FRAGMENT_SEARCH, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SEARCH_TAG, "");
        editor.apply();

        mPresenter.refreshSearchTag("");
    }

}
