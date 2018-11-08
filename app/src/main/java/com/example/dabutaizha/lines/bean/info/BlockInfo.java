package com.example.dabutaizha.lines.bean.info;

import com.example.dabutaizha.lines.Constant;

import java.util.List;

import me.ghui.fruit.annotations.Pick;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 板块页模型
 * Created by dabutaizha on 2018/1/4 下午7:57.
 */

public class BlockInfo extends BaseInfo {

    @Pick("div.views-field-tid")
    private List<BlockItemPage> itemsPage;

    @Pick("div.views-field-phpcode")
    private List<BlockItemContent> itemsContent;

    public List<BlockItemPage> getItemsPage() {
        return itemsPage;
    }

    public void setItemsPage(List<BlockItemPage> itemsPage) {
        this.itemsPage = itemsPage;
    }

    public List<BlockItemContent> getItemsContent() {
        return itemsContent;
    }

    public void setItemsContent(List<BlockItemContent> itemsContent) {
        this.itemsContent = itemsContent;
    }

    @Override
    public String toString() {
        return "BlockInfo{" +
                "itemsPage=" + itemsPage +
                "\n, itemsContent=" + itemsContent +
                '}';
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static class BlockItemPage {

        @Pick(value = "div.views-field-tid > a > img", attr = "src")
        private String titlePageUrl;

        public String getTitlePageUrl() {
            return Constant.HTTP_HEAD + titlePageUrl;
        }

        public void setTitlePageUrl(String titlePageUrl) {
            this.titlePageUrl = titlePageUrl;
        }

        @Override
        public String toString() {
            return "BlockItemPage{" +
                    "titlePageUrl='" + getTitlePageUrl() + '\'' +
                    '}';
        }
    }

    public static class BlockItemContent {

        @Pick(value = "span.xqallarticletilelinkspan > a")
        private String title;

        @Pick(value = "div.views-field-phpcode > a")
        private String author;

        @Pick(value = "div.xqagepawirdesc")
        private String content;

        @Pick(value = "div.xqagepawirdesclink > a", attr = "href")
        private String hyperLink;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHyperLink() {
            return Constant.BASE_URL + hyperLink;
        }

        public void setHyperLink(String hyperLink) {
            this.hyperLink = hyperLink;
        }

        @Override
        public String toString() {
            return "BlockItemContent{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", hyperLink='" + hyperLink + '\'' +
                    '}';
        }
    }
}
