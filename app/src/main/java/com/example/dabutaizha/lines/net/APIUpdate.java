package com.example.dabutaizha.lines.net;

import com.example.dabutaizha.lines.bean.info.VersionInfo;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/26 下午1:28.
 */

public interface APIUpdate {

    /**
     *Description: 用于获取最新的正式版本号
     */
    @GET("/apk/com.example.dabutaizha.lines") @Html
    Observable<VersionInfo> getRecentVersion();

}
