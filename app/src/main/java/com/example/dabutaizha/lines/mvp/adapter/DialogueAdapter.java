package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.DialogueInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/21 下午4:00.
 */

public class DialogueAdapter extends BaseQuickAdapter<DialogueInfo.DialogueItem, BaseViewHolder> {

    public DialogueAdapter(@Nullable List<DialogueInfo.DialogueItem> data) {
        super(R.layout.dialogue_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DialogueInfo.DialogueItem item) {
        ImageView dialogueImageView = helper.getView(R.id.dialogue_img);
        ImageLoader.loadImageByUrl(dialogueImageView.getContext(), dialogueImageView, item.getmDialogueItemUrl());
    }

}
