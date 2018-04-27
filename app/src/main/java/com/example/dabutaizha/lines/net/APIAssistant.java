package com.example.dabutaizha.lines.net;

import com.example.dabutaizha.lines.bean.PictureInfo;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/6 下午7:10.
 */

public interface APIAssistant {

    /**
     *Description: 用于获取首页所用的图片
     */
    @GET("/") @Html
    Observable<PictureInfo> getHotPagePictures();

}
