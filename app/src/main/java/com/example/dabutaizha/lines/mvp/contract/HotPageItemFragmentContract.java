package com.example.dabutaizha.lines.mvp.contract;

import android.os.Bundle;

import com.example.dabutaizha.lines.bean.SearchInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/11 下午7:58.
 */

public interface HotPageItemFragmentContract {

    interface View {

        /**
         *Description: 生成头部
         */
        void updateHeaderView(String url);

        void refreshHeaderTag(String tag);

        /**
         *Description: 更新句子
         */
        void updateData(List<SearchInfo.SentencesItem> sentences);

        void showRequestError();

        void showLoadMoreRequestError();

        void showMessage(CharSequence msg);
    }

    interface Presenter {

        /**
         *Description: 存储状态
         */
        void onSaveInstanceState(Bundle outState);

        /**
         *Description: 初始化数据
         */
        void initData(Bundle bundle, Bundle savedInstanceState);

        /**
         *Description: 主体流程
         */
        void process(Bundle savedInstanceState);

        /**
         * 加载数据，通过Model中对应方法获取
         */
        void loadData(SearchInfo searchInfo);

        /**
         * 加载图片数据，通过Model中对应方法获取
         */
        void loadPictureData(String url);

        /**
         *Description: 刷新或加载请求
         */
        void pullToRefresh(boolean isLoadMore);

        void refreshHeaderTag(String tag);

        /**
         *Description: 请求失败
         */
        void fail(String msg);

        void requestError();
    }

    interface Model {

        /**
         *Description: 加载首页句子数据
         */
        void loadData(int page);

        /**
         *Description: 加载主页图片数据
         */
        void loadPictureData();

        void savePicData(String url);

        String getLastPicData();

    }

}
