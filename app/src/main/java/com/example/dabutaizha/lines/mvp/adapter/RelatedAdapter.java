package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.ArticleInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午2:14.
 */

public class RelatedAdapter extends BaseQuickAdapter<ArticleInfo.RelatedWork, BaseViewHolder> {


    public RelatedAdapter(@Nullable List<ArticleInfo.RelatedWork> data) {
        super(R.layout.related_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleInfo.RelatedWork item) {
        Log.d("DFFF", "enter convert");
        if (item != null) {
            helper.setText(R.id.related_title, item.getRelatedWorkTitle());
        }
    }
}
