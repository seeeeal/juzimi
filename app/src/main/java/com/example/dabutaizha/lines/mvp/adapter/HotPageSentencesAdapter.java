package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 上午11:50.
 */

public class HotPageSentencesAdapter extends BaseQuickAdapter<SearchInfo.SentencesItem, BaseViewHolder> {

    public HotPageSentencesAdapter(@Nullable List<SearchInfo.SentencesItem> data) {
        super(R.layout.sentence_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.SentencesItem item) {
        String article = item.getArticle() == null ? "未知" : item.getArticle();
        String writer = item.getWriter() == null ? "佚名" : item.getWriter();

        helper.setText(R.id.item_article, article);
        helper.setText(R.id.item_writer, writer);

        String content = SentenceItemRegexUtil.getFormatItemContent(item);
        helper.setText(R.id.item_content, content);
    }
}
