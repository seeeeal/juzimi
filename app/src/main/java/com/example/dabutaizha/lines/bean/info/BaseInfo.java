package com.example.dabutaizha.lines.bean.info;

/**
 * Copyright (C) 2017 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2017/12/23 下午3:25.
 */

public abstract class BaseInfo implements IBase {

    public String rawResponse;

    @Override
    public String getResponse() {
        return rawResponse;
    }

    @Override
    public void setResponse(String response) {
        rawResponse = response;
    }

}
