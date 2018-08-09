package com.example.dabutaizha.lines.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.ArticleInfo;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.mvp.adapter.RelatedAdapter;
import com.example.dabutaizha.lines.mvp.adapter.SentencesAdapter;
import com.example.dabutaizha.lines.mvp.contract.ArticleActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.ArticlePresenter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/30 下午4:56.
 */

public class ArticleActivity extends BaseActivity implements ArticleActivityContract.View {

    @BindView(R.id.article_sliding_layout)
    public SlideDampingAnimationLayout mSlideAnimationLayout;
    @BindView(R.id.search_result_coord)
    public CollapsingToolbarLayout mCollLayout;
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.article_img)
    public ImageView mImgBg;
    @BindView(R.id.article_rcy)
    public RecyclerView mRecyclerView;
    @BindView(R.id.article_processbar)
    public ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.article_error)
    public RelativeLayout mArticleError;

    private ArticleActivityContract.Presenter mPresenter;

    private SentencesAdapter mAdapter;
    private RelatedAdapter mRelatedAdapter;
    private String mArticleId;
    private String mArticleTitle;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     *Description: 重写onCreate隐藏状态栏
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fullScreen(true)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mArticleId = getIntent().getExtras().getString(Constant.ARTICLE_ID);
        mArticleTitle = getIntent().getExtras().getString(Constant.ARTICLE_TITLE);

        mPresenter = new ArticlePresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView() {
        mCollLayout.setTitle(mArticleTitle);
        mCollLayout.setExpandedTitleColor(this.getResources().getColor(R.color.white));
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
        mAdapter.setHeaderAndEmpty(true);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRelatedAdapter = new RelatedAdapter(new ArrayList<>());
    }

    @Override
    protected void initViewListener() {
        mSlideAnimationLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                ArticleActivity.this.finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        mToolbar.setNavigationOnClickListener(view -> ArticleActivity.this.finish());

        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMore());

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchInfo.SentencesItem sentencesItem = (SearchInfo.SentencesItem) adapter.getItem(position);
            sentencesItem = SentenceUtil.completeSentence(sentencesItem);

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.SHARE_INFO, sentencesItem);

            ShareActivity.startActivity(ArticleActivity.this, bundle);
        });

        mRelatedAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleInfo.RelatedWork relatedWork = (ArticleInfo.RelatedWork) adapter.getData().get(position);
            String articleId = relatedWork.getRelatedWorkLink().replace(Constant.ARTICLE_URL_HEADER, "");
            String articleTitle = relatedWork.getRelatedWorkTitle();

            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_ID, articleId);
            bundle.putString(Constant.ARTICLE_TITLE, articleTitle);
            ArticleActivity.startActivity(ArticleActivity.this, bundle);
        });

        mArticleError.setOnClickListener(view -> {
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.process(mArticleId);
            mArticleError.setVisibility(View.GONE);
        });
    }

    @Override
    protected void process() {
        mPresenter.process(mArticleId);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_article;
    }

    /**
     *Description: ArticleActivityContract.View
     */
    @Override
    public void updateHead(Bundle bundle) {

        String pageUrl = bundle.getString(Constant.ARTICLE_PAGE_URL);
        String intro = bundle.getString(Constant.ARTICLE_PAGE_INTRO);
        ArrayList<ArticleInfo.RelatedWork> works = bundle.getParcelableArrayList(Constant.ARTICLE_PAGE_RELATED);

        ImageLoader.loadTransformByUrl(this, mImgBg, pageUrl);

        mAdapter.removeAllHeaderView();
        View headerView = LayoutInflater.from(this).inflate(R.layout.article_header, null);
        mAdapter.addHeaderView(headerView);

        TextView tvIntro = headerView.findViewById(R.id.article_intro_content);
        tvIntro.setText(intro);

        TextView tvRelateEmpty = headerView.findViewById(R.id.article_related_empty);
        if (works == null || works.size() == 0) {
            tvRelateEmpty.setVisibility(View.VISIBLE);
        }

        RecyclerView rcyRelated = headerView.findViewById(R.id.article_related_content);
        rcyRelated.setAdapter(mRelatedAdapter);
        rcyRelated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mRelatedAdapter.setNewData(works);
        mRelatedAdapter.notifyDataSetChanged();
    }

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
        mArticleError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(getString(R.string.load_end))) {
            mAdapter.loadMoreEnd();
        }

        ResUtil.showToast(this, msg);
    }
}
