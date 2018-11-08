package com.example.dabutaizha.lines.mvp.view;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupAdapter;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupBaseAdapter;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupNightAdapter;
import com.example.dabutaizha.lines.mvp.contract.CollectionActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.CollectionPresenter;
import com.example.dabutaizha.lines.provider.WidgetModel;
import com.example.dabutaizha.lines.SentenceItemRegexUtil;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/4/12 下午9:25.
 */

public class WidgetActivity extends BaseActivity implements CollectionActivityContract.View {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.collection_rcy)
    public RecyclerView mRecyclerView;
    @BindView(R.id.collection_empty_view)
    public RelativeLayout mEmptyView;
    @BindView(R.id.collection_background_layout)
    public RelativeLayout mBackgroundLayout;

    private CollectionActivityContract.Presenter mPresenter;

    private CollectionGroupBaseAdapter mGroupAdapter;
    private List<GroupSentencesInfo> mGroupSentencesList;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.process();
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mPresenter = new CollectionPresenter(this);
        mPresenter.initData();

        mGroupSentencesList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.collection_title);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);

        if (AppThemeUtils.getCurrentAppTheme() == Constant.DAY_TIME) {
            mGroupAdapter = new CollectionGroupAdapter(mGroupSentencesList);
        } else {
            mGroupAdapter = new CollectionGroupNightAdapter(mGroupSentencesList);
        }

        mRecyclerView.setAdapter(mGroupAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initViewListener() {
        mToolbar.setNavigationOnClickListener(view -> finish());

        mGroupAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GroupSentencesInfo info = (GroupSentencesInfo) adapter.getItem(position);
            SearchInfo.SentencesItem item = SentenceUtil.completeSentence(info.t);
            String content = SentenceItemRegexUtil.getFormatItemContent(item);

            RemoteViews remoteViews = getRemoteViews();
            remoteViews.setTextViewText(R.id.widget_small_content, content);
            remoteViews.setTextViewText(R.id.widget_small_title, item.getArticle());

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            appWidgetManager.partiallyUpdateAppWidget(mAppWidgetId, remoteViews);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.black));
                mToolbar.setNavigationIcon(R.drawable.back);
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                break;
            case Constant.NIGHT:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.white));
                mToolbar.setNavigationIcon(R.drawable.back_white);
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.background_night));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {

    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_collection;
    }

    /**
     *Description: CollectionActivityContract.View
     */
    @Override
    public void refreshCollectList(List<GroupSentencesInfo> groups) {
        mGroupAdapter.setNewData(groups);
        if (groups.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
    }

    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = null;
        int widgetTheme = WidgetModel.getWidgetTheme();
        if (widgetTheme == Constant.THEME_DEFAULT) {
            remoteViews = new RemoteViews(BaseApplication.getInstance().getPackageName(), R.layout.widget_sentence_default);
        } else {
            remoteViews = new RemoteViews(BaseApplication.getInstance().getPackageName(), R.layout.widget_sentence_transparent);
        }
        return remoteViews;
    }
}
