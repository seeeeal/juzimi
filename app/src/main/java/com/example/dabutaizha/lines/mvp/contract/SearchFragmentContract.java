package com.example.dabutaizha.lines.mvp.contract;


/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/26 下午4:26.
 */

public interface SearchFragmentContract {

    interface View {

        void refreshRecommendTag(String[] recommendTags);

        void refreshSearchTag(String[] searchTags);

        void addSearchTag(String searchTag);

        void clearSearchTags();

        void showMessage(int resid);
    }

    interface Presenter {

        void initData();

        void process();

        void addSearchTag(String searchTag);

        void refreshRecommendTag();

        void refreshSearchTag(String searchTags);

        void clearSearchTags();

    }

    interface Model {

        String getSearchTags();

        void addSearchTag(String searchTag);

        void clearSearchTags();

    }

}
