package com.example.dabutaizha.lines.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.info.VersionInfo;
import com.example.dabutaizha.lines.database.PremissionObjectBox;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;
import com.example.dabutaizha.lines.mvp.view.DialogueFragment;
import com.example.dabutaizha.lines.mvp.view.HotPageFragment;
import com.example.dabutaizha.lines.mvp.view.MenuFragment;
import com.example.dabutaizha.lines.mvp.view.SearchFragment;
import com.example.dabutaizha.lines.mvp.contract.MainActivityContract;
import com.example.dabutaizha.lines.mvp.model.MainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description : 主页的控制器
 * Created by dabutaizha on 2017/11/29 下午8:03.
 */

public class MainPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private MainActivityContract.Model mModel;

    private String[] mTitles;
    private List<Fragment> mFragmentList;

    public MainPresenter(MainActivityContract.View mView) {
        this.mView = mView;
        mModel = new MainModel(this);
        querySentencesSize();
    }

    // 初始化所需要的数据
    @Override
    public void initData(Intent intent) {
        checkInputSentencePremission();

        mTitles = ResUtil.getStringArray(R.array.tab_item);
        initFragmentList();
    }

    private void checkInputSentencePremission() {
        PremissionObjectBox.getInstance().findAllByRxJava().subscribe(premissionModels -> {
            if (premissionModels != null) {
                int size = premissionModels.size();
                if (size == 0) {
                    Log.d("dabutaizha", "PremissionBox Error0" + size);
                    Constant.INPUT_SENTENCE_PREMISSION = false;
                }
                if (size == 1) {
                    Log.d("dabutaizha", "PremissionBox Error1" + size);
                    Constant.INPUT_SENTENCE_PREMISSION = premissionModels.get(0).isInputSentencePremission();
                }
                if (size != 0 && size != 1){
                    Log.d("dabutaizha", "PremissionBox Error" + size);
                    Constant.INPUT_SENTENCE_PREMISSION = false;
                }
            }
        });
    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();

        for (int i = 0; i < mTitles.length; i ++) {
            switch (i) {
                case 0:
                    mFragmentList.add(HotPageFragment.newInstance(mTitles[i]));
                    break;
//                case 1:
//                    mFragmentList.add(DialogueFragment.newInstance(mTitles[i]));
//                    break;
                case 1:
                    mFragmentList.add(MenuFragment.newInstance(mTitles[i]));
                    break;
                case 2:
                    mFragmentList.add(new SearchFragment());
                    break;
                default:

                    break;
            }
        }
    }

    // 装载数据
    @Override
    public void process() {
        mView.setTab(mFragmentList, mTitles);
        mView.setSelectPage(0);
    }

    @Override
    public void clearCache() {
        mModel.clearCache();
    }

    @Override
    public void getRecentVersionInfo() {
        mModel.requestRecentVersionInfo();
    }

    @Override
    public void compareVersionInfo(VersionInfo versionInfo) {
        String packageName = BaseApplication.getInstance().getPackageName();
        PackageInfo info = null;
        try {
            info = BaseApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (info != null) {
            String localVersion = info.versionName;
            String recentVersion = versionInfo.getVersionMessage().getVersionCode();
            try {
                if (compareVersion(recentVersion, localVersion) > 0) {
                    String[] apkMessage = versionInfo.getVersionMessage().getApkMessage().split("/");
                    String apkSize = apkMessage[0] == null ? "0" : apkMessage[0];

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.UPDATE_LOCAL_VERSION, localVersion);
                    bundle.putString(Constant.UPDATE_RECENT_VERSION, recentVersion);
                    bundle.putString(Constant.UPDATE_APK_SIZE, apkSize);

                    mView.showUpdateDialog(bundle);
                } else {
                    mView.showMessage(ResUtil.getString(R.string.update_tip));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Intent buildFeedBackData(Context context) {
        return mModel.buildFeedBackData(context);
    }

    @Override
    public void refreshView(int collectSize) {
        mView.refreshView(collectSize);
    }

    @Override
    public void querySentencesSize() {
        mModel.querySentencesSize();
    }

    @Override
    public void showMessage(String msg) {
        mView.showMessage(msg);
    }

    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

}
