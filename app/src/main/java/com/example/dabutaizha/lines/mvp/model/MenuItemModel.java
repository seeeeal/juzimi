package com.example.dabutaizha.lines.mvp.model;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.info.BlockInfo;
import com.example.dabutaizha.lines.net.ApiServices;
import com.example.dabutaizha.lines.mvp.contract.MenuItemFragmentContract;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午3:56.
 */

public class MenuItemModel implements MenuItemFragmentContract.Model {

    private MenuItemFragmentContract.Presenter mPresenter;
    private String mCategory;

    public MenuItemModel(MenuItemFragmentContract.Presenter mPresenter, String category) {
        this.mPresenter = mPresenter;
        this.mCategory = category;
    }

    @Override
    public void loadData(int page) {
        getObservableByCategory(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BlockInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BlockInfo blockInfo) {
                        if (blockInfo == null) {
                            mPresenter.fail(ResUtil.getString(R.string.load_fail));
                        }
                        if (blockInfo != null && blockInfo.getItemsPage() != null && blockInfo.getItemsContent() != null) {
                            mPresenter.loadData(blockInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();

                        if (message.contains("404")) {
                            mPresenter.fail(ResUtil.getString(R.string.load_end));
                        } else {
                            mPresenter.requestError();
                            mPresenter.fail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<BlockInfo> getObservableByCategory(int page) {
        switch (mCategory) {
            case "书籍":
                return ApiServices.getAPIs().getBlockBooks(page);
            case "电影":
                return ApiServices.getAPIs().getBlockMovies(page);
            case "散文":
                return ApiServices.getAPIs().getBlockProses(page);
            case "动漫":
                return ApiServices.getAPIs().getBlockCartoons(page);
            case "电视剧":
                return ApiServices.getAPIs().getBlockTeleplays(page);
            case "古诗词":
                return ApiServices.getAPIs().getBlockPoetries(page);
            default:
                return null;
        }
    }

}
