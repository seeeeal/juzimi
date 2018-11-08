package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.info.BlockInfoItem;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午5:01.
 */

public class CategoryAdapter extends BaseMultiItemQuickAdapter<BlockInfoItem, BaseViewHolder> {

    public CategoryAdapter(@Nullable List<BlockInfoItem> data) {
        super(data);
        addItemType(Constant.DAY_TIME, R.layout.category_item);
        addItemType(Constant.NIGHT, R.layout.category_item_night);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockInfoItem item) {
        ImageView imageView = helper.getView(R.id.category_item_img);
        ImageLoader.loadRoundByUrl(imageView.getContext(), imageView, item.getmBlockItemPage().getTitlePageUrl());

        String title = item.getmBlockItemContent().getTitle() == null ? "未知" : item.getmBlockItemContent().getTitle();
        helper.setText(R.id.category_item_title, title);

        String author = item.getmBlockItemContent().getAuthor() == null ? "佚名" : item.getmBlockItemContent().getAuthor();
        helper.setText(R.id.category_item_author, author);

        helper.setText(R.id.category_item_content, item.getmBlockItemContent().getContent());
    }
}
