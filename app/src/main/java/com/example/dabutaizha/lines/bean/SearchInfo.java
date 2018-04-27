package com.example.dabutaizha.lines.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.dabutaizha.lines.Constant;

import java.util.List;

import me.ghui.fruit.annotations.Pick;
import retrofit2.http.POST;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description : 搜索页模型
 * Created by dabutaizha on 2017/12/21 下午7:39.
 */

public class SearchInfo extends BaseInfo {

    @Pick("div.views-field-phpcode")
    private List<SentencesItem> sentencesItems;

    public List<SentencesItem> getSentencesItems() {
        return sentencesItems;
    }

    public void setSentencesItems(List<SentencesItem> sentencesItems) {
        this.sentencesItems = sentencesItems;
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
                "sentencesItems=" + sentencesItems +
                '}';
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static class SentencesItem implements Parcelable {

        @Pick(value = "a.xlistju", attr = "href")
        private String sentencesId;

        @Pick(value = "a.xlistju")
        private String content;

        @Pick(value = "div.xqjulistwafo > a")
        private String writer;

        @Pick(value = "span.views-field-field-oriarticle-value > a")
        private String article;

        @Pick(value = "div.views-field-ops > a")
        private String like;

        @Pick(value = "div.views-field-ops > a", attr = "href")
        private String likeLink;

        @Pick(value = "div.views-field-comment-count > a")
        private String commentCount;

        @Pick(value = "div.views-field-xqname > a")
        private String publisher;

        private long date;

        public SentencesItem() {

        }

        public SentencesItem(String sentencesId, String content, String writer, String article, String like, String likeLink, String commentCount, String publisher, long date) {
            this.sentencesId = sentencesId;
            this.content = content;
            this.writer = writer;
            this.article = article;
            this.like = like;
            this.likeLink = likeLink;
            this.commentCount = commentCount;
            this.publisher = publisher;
            this.date = date;
        }

        public String getSentencesId() {
            return sentencesId.replace("/ju/", "");
        }

        public void setSentencesId(String sentencesId) {
            this.sentencesId = sentencesId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }

        public String getLike() {
            return like.replace("喜欢", "热度");
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getLikeLink() {
            return Constant.BASE_URL + likeLink;
        }

        public void setLikeLink(String likeLink) {
            this.likeLink = likeLink;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "SentencesItem{" +
                    "sentencesId='" + sentencesId + '\'' +
                    ", content='" + content + '\'' +
                    ", writer='" + writer + '\'' +
                    ", article='" + article + '\'' +
                    ", like='" + like + '\'' +
                    ", likeLink='" + likeLink + '\'' +
                    ", commentCount='" + commentCount + '\'' +
                    ", publisher='" + publisher + '\'' +
                    ", date=" + date +
                    '}';
        }

        /**
         *Description: Parcelable
         */
        protected SentencesItem(Parcel in) {
            sentencesId = in.readString();
            content = in.readString();
            writer = in.readString();
            article = in.readString();
            like = in.readString();
            likeLink = in.readString();
            commentCount = in.readString();
            publisher = in.readString();
            date = in.readLong();
        }

        public static final Creator<SentencesItem> CREATOR = new Creator<SentencesItem>() {
            @Override
            public SentencesItem createFromParcel(Parcel in) {
                SentencesItem sentencesItem = new SentencesItem();
                sentencesItem.setSentencesId(in.readString());
                sentencesItem.setContent(in.readString());
                sentencesItem.setWriter(in.readString());
                sentencesItem.setArticle(in.readString());
                sentencesItem.setLike(in.readString());
                sentencesItem.setLikeLink(in.readString());
                sentencesItem.setCommentCount(in.readString());
                sentencesItem.setPublisher(in.readString());
                sentencesItem.setDate(in.readLong());
                return sentencesItem;
            }

            @Override
            public SentencesItem[] newArray(int size) {
                return new SentencesItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(sentencesId);
            parcel.writeString(content);
            parcel.writeString(writer);
            parcel.writeString(article);
            parcel.writeString(like);
            parcel.writeString(likeLink);
            parcel.writeString(commentCount);
            parcel.writeString(publisher);
            parcel.writeLong(date);
        }
    }
}
