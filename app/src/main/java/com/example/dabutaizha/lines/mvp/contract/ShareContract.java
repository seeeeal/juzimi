package com.example.dabutaizha.lines.mvp.contract;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.bean.model.SentencesModel;
import com.example.dabutaizha.lines.mvp.adapter.ShareAdapter;
import com.example.dabutaizha.lines.mvp.presenter.SharePresenter;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午5:53.
 */

public interface ShareContract {

    interface View {

        void getShareBitmap(Bitmap bitmap);

        void refreshIsCollected(boolean isExist);

        void hideCollectImg();

        void showCollectImg();

        void showMessage(String msg);

    }

    interface Presenter {

        void initData();

        void process();

        void getScreenshot(ShareAdapter adapter, RecyclerView recyclerView, boolean isSave);

        void getLongScreenShotRecycleView(RecyclerView recyclerView, ShareAdapter adapter, SharePresenter.RecycleViewRecCallback callback);

        void checkIsCollect(int sentenceId);

        void refreshIsCollected(boolean isExist);

        void deleteData(long id);

        void addData(SentencesModel model);

        void notifyDataThemeChanged(List<SearchInfo.SentencesItem> sentencesItems, int themeId);

        void showMessage(String msg);

    }

    interface Module {

        void saveShareData(Bitmap bitmap);

        void checkIsCollect(int sentenceId);

        void deleteData(long id);

        void addData(SentencesModel model);

    }

}
