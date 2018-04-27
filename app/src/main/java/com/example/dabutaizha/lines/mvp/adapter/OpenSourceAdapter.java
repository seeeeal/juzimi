package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.OpenSourceInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/5 下午4:11.
 */

public class OpenSourceAdapter extends BaseQuickAdapter<OpenSourceInfo, BaseViewHolder> {

    public OpenSourceAdapter(@Nullable List<OpenSourceInfo> data) {
        super(R.layout.open_source_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OpenSourceInfo item) {
        helper.setText(R.id.oc_title, item.getOpenSourceTitle());
        helper.setText(R.id.oc_author, item.getOpenSourceAuthor());
        helper.setText(R.id.oc_intro, item.getOpenSourceIntro());
    }
}
