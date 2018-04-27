package com.example.dabutaizha.lines.share;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.bilibili.socialize.share.core.BiliShare;
import com.bilibili.socialize.share.core.BiliShareConfiguration;
import com.bilibili.socialize.share.core.SocializeListeners;
import com.bilibili.socialize.share.core.SocializeMedia;
import com.bilibili.socialize.share.core.shareparam.BaseShareParam;
import com.example.dabutaizha.lines.ImageUtil.GlideImageDownloader;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/6 下午3:07.
 */

public final class ShareHelper {
    public static final String QQ_APPID = "";
    public static final String WECHAT_APPID = "wx15a8e86522aca0b7";
    public static final String SINA_APPKEY = "";

    static final String APP_URL = "";
    private AppCompatActivity mContext;
    private Callback mCallback;
    private BaseSharePlatformSelector mPlatformSelector;

    public static ShareHelper instance(AppCompatActivity context, Callback callback) {
        return new ShareHelper(context, callback);
    }

    private ShareHelper(AppCompatActivity context, Callback callback) {
        mContext = context;
        mCallback = callback;
        if (context == null) {
            throw new NullPointerException();
        }
        BiliShareConfiguration configuration = new BiliShareConfiguration.Builder(context)
                .imageDownloader(new GlideImageDownloader())
                .qq(QQ_APPID)
                .weixin(WECHAT_APPID)
                .sina(SINA_APPKEY, null, null)
                .build();
        shareClient().config(configuration);
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public void showShareWarpWindow(View anchor) {
        mPlatformSelector = new PopWrapSharePlatformSelector(mContext, anchor, this::onShareSelectorDismiss, mShareItemClick);
        mPlatformSelector.show();
    }

    public void onShareSelectorDismiss() {
        mCallback.onDismiss(this);
    }

    public void hideShareWindow() {
        if (mPlatformSelector != null) {
            mPlatformSelector.dismiss();
        }
    }

    private AdapterView.OnItemClickListener mShareItemClick = (parent, view, position, id) -> {
        BaseSharePlatformSelector.ShareTarget item = (BaseSharePlatformSelector.ShareTarget) parent.getItemAtPosition(position);
        ShareHelper.this.shareTo(item);
        ShareHelper.this.hideShareWindow();
    };

    public void shareTo(BaseSharePlatformSelector.ShareTarget item) {
        BaseShareParam content = mCallback.getShareContent(ShareHelper.this, item.media);
        if (content == null) {
            return;
        }
        shareClient().share(mContext, item.media, content, shareListener);
    }

    protected SocializeListeners.ShareListener shareListener = new SocializeListeners.ShareListenerAdapter() {

        @Override
        public void onStart(SocializeMedia type) {
            if (mCallback != null) {
                mCallback.onShareStart(ShareHelper.this);
            }
        }

        @Override
        protected void onComplete(SocializeMedia type, int code, Throwable error) {
            if (mCallback != null) {
                mCallback.onShareComplete(ShareHelper.this, code);
            }
        }
    };

    public Context getContext() {
        return mContext;
    }

    public void reset() {
        if (mPlatformSelector != null) {
            mPlatformSelector.release();
            mPlatformSelector = null;
        }
        mShareItemClick = null;
    }

    public static BiliShare shareClient()  {
        return BiliShare.global();
    }

    public interface Callback {
        BaseShareParam getShareContent(ShareHelper helper, SocializeMedia target);

        void onShareStart(ShareHelper helper);

        void onShareComplete(ShareHelper helper, int code);

        void onDismiss(ShareHelper helper);
    }

}
