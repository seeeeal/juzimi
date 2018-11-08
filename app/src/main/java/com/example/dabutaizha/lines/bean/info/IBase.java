package com.example.dabutaizha.lines.bean.info;

import me.ghui.fruit.converter.retrofit.IBaseWrapper;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/6 下午2:55.
 */

public interface IBase extends IBaseWrapper {

    /**
     * 某个接口返回业务上的合法性
     */
    boolean isValid();

}
