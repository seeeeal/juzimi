package com.example.dabutaizha.lines.mvp.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;
import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/11/8 下午4:57.
 */

public class CollectionGroupBaseAdapter extends BaseSectionQuickAdapter<GroupSentencesInfo, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public CollectionGroupBaseAdapter(int layoutResId, int sectionHeadResId, List<GroupSentencesInfo> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, GroupSentencesInfo item) {
        helper.setText(R.id.collection_group_title, item.header);
        helper.setText(R.id.collection_group_first_word, item.getFirstWord());
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupSentencesInfo item) {
        helper.setText(R.id.item_writer, item.t.getWriter());

        String content = SentenceItemRegexUtil.getFormatItemContent(item.t);
        helper.setText(R.id.item_content, content);

        helper.addOnClickListener(R.id.item_collect_bg);
    }

}
