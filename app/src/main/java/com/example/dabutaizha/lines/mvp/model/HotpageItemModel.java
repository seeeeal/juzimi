package com.example.dabutaizha.lines.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.PictureInfo;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.HotPageItemFragmentContract;
import com.example.dabutaizha.lines.net.ApiServices;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 上午10:00.
 */

public class HotpageItemModel implements HotPageItemFragmentContract.Model {

    private int mHotpageTag = 0;
    private String mHotpageTagName[] = ResUtil.getStringArray(R.array.hotpage_tag);

    private HotPageItemFragmentContract.Presenter mPresenter;
    private String mCategory;

    public HotpageItemModel(HotPageItemFragmentContract.Presenter mPresenter, String category) {
        this.mPresenter = mPresenter;
        mCategory = category;
    }

    @Override
    public void loadData(int page) {
        Observable<SearchInfo> observable = null;

        observable = getObservableByCategory(page);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchInfo searchInfo) {
                        if (searchInfo == null) {
                            mPresenter.fail(ResUtil.getString(R.string.load_fail));
                        }
                        if (searchInfo != null) {
                            mPresenter.loadData(searchInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();

                        if (message.contains("404")) {
                            mPresenter.fail(ResUtil.getString(R.string.load_end));
                        } else {
                            mPresenter.fail(e.getMessage());
                            mPresenter.requestError();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<SearchInfo> getObservableByCategory(int page) {
        switch (mCategory) {
            case "最新上传":
                mHotpageTag = 0;
                return ApiServices.getAPIs().getHotPageNew(page);
            case "今日热门":
                mHotpageTag = 1;
                return ApiServices.getAPIs().getHotPageTodayHot(page);
            case "今日推荐":
                mHotpageTag = 2;
                return ApiServices.getAPIs().getHotPageRecommend(page);
            case "最受欢迎":
                mHotpageTag = 3;
                return ApiServices.getAPIs().getHotPageLike(page);
            default:
                return null;
        }
    }

    @Override
    public void loadPictureData() {
        ApiServices.getAPIAssistant().getHotPagePictures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PictureInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PictureInfo pictureInfo) {
                        if (pictureInfo == null) {
                            mPresenter.fail(ResUtil.getString(R.string.load_fail));
                        }
                        if (pictureInfo != null && pictureInfo.getPictureItems() != null) {

                            List<PictureInfo.PictureItem> pictures = pictureInfo.getPictureItems();
                            PictureInfo.PictureItem item = pictures.get(mHotpageTag);
                            String url = item.getPictureUrl();

                            mPresenter.loadPictureData(url);
                            mPresenter.refreshHeaderTag(item.getPictureMessage());

                            // 存储图片数据
                            savePicData(buildUrlStr(pictures));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();

                        if (message.contains("404")) {
                            mPresenter.fail(ResUtil.getString(R.string.load_end));
                        } else {
                            mPresenter.fail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String buildUrlStr(List<PictureInfo.PictureItem> pictures) {
        StringBuilder urlBuilder = new StringBuilder();
        for (PictureInfo.PictureItem item : pictures) {
            urlBuilder.append(item.getPictureUrl()).append(Constant.SPLIT);
        }
        urlBuilder.replace(urlBuilder.length() - 1, urlBuilder.length(), "");
        return urlBuilder.toString();
    }

    @Override
    public void savePicData(String url) {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.FRAGMENT_HOTPAGE_PIC, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.PICS_SAVE, url);
        editor.apply();
    }

    @Override
    public String getLastPicData() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.FRAGMENT_HOTPAGE_PIC, Context.MODE_PRIVATE);

        return sp.getString(Constant.PICS_SAVE, "");
    }


}
