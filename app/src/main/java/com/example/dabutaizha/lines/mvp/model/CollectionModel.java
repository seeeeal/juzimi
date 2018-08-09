package com.example.dabutaizha.lines.mvp.model;

import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.contract.CollectionActivityContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class CollectionModel implements CollectionActivityContract.Model {

    private CollectionActivityContract.Presenter mPresenter;

    public CollectionModel(CollectionActivityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getUserCollectData() {
        SentencesObjectBox
                .getInstance()
                .findAllByRxJava()
                .subscribe(new Observer<List<SentencesModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SentencesModel> sentencesModels) {
                        if (sentencesModels == null) {
                            List<SentencesModel> models = new ArrayList<>();
                            mPresenter.refreshCollectList(models);
                        }
                        if (sentencesModels != null && sentencesModels.size() >= 0) {
                            mPresenter.refreshCollectList(sentencesModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

