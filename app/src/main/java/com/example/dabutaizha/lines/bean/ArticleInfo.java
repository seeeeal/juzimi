package com.example.dabutaizha.lines.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.dabutaizha.lines.Constant;

import java.io.Serializable;
import java.util.List;

import me.ghui.fruit.annotations.Pick;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 作品页模型
 * Created by dabutaizha on 2018/1/6 下午7:53.
 */

public class ArticleInfo extends BaseInfo {

    @Override
    public boolean isValid() {
        return true;
    }

    @Pick("div.views-field-phpcode")
    private List<SearchInfo.SentencesItem> sentencesItems;

    @Pick(value = "span.xqwridesczuopinlinkspan > a")
    private List<RelatedWork> relatedWorks;

    @Pick(value = "div.views-row.views-row-1.views-row-odd.views-row-first.views-row-last > div.views-field-tid > img", attr = "src")
    private String titlePageUrl;

    @Pick(value = "div.views-row.views-row-1.views-row-odd.views-row-first.views-row-last > div.views-field-name > strong")
    private String title;

    @Pick(value = "div.views-row.views-row-1.views-row-odd.views-row-first.views-row-last > div.views-field-phpcode-1 > a")
    private String author;

    @Pick(value = "div.wridesccon")
    private String intro;

    public List<SearchInfo.SentencesItem> getSentencesItems() {
        return sentencesItems;
    }

    public void setSentencesItems(List<SearchInfo.SentencesItem> sentencesItems) {
        this.sentencesItems = sentencesItems;
    }

    public String getTitlePageUrl() {
        return Constant.HTTP_HEAD + titlePageUrl;
    }

    public void setTitlePageUrl(String titlePageUrl) {
        this.titlePageUrl = titlePageUrl;
    }

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

    public List<RelatedWork> getRelatedWorks() {
        return relatedWorks;
    }

    public void setRelatedWorks(List<RelatedWork> relatedWorks) {
        this.relatedWorks = relatedWorks;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "sentencesItems=" + sentencesItems +
                ", titlePageUrl='" + getTitlePageUrl() + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", relatedWorks=" + relatedWorks +
                ", intro='" + intro + '\'' +
                '}';
    }

    public static class RelatedWork implements Parcelable {

        @Pick()
        private String relatedWorkTitle;

        @Pick(attr = "href")
        private String relatedWorkLink;

        public RelatedWork() {
        }

        public String getRelatedWorkTitle() {
            return relatedWorkTitle;
        }

        public void setRelatedWorkTitle(String relatedWorkTitle) {
            this.relatedWorkTitle = relatedWorkTitle;
        }

        public String getRelatedWorkLink() {
            return Constant.BASE_URL + relatedWorkLink;
        }

        public void setRelatedWorkLink(String relatedWorkLink) {
            this.relatedWorkLink = relatedWorkLink;
        }

        @Override
        public String toString() {
            return "RelatedWork{" +
                    "relatedWorkTitle='" + relatedWorkTitle + '\'' +
                    ", relatedWorkLink='" + getRelatedWorkLink() + '\'' +
                    '}';
        }

        /**
         *Description: Parcelable
         */
        protected RelatedWork(Parcel in) {
            relatedWorkTitle = in.readString();
            relatedWorkLink = in.readString();
        }

        public static final Creator<RelatedWork> CREATOR = new Creator<RelatedWork>() {
            @Override
            public RelatedWork createFromParcel(Parcel in) {
                RelatedWork relatedWork = new RelatedWork();
                relatedWork.setRelatedWorkTitle(in.readString());
                relatedWork.setRelatedWorkLink(in.readString());
                return relatedWork;
            }

            @Override
            public RelatedWork[] newArray(int size) {
                return new RelatedWork[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(relatedWorkTitle);
            parcel.writeString(relatedWorkLink);
        }
    }
}
