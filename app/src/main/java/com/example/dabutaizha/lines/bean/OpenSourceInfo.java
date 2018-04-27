package com.example.dabutaizha.lines.bean;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/2 下午12:00.
 */

public class OpenSourceInfo {

    private String openSourceTitle;

    private String openSourceAuthor;

    private String openSourceIntro;

    private String openSourceLink;

    public OpenSourceInfo() {
    }

    public OpenSourceInfo(String openSourceTitle, String openSourceAuthor, String openSourceIntro, String openSourceLink) {
        this.openSourceTitle = openSourceTitle;
        this.openSourceAuthor = openSourceAuthor;
        this.openSourceIntro = openSourceIntro;
        this.openSourceLink = openSourceLink;
    }

    public String getOpenSourceTitle() {
        return openSourceTitle;
    }

    public void setOpenSourceTitle(String openSourceTitle) {
        this.openSourceTitle = openSourceTitle;
    }

    public String getOpenSourceAuthor() {
        return openSourceAuthor;
    }

    public void setOpenSourceAuthor(String openSourceAuthor) {
        this.openSourceAuthor = openSourceAuthor;
    }

    public String getOpenSourceIntro() {
        return openSourceIntro;
    }

    public void setOpenSourceIntro(String openSourceIntro) {
        this.openSourceIntro = openSourceIntro;
    }

    public String getOpenSourceLink() {
        return openSourceLink;
    }

    public void setOpenSourceLink(String openSourceLink) {
        this.openSourceLink = openSourceLink;
    }

    @Override
    public String toString() {
        return "OpenSourceInfo{" +
                "openSourceTitle='" + openSourceTitle + '\'' +
                ", openSourceAuthor='" + openSourceAuthor + '\'' +
                ", openSourceIntro='" + openSourceIntro + '\'' +
                ", openSourceLink='" + openSourceLink + '\'' +
                '}';
    }
}
