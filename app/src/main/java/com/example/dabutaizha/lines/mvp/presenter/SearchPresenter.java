package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.mvp.contract.SearchFragmentContract;
import com.example.dabutaizha.lines.mvp.model.SearchModel;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/26 下午5:18.
 */

public class SearchPresenter implements SearchFragmentContract.Presenter {

    private SearchFragmentContract.View mView;
    private SearchFragmentContract.Model mModel;

    private String mTags;
    private String[] mRecommendTags;

    public SearchPresenter(SearchFragmentContract.View mView) {
        this.mView = mView;
        mModel = new SearchModel(this);
    }

    @Override
    public void initData() {
        mTags = mModel.getSearchTags();
        mRecommendTags = ResUtil.getStringArray(R.array.recommend_tag_item);
    }

    @Override
    public void process() {
        refreshRecommendTag();
        refreshSearchTag(mTags);
    }

    @Override
    public void addSearchTag(String searchTag) {
        mModel.addSearchTag(searchTag);
    }

    @Override
    public void refreshRecommendTag() {
        mView.refreshRecommendTag(mRecommendTags);
    }

    @Override
    public void refreshSearchTag(String searchTags) {
        if (searchTags != null) {
            String[] tags = searchTags.split(Constant.SPLIT);

            //对数组进行倒序
            for (int start = 0, end = tags.length - 1; start < end; start++, end--) {
                String temp = tags[end];
                tags[end] = tags[start];
                tags[start] = temp;
            }
            mView.refreshSearchTag(tags);
        }
    }

    @Override
    public void clearSearchTags() {
        mModel.clearSearchTags();
    }

}
