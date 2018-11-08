package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午8:19.
 */

public class SentencesAdapter extends BaseMultiItemQuickAdapter<SearchInfo.SentencesItem, BaseViewHolder> {

    public SentencesAdapter(@Nullable List<SearchInfo.SentencesItem> data) {
        super(data);
        addItemType(Constant.DAY_TIME, R.layout.search_item);
        addItemType(Constant.NIGHT, R.layout.search_item_night);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.SentencesItem item) {
        String title = item.getArticle() == null ? "未知" : item.getArticle();
        helper.setText(R.id.search_item_title, title);

        String content = SentenceItemRegexUtil.getFormatItemContent(item);

        helper.setText(R.id.search_item_content, content);

        String likeCount = item.getCommentCount() == null ? "0" : item.getLike();
        helper.setText(R.id.search_item_like_count, likeCount);

        String writer = item.getWriter() == null ? "佚名" : item.getWriter();
        helper.setText(R.id.search_item_writer, writer);

        String publisher = item.getPublisher() == null ? "佚名" : item.getPublisher();
        helper.setText(R.id.search_item_publisher, publisher);
    }
}
