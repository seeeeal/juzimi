package com.example.dabutaizha.lines;

import com.example.dabutaizha.lines.bean.info.SearchInfo;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/9/28 上午11:42.
 */

public class SentenceItemRegexUtil {

    public static String getFormatItemContent(SearchInfo.SentencesItem item) {
        String content = item.getContent().trim();
        String regex = ".*[a-zA-Z]+.*";
        if (!content.matches(regex)) {
            content = content.replace(" ", "\n");
        }
        return content;
    }

}
