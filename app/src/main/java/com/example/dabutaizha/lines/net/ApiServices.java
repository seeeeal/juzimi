package com.example.dabutaizha.lines.net;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import me.ghui.fruit.Fruit;
import me.ghui.fruit.converter.retrofit.FruitConverterFactory;
import me.ghui.retrofit.converter.GlobalConverterFactory;
import me.ghui.retrofit.converter.annotations.Html;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2017/12/4 下午7:51.
 */

public class ApiServices {

    private static final long TIMEOUT_LENGTH = 15;
    private static APIs mApi;
    private static APIAssistant mAPIAssistant;
    private static APIUpdate mAPIUpdate;
    private static Fruit mFruit;
    private static OkHttpClient mOkHttpClient;

    public static void init() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addConverterFactory(GlobalConverterFactory.create()
                    .add(FruitConverterFactory.create(getFruit()), Html.class))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_URL)
                    .build();
            mApi = retrofit.create(APIs.class);
        }
    }

    public static void initAPIAssistant() {
        if (mAPIAssistant == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addConverterFactory(GlobalConverterFactory.create()
                            .add(FruitConverterFactory.create(getFruit()), Html.class))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_URL_ASSISTANT)
                    .build();
            mAPIAssistant = retrofit.create(APIAssistant.class);
        }
    }

    public static void initAPIUpdate() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GlobalConverterFactory.create()
                        .add(FruitConverterFactory.create(getFruit()), Html.class))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL_UPDATE)
                .build();
        mAPIUpdate = retrofit.create(APIUpdate.class);
    }

    public static OkHttpClient getOkHttpClient() {

        LoginUtil.initLoginState();

        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(getCache())
                    .addNetworkInterceptor(new StethoInterceptor())
                    .connectTimeout(TIMEOUT_LENGTH, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    // 修改useragent防止请求失效
                    .addInterceptor(chain -> {
                        Request request = chain.request()
                                .newBuilder()
                                .removeHeader("If-None-Match")
                                .addHeader("If-None-Match", "")
                                .removeHeader("If-Modified-Since")
                                .addHeader("If-Modified-Since", "")
                                .removeHeader("User-Agent")
                                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                .build();
                        return chain.proceed(request);
                    })
                    // cookie本地持久化
                    .addInterceptor(new ReadCookiesInterceptor())
                    .addInterceptor(new SaveCookiesInterceptor())
                    .build();
        }
        return mOkHttpClient;
    }

    public static Fruit getFruit() {
        if (mFruit == null) {
            mFruit = new Fruit();
        }
        return mFruit;
    }

    public static APIs getAPIs() {
        init();
        return mApi;
    }

    public static APIAssistant getAPIAssistant() {
        initAPIAssistant();
        return mAPIAssistant;
    }

    public static APIUpdate getAPIUpdate() {
        initAPIUpdate();
        return mAPIUpdate;
    }

    public static Cache getCache(){
        File httpCacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "okhttp_cache");
        return new Cache(httpCacheDirectory, 10 * 1024 * 1024);         // 缓存空间10M
    }

    //读取Cookie的拦截器
    public static class ReadCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> cookies = LoginUtil.getCookies();
                for (String cookie : cookies) {
                    builder.addHeader("Cookie", cookie);
                }
            return chain.proceed(builder.build());
        }
    }

    //存储Cookie的拦截器
    public static class SaveCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }
                LoginUtil.setCookies(cookies);
            }

            return originalResponse;
        }
    }
}
