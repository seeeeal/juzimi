package com.example.dabutaizha.lines;

import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.bean.SentencesModel;


/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/6 下午1:00.
 */

public class SentenceUtil {

    public static SearchInfo.SentencesItem completeSentence(SearchInfo.SentencesItem sentenceItem) {

        sentenceItem.setArticle(sentenceItem.getArticle() == null ? "未知" : sentenceItem.getArticle());
        sentenceItem.setWriter(sentenceItem.getWriter() == null ? "佚名" : sentenceItem.getWriter());
        sentenceItem.setContent(sentenceItem.getContent() == null ? "null" : sentenceItem.getContent());

        return sentenceItem;

    }

    public static SentencesModel item2model(SearchInfo.SentencesItem sentenceItem) {

        SentencesModel model = new SentencesModel();
        model.setArticle(sentenceItem.getArticle());
        model.setCommentCount(sentenceItem.getCommentCount());
        model.setContent(sentenceItem.getContent());
        model.setLike(sentenceItem.getLike());
        model.setPublisher(sentenceItem.getPublisher());
        model.setSentencesId(Long.valueOf(sentenceItem.getSentencesId()));
        model.setWriter(sentenceItem.getWriter());
        model.setDate(System.currentTimeMillis());

        return model;

    }

    public static SearchInfo.SentencesItem model2item(SentencesModel sentencesModel) {

        SearchInfo.SentencesItem item = new SearchInfo.SentencesItem();
        item.setSentencesId(String.valueOf(sentencesModel.getSentencesId()));
        item.setArticle(sentencesModel.getArticle());
        item.setWriter(sentencesModel.getWriter());
        item.setContent(sentencesModel.getContent());
        item.setDate(sentencesModel.getDate());
        item.setLike(sentencesModel.getLike());
        item.setPublisher(sentencesModel.getPublisher());
        item.setCommentCount(sentencesModel.getCommentCount());

        return item;

    }

}
