package com.example.dabutaizha.lines.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
@Entity
public class SentencesModel implements Serializable {

    @Id(assignable = true)
    private long sentencesId;

    @NameInDb("CONTENT")
    private String content;

    @NameInDb("WRITER")
    private String writer;

    @NameInDb("ARTICLE")
    private String article;

    @NameInDb("LIKE")
    private String like;

    @NameInDb("COMMENT_COUNT")
    private String commentCount;

    @NameInDb("PUBLISHER")
    private String publisher;

//    @NameInDb("IS_COLLECT")
//    private boolean isCollect;

//    @NameInDb("CARD_PATH")
//    private String cardPath;

    @NameInDb("DATE")
    private long date;

    public SentencesModel() {

    }

    public SentencesModel(int sentencesId, String content, String writer, String article, String like, String commentCount, String publisher, long date) {
        this.sentencesId = sentencesId;
        this.content = content;
        this.writer = writer;
        this.article = article;
        this.like = like;
        this.commentCount = commentCount;
        this.publisher = publisher;
        this.date = date;
    }

    public long getSentencesId() {
        return sentencesId;
    }

    public void setSentencesId(long sentencesId) {
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
        return like;
    }

    public void setLike(String like) {
        this.like = like;
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
        return "SentencesModel{" +
                "sentencesId='" + sentencesId + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", article='" + article + '\'' +
                ", like='" + like + '\'' +
                ", commentCount='" + commentCount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", date=" + date +
                '}';
    }
}
