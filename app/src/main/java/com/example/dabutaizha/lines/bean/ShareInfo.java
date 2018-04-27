package com.example.dabutaizha.lines.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午5:59.
 */

public class ShareInfo implements Parcelable {

    private String mTitle;

    private String mAuthor;

    private String mContent;

    public ShareInfo() {

    }

    public ShareInfo(String mTitle, String mAuthor, String mContent) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    @Override
    public String toString() {
        return "ShareInfo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mContent='" + mContent + '\'' +
                '}';
    }

    /**
     *Description: Parcelable
     */
    protected ShareInfo(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
    }

    public static final Creator<ShareInfo> CREATOR = new Creator<ShareInfo>() {
        @Override
        public ShareInfo createFromParcel(Parcel in) {
            ShareInfo shareInfo = new ShareInfo();
            shareInfo.setmTitle(in.readString());
            shareInfo.setmAuthor(in.readString());
            shareInfo.setmContent(in.readString());
            return shareInfo;
        }

        @Override
        public ShareInfo[] newArray(int size) {
            return new ShareInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
    }
}
