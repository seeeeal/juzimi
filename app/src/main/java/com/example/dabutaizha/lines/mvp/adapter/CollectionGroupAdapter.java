package com.example.dabutaizha.lines.mvp.adapter;

import android.content.Context;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/8 下午1:38.
 */

public class CollectionGroupAdapter extends CollectionGroupBaseAdapter {

    public CollectionGroupAdapter(List<GroupSentencesInfo> data) {
        super(R.layout.collection_group_content_item, R.layout.collection_group_header_item, data);
    }

}
