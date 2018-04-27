package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.SearchInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午8:19.
 */

public class SentencesAdapter extends BaseQuickAdapter<SearchInfo.SentencesItem, BaseViewHolder> {

    public SentencesAdapter(@Nullable List<SearchInfo.SentencesItem> data) {
        super(R.layout.search_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.SentencesItem item) {
        String title = item.getArticle() == null ? "未知" : item.getArticle();
        helper.setText(R.id.search_item_title, title);

        // 把中文字符串的空格改为换行
        String content = item.getContent().trim();
        String regex = ".*[a-zA-Z]+.*";
        if (!content.matches(regex)) {
            content = content.replace(" ", "\n");
        }
        helper.setText(R.id.search_item_content, content);

        String likeCount = item.getCommentCount() == null ? "0" : item.getLike();
        helper.setText(R.id.search_item_like_count, likeCount);

        String writer = item.getWriter() == null ? "佚名" : item.getWriter();
        helper.setText(R.id.search_item_writer, writer);

        String publisher = item.getPublisher() == null ? "佚名" : item.getPublisher();
        helper.setText(R.id.search_item_publisher, publisher);
    }
}
