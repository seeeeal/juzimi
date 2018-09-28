package com.example.dabutaizha.lines.provider;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.query.Query;
import io.reactivex.functions.Consumer;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/27 下午1:39.
 */

public class WidgetModel {

    public static void saveWidgetTheme(int themeType) {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.WIDGET_THEME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.WIDGET_THEME_TYPE, themeType);
        editor.apply();
    }

    public static int getWidgetTheme() {
        SharedPreferences sp = BaseApplication.getInstance()
                .getSharedPreferences(Constant.WIDGET_THEME, Context.MODE_PRIVATE);

        return sp.getInt(Constant.WIDGET_THEME_TYPE, Constant.THEME_DEFAULT);
    }

    public static void getRandomSentences(QueryCallback callback) {
        SentencesObjectBox
                .getInstance()
                .findAllByRxJava()
                .subscribe(new Consumer<List<SentencesModel>>() {
                    @Override
                    public void accept(List<SentencesModel> sentencesModels) throws Exception {
                        List<SearchInfo.SentencesItem> sentencesItems = getModels2Items(sentencesModels);
                        Random random = new Random();
                        int randomInt = random.nextInt(sentencesItems.size());
                        callback.onSucceed(sentencesItems.get(randomInt));
                    }
                });
    }

    private static List<SearchInfo.SentencesItem> getModels2Items(List<SentencesModel> sentencesModels) {
        List<SearchInfo.SentencesItem> sentencesItems = new ArrayList<>();
        if (sentencesModels != null) {
            for (SentencesModel model : sentencesModels) {
                sentencesItems.add(SentenceUtil.model2item(model));
            }
        }
        return sentencesItems;
    }

}
