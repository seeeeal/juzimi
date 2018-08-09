package com.example.dabutaizha.lines.database;


import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.bean.SentencesModel_;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class SentencesObjectBox {

    private static SentencesObjectBox mSentencesBoxInstance;
    private Box<SentencesModel> mSentencesBox;

    public static SentencesObjectBox getInstance() {
        if (mSentencesBoxInstance == null) {
            synchronized (SentencesObjectBox.class) {
                if (mSentencesBoxInstance == null) {
                    mSentencesBoxInstance = new SentencesObjectBox();
                }
            }
        }
        return mSentencesBoxInstance;
    }

    private SentencesObjectBox() {
        BoxStore boxStore = ((BaseApplication)BaseApplication.getInstance()).getBoxStore();
        mSentencesBox = boxStore.boxFor(SentencesModel.class);
    }

    /**
     *Description: RxJava封装的方法
     */
    public Observable<List<SentencesModel>> findAllByRxJava() {
        return Observable.create((ObservableOnSubscribe<List<SentencesModel>>) emitter -> {
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

    public Observable<Long> addByRxJava(SentencesModel sentencesModel) {
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext(add(sentencesModel));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> deleteByRxJava(long id) {
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext(delete(id));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SentencesModel> getSentenceByRxJava(long id) {
        return Observable.create((ObservableOnSubscribe<SentencesModel>) emitter -> {
            emitter.onNext(getSentenceById(id));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *Description: objectBox原生方法
     */
    private List<SentencesModel> findAll() {
        Collator collator = Collator.getInstance(java.util.Locale.CHINA);
        QueryBuilder<SentencesModel> builder = mSentencesBox.query().sort(new Comparator<SentencesModel>() {
            @Override
            public int compare(SentencesModel model, SentencesModel t1) {
                return collator.compare(model.getArticle(), t1.getArticle());
            }
        });
        return builder.build().find();
    }


    private SentencesModel getSentenceById(long sentenceId) {
        return mSentencesBox.get(sentenceId);
    }

    private boolean checkIsExist(int sentenceId) {
        List<SentencesModel> sentence =  mSentencesBox.find(SentencesModel_.__ID_PROPERTY, (long)sentenceId);
        return (null != sentence && sentence.size() > 0);
    }

    private long add(SentencesModel model) {
        return mSentencesBox.put(model);
    }

    private long delete(long modelId) {
        mSentencesBox.remove(modelId);
        return modelId;
    }

}
