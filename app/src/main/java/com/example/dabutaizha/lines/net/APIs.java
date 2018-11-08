package com.example.dabutaizha.lines.net;

import com.example.dabutaizha.lines.bean.info.ArticleInfo;
import com.example.dabutaizha.lines.bean.info.BlockInfo;
import com.example.dabutaizha.lines.bean.info.DialogueInfo;
import com.example.dabutaizha.lines.bean.info.SearchInfo;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2017/12/21 下午7:32.
 */

public interface APIs {

    /**
     *Description: 首页今日热门
     */
    @GET("/todayhot") @Html
    Observable<SearchInfo> getHotPageTodayHot(@Query("page") int page);

    /**
     *Description: 首页今日推荐
     */
    @GET("/recommend") @Html
    Observable<SearchInfo> getHotPageRecommend(@Query("page") int page);

    /**
     *Description: 首页最新发布
     */
    @GET("/new") @Html
    Observable<SearchInfo> getHotPageNew(@Query("page") int page);

    /**
     *Description: 首页最受最受欢迎
     */
    @GET("/totallike") @Html
    Observable<SearchInfo> getHotPageLike(@Query("page") int page);

    /**
     *Description: 经典对白
     */
    @GET("/meitumeiju/jingdianduibai") @Html
    Observable<DialogueInfo> getDialoguePage(@Query("page") int page);

    /**
     *Description: 搜索接口(第一页)
     */
    @GET("/search/node/{content}") @Html
    @Headers("Referer:http://www.juzimi.com/search/node/%s/page=-1")
    Observable<SearchInfo> searchSentences(@Path("content") String content);

    /**
     *Description: 搜索接口(>1页)
     */
    @GET("/search/node/{content}") @Html
    @Headers("Referer:http://www.juzimi.com/search/node/%s/page=-1")
    Observable<SearchInfo> searchSentences(@Path("content") String content, @Query("page") int page);

    /**
     *Description: 书籍
     */
    @GET("/books") @Html
    Observable<BlockInfo> getBlockBooks(@Query("page") int page);

    /**
     *Description: 电影台词
     */
    @GET("/allarticle/jingdiantaici") @Html
    Observable<BlockInfo> getBlockMovies(@Query("page") int page);

    /**
     *Description: 散文
     */
    @GET("/allarticle/sanwen") @Html
    Observable<BlockInfo> getBlockProses(@Query("page") int page);

    /**
     *Description: 动漫
     */
    @GET("/allarticle/dongmantaici") @Html
    Observable<BlockInfo> getBlockCartoons(@Query("page") int page);

    /**
     *Description: 电视剧
     */
    @GET("/lianxujutaici") @Html
    Observable<BlockInfo> getBlockTeleplays(@Query("page") int page);

    /**
     *Description: 古诗词
     */
    @GET("/allarticle/guwen") @Html
    Observable<BlockInfo> getBlockPoetries(@Query("page") int page);

    /**
     *Description: 作品详情页
     */
    @GET("/article/{articleId}") @Html
    Observable<ArticleInfo> getArticlePage(@Path("articleId") String articleId,
                                           @Query("page") int page);
}

