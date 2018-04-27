package com.example.dabutaizha.lines.bean;

import java.util.List;

import me.ghui.fruit.annotations.Pick;
import me.ghui.retrofit.converter.annotations.Html;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : ONE主页获取图片的模型
 * Created by dabutaizha on 2018/1/6 下午7:12.
 */

public class PictureInfo extends BaseInfo {

    @Pick("div.item") @Html
    private List<PictureItem> pictureItems;

    public List<PictureItem> getPictureItems() {
        return pictureItems;
    }

    public void setPictureItems(List<PictureItem> pictureItems) {
        this.pictureItems = pictureItems;
    }

    @Override
    public String toString() {
        return "PictureInfo{" +
                "pictureItems=" + pictureItems +
                '}';
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static class PictureItem {

        @Pick(value = "img.fp-one-imagen", attr = "src")
        private String pictureUrl;

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        @Override
        public String toString() {
            return "PictureItem{" +
                    "pictureUrl='" + pictureUrl + '\'' +
                    '}';
        }
    }

}
