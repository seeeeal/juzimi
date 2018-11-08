package com.example.dabutaizha.lines.mvp.adapter;

import android.content.Context;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/11/8 下午4:44.
 */

public class CollectionGroupNightAdapter extends CollectionGroupBaseAdapter {

    public CollectionGroupNightAdapter(List<GroupSentencesInfo> data) {
        super(R.layout.collection_group_content_item_night, R.layout.collection_group_header_item_night, data);
    }

}
