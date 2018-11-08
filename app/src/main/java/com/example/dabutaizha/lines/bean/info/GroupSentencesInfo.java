package com.example.dabutaizha.lines.bean.info;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 句子组的模型（仅用于收藏展示）
 * Created by dabutaizha on 2018/4/8 下午1:24.
 */

public class GroupSentencesInfo extends SectionEntity<SearchInfo.SentencesItem> {

    private String mFirstWord;

    public GroupSentencesInfo(boolean isHeader, String header, String firstWord) {
        super(isHeader, header);
        mFirstWord = firstWord;
    }

    public GroupSentencesInfo(SearchInfo.SentencesItem item) {
        super(item);
    }

    public String getFirstWord() {
        return mFirstWord;
    }

    public void setFirstWord(String mFirstWord) {
        this.mFirstWord = mFirstWord;
    }
}
