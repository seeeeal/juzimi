package com.example.dabutaizha.lines.bean;

import java.util.List;

import me.ghui.fruit.annotations.Pick;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class CollectionInfo extends BaseInfo {

    @Override
    public boolean isValid() {
        return true;
    }

    @Pick("div#contentinside")
    private CollectionSentencesInfo mCollectionSentencesInfo;

    public CollectionSentencesInfo getmCollectionSentencesInfo() {
        return mCollectionSentencesInfo;
    }

    public void setmCollectionSentencesInfo(CollectionSentencesInfo mCollectionSentencesInfo) {
        this.mCollectionSentencesInfo = mCollectionSentencesInfo;
    }

    @Override
    public String toString() {
        return "CollectionInfo{" +
                "mCollectionSentencesInfo=" + mCollectionSentencesInfo +
                '}';
    }

    public static class CollectionSentencesInfo {

        @Pick("div.views-field-phpcode")
        private List<SearchInfo.SentencesItem> sentencesItems;

        public List<SearchInfo.SentencesItem> getSentencesItems() {
            return sentencesItems;
        }

        public void setSentencesItems(List<SearchInfo.SentencesItem> sentencesItems) {
            this.sentencesItems = sentencesItems;
        }

        @Override
        public String toString() {
            return "CollectionSentencesInfo{" +
                    "sentencesItems=" + sentencesItems +
                    '}';
        }
    }

}
