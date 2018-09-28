package com.example.dabutaizha.lines.provider;

import com.example.dabutaizha.lines.bean.SearchInfo;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/9/28 下午2:15.
 */

public interface QueryCallback {

    void onSucceed(SearchInfo.SentencesItem item);

}
