package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.info.DialogueInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/20 下午11:27.
 */

public interface DialogueFragmentContract {

    interface View {
        /**
         * Description: 初始化监听
         */
        void initViewListener();

        /**
         *Description: 更新数据
         */
        void updateList(List<DialogueInfo.DialogueItem> list);

        void showLoadMoreRequestError();

        void showRequestError();

        void showMessage(String msg);
    }

    interface Presenter {

        void initData();

        void process();

        void loadData(DialogueInfo dialogueInfo);

        /**
         *Description: 刷新或加载请求
         */
        void pullToRefresh(boolean isLoadMore);

        /**
         *Description: 请求失败
         */
        void fail(String msg);

        void requestError();

    }

    interface Model {

        /**
         *Description: 加载分类数据
         */
        void loadData(int page);

    }

}
