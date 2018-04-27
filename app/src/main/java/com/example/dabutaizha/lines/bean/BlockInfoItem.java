package com.example.dabutaizha.lines.bean;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午3:15.
 */

public class BlockInfoItem {

    private BlockInfo.BlockItemPage mBlockItemPage;

    private BlockInfo.BlockItemContent mBlockItemContent;

    public BlockInfoItem(BlockInfo.BlockItemPage mBlockItemPage, BlockInfo.BlockItemContent mBlockItemContent) {
        this.mBlockItemPage = mBlockItemPage;
        this.mBlockItemContent = mBlockItemContent;
    }

    public BlockInfo.BlockItemPage getmBlockItemPage() {
        return mBlockItemPage;
    }

    public void setmBlockItemPage(BlockInfo.BlockItemPage mBlockItemPage) {
        this.mBlockItemPage = mBlockItemPage;
    }

    public BlockInfo.BlockItemContent getmBlockItemContent() {
        return mBlockItemContent;
    }

    public void setmBlockItemContent(BlockInfo.BlockItemContent mBlockItemContent) {
        this.mBlockItemContent = mBlockItemContent;
    }

    @Override
    public String toString() {
        return "BlockInfoItem{" +
                "mBlockItemPage=" + mBlockItemPage +
                ", mBlockItemContent=" + mBlockItemContent +
                '}';
    }

}
