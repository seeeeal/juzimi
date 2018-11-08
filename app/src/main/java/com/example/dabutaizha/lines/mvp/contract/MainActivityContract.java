package com.example.dabutaizha.lines.mvp.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.dabutaizha.lines.bean.info.VersionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2017/11/29 下午7:54.
 */

public interface MainActivityContract {

    interface View {

        void setSelectPage(int item);

        void setTab(List<Fragment> fragmentList, String[] titles);

        void showUpdateDialog(Bundle bundle);

        void refreshView(int collectSize);

        void showMessage(String msg);

    }

    interface Presenter {

        void initData(Intent intent);

        void process();

        void clearCache();

        void getRecentVersionInfo();

        void compareVersionInfo(VersionInfo versionInfo);

        Intent buildFeedBackData(Context context);

        void refreshView(int collectSize);

        void querySentencesSize();

        void showMessage(String msg);

    }

    interface Model {

        void clearCache();

        void requestRecentVersionInfo();

        Intent buildFeedBackData(Context context);

        void querySentencesSize();

    }

}
