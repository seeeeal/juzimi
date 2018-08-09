package com.example.dabutaizha.lines.mvp.contract;

import android.os.Bundle;

import com.example.dabutaizha.lines.bean.BlockInfo;
import com.example.dabutaizha.lines.bean.BlockInfoItem;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午3:07.
 */

public interface MenuItemFragmentContract {

    interface View {

        /**
         * Description: 初始化监听
         */
        void initViewListener();

        /**
         *Description: 更新数据
         */
        void updateList(List<BlockInfoItem> list);

        void showLoadMoreRequestError();

        void showRequestError();

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
        void loadData(BlockInfo blockInfo);

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
