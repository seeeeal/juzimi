package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 上午11:50.
 */

public class HotPageSentencesAdapter extends BaseMultiItemQuickAdapter<SearchInfo.SentencesItem, BaseViewHolder> {

    public HotPageSentencesAdapter(@Nullable List<SearchInfo.SentencesItem> data) {
        super(data);
        addItemType(Constant.DAY_TIME, R.layout.sentence_item);
        addItemType(Constant.NIGHT, R.layout.sentent_item_night);
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
