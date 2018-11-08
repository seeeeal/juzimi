package com.example.colorfultablayout;

import android.content.Context;
import android.support.v4.util.Pools;
import android.util.Log;
import android.widget.TableLayout;

import java.lang.reflect.Field;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/25 下午4:55.
 */

public class ColorfulTabLayout extends TableLayout {

    public ColorfulTabLayout(Context context) {
        super(context);
    }

    public void setTabBackground(int resColor) {
        try {
            Pools.Pool<TabView>
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static Object getValue(Object object, String fieldName) throws Exception {
        if (object == null) {
            return null;
        }
        if (fieldName.trim().length() == 0) {
            return null;
        }
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return null;
    }

}
