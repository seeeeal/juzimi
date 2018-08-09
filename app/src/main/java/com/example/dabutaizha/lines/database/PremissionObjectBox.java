package com.example.dabutaizha.lines.database;

import com.example.dabutaizha.lines.bean.PremissionModel;
import com.example.dabutaizha.lines.bean.PremissionModel_;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/23 下午10:15.
 */

public class PremissionObjectBox {

    private static PremissionObjectBox mPremissionBoxInstance;
    private Box<PremissionModel> mPremissionBox;

    public static PremissionObjectBox getInstance() {
        if (mPremissionBoxInstance == null) {
            synchronized (PremissionObjectBox.class) {
                if (mPremissionBoxInstance == null) {
                    mPremissionBoxInstance = new PremissionObjectBox();
                }
            }
        }
        return mPremissionBoxInstance;
    }

    private PremissionObjectBox() {
        BoxStore boxStore = ((BaseApplication)BaseApplication.getInstance()).getBoxStore();
        mPremissionBox = boxStore.boxFor(PremissionModel.class);
    }

    /**
     *Description: RxJava封装的方法
     */
    public Observable<List<PremissionModel>> findAllByRxJava() {
        return Observable.create((ObservableOnSubscribe<List<PremissionModel>>) emitter -> {
            emitter.onNext(findAll());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> checkIsExistByRxJava(int id) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            emitter.onNext(checkIsExist(id));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> addByRxJava(PremissionModel premissionModel) {
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext(add(premissionModel));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> deleteByRxJava(long id) {
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext(delete(id));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *Description: objectBox原生方法
     */
    private List<PremissionModel> findAll() {
        QueryBuilder<PremissionModel> builder = mPremissionBox.query();
        return builder.build().find();
    }

    private boolean checkIsExist(int objectId) {
        List<PremissionModel> premissionModelList =  mPremissionBox.find(PremissionModel_.__ID_PROPERTY, (long)objectId);
        return (null != premissionModelList && premissionModelList.size() > 0);
    }

    private long add(PremissionModel model) {
        return mPremissionBox.put(model);
    }

    private long delete(long modelId) {
        mPremissionBox.remove(modelId);
        return modelId;
    }

}
