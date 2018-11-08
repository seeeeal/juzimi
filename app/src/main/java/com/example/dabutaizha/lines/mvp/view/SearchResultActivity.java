package com.example.dabutaizha.lines.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.mvp.adapter.SentencesAdapter;
import com.example.dabutaizha.lines.mvp.contract.SearchResultActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/29 下午7:08.
 */

public class SearchResultActivity extends BaseActivity implements SearchResultActivityContract.View {

    @BindView(R.id.search_result_slide_layout)
    public SlideDampingAnimationLayout mSlideAnimationLayout;
    @BindView(R.id.search_result_background_layout)
    public RelativeLayout mBackgroundLayout;
    @BindView(R.id.search_result_rcy)
    public RecyclerView mSearchResultRcy;
    @BindView(R.id.search_result_coord)
    public CollapsingToolbarLayout mCollLayout;
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.search_processbar)
    public ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.search_result_error)
    public RelativeLayout mSearchErrorLayout;

    private SearchResultActivityContract.Presenter mPresenter;

    private SentencesAdapter mAdapter;
    private String mSearchTag;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mSearchTag = getIntent().getExtras().getString(Constant.SEARCH);

        mPresenter = new SearchResultPresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView() {
        mCollLayout.setTitle(mSearchTag);
        mCollLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        mCollLayout.setExpandedTitleTypeface(Typeface.create("bold", Typeface.BOLD));
        mCollLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back_white);

        mProgressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.MULTIPLY);
        mProgressBar.show();

        mAdapter = new SentencesAdapter(new ArrayList<>());
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setNotDoAnimationCount(5);
        mSearchResultRcy.setAdapter(mAdapter);
        mSearchResultRcy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initViewListener() {
        mSlideAnimationLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        mToolbar.setNavigationOnClickListener(view -> SearchResultActivity.this.finish());

        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMore());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchInfo.SentencesItem sentencesItem = (SearchInfo.SentencesItem) adapter.getItem(position);

            sentencesItem = SentenceUtil.completeSentence(sentencesItem);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.SHARE_INFO, sentencesItem);

            ShareActivity.startActivity(SearchResultActivity.this, bundle);
        });

        mSearchErrorLayout.setOnClickListener(view -> {
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.process(mSearchTag);
            mSearchErrorLayout.setVisibility(View.GONE);
        });
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mCollLayout.setBackgroundColor(ResUtil.getColor(R.color.red_bg));
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                break;
            case Constant.NIGHT:
                mCollLayout.setBackgroundColor(ResUtil.getColor(R.color.red_text_bg));
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.background_night));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {
        mPresenter.process(mSearchTag);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_search_result;
    }


    /**
     *Description: SearchResultActivityContract.View
     */
    @Override
    public void updateList(List<SearchInfo.SentencesItem> itemList) {
        mProgressBar.hide();

        mAdapter.addData(itemList);
        mAdapter.notifyDataSetChanged();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadMoreRequestError() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void showRequestError() {
        mProgressBar.setVisibility(View.GONE);
        mSearchErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {

        if (msg.equals(getString(R.string.load_end))) {
            mAdapter.loadMoreEnd();
        }

        ResUtil.showToast(this, msg);
    }
}
