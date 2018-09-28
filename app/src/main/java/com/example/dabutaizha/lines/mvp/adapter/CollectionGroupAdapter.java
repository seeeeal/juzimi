package com.example.dabutaizha.lines.mvp.adapter;

import android.content.Context;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.GroupSentencesInfo;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/8 下午1:38.
 */

public class CollectionGroupAdapter extends BaseSectionQuickAdapter<GroupSentencesInfo, BaseViewHolder> {

    private Context mContext;

    public CollectionGroupAdapter(Context context, List<GroupSentencesInfo> data) {
        super(R.layout.collection_group_content_item, R.layout.collection_group_header_item, data);
        mContext = context;
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
