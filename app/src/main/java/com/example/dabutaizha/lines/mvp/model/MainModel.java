package com.example.dabutaizha.lines.mvp.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import com.example.dabutaizha.lines.FileCacheUtils;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.bean.VersionInfo;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.net.ApiServices;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;
import com.example.dabutaizha.lines.mvp.contract.MainActivityContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/2 上午10:21.
 */

public class MainModel implements MainActivityContract.Model {

    private MainActivityContract.Presenter mPresenter;

    public MainModel(MainActivityContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void clearCache() {
        new ClearCacheTask().execute();
    }

    @Override
    public void requestRecentVersionInfo() {
        ApiServices.getAPIUpdate().getRecentVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersionInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VersionInfo versionInfo) {
                        if (versionInfo != null && versionInfo.getVersionMessage() != null) {
                            mPresenter.compareVersionInfo(versionInfo);
                        } else {
                            mPresenter.showMessage(ResUtil.getString(R.string.request_version_error));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public Intent buildFeedBackData(Context context) {
        String appVersionCode = "0";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionCode = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:dabutaizha@163.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "摘·抄【应用反馈】");
        intent.putExtra(Intent.EXTRA_TEXT, "-------- -------- --------\n" +
                "    ANDROID VERSION:" + Build.VERSION.RELEASE + "\n" +
                "    MODEL:" + Build.MODEL + "\n" +
                "    APP VERSION:" + appVersionCode + "\n" +
                "-------- -------- --------\n");

        return intent;
    }

    @Override
    public void querySentencesSize() {
        SentencesObjectBox.getInstance().findAllByRxJava().subscribe(new Consumer<List<SentencesModel>>() {
            @Override
            public void accept(List<SentencesModel> sentencesModels) throws Exception {
                if (sentencesModels != null) {
                    mPresenter.refreshView(sentencesModels.size());
                }
            }
        });
    }

    private class ClearCacheTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            FileCacheUtils.cleanApplicationData(BaseApplication.getInstance());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPresenter.showMessage(BaseApplication.getInstance().getString(R.string.clearCache));
        }
    }

}
