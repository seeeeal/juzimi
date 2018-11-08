package com.example.dabutaizha.lines.bean;

import java.io.Serializable;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 上午10:53.
 */

public class AppThemeBean implements Serializable {

    private int mId;
    private String mName;
    private int mCardBackground;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getCardBackground() {
        return mCardBackground;
    }

    public void setCardBackground(int cardBackground) {
        this.mCardBackground = cardBackground;
    }

    @Override
    public String toString() {
        return "AppThemeBean{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mCardBackground=" + mCardBackground +
                '}';
    }
}
