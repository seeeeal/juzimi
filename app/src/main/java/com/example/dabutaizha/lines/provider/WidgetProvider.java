package com.example.dabutaizha.lines.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.mvp.view.CollectionActivity;
import com.example.dabutaizha.lines.mvp.view.MainActivity;

import static com.example.dabutaizha.lines.provider.WidgetModel.getWidgetTheme;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/12 下午6:04.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String CLICK_ACTION = "WIDGET_CLICKED";

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final int widgetSize = appWidgetIds.length;

        for (int i = 0; i < widgetSize; i++) {
            initSingleWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    private void initSingleWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        int widgetThemeType = getWidgetTheme();
        int layoutId = (widgetThemeType == Constant.THEME_DEFAULT ?
                R.layout.widget_sentence_default : R.layout.widget_sentence_transparent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutId);

        Intent intent = new Intent(context, WidgetProvider.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (CLICK_ACTION.equals(intent.getAction())) {
            WidgetModel.getRandomSentences(new QueryCallback() {
                @Override
                public void onSucceed(SearchInfo.SentencesItem item) {
                    int widgetThemeType = getWidgetTheme();
                    int layoutId = (widgetThemeType == Constant.THEME_DEFAULT ?
                            R.layout.widget_sentence_default :
                            R.layout.widget_sentence_transparent);

                    RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);

                    String content = SentenceItemRegexUtil.getFormatItemContent(item);
                    views.setTextViewText(R.id.widget_small_content, content);
                    views.setTextViewText(R.id.widget_small_title, item.getArticle());
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                    appWidgetManager.updateAppWidget(new ComponentName(context, WidgetProvider.class), views);
                }
            });
        }
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
