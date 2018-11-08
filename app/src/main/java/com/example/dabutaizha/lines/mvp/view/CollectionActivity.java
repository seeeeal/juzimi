package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupBaseAdapter;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupAdapter;
import com.example.dabutaizha.lines.mvp.adapter.CollectionGroupNightAdapter;
import com.example.dabutaizha.lines.mvp.contract.CollectionActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.CollectionPresenter;
import com.example.dabutaizha.lines.wxapi.AppThemeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description : 收藏界面
 * Created by dabutaizha on 2018/4/8 下午12:12.
 */

public class CollectionActivity extends BaseActivity implements CollectionActivityContract.View {

    @BindView(R.id.collection_sliding_layout)
    public SlideDampingAnimationLayout mSlideAnimationLayout;
    @BindView(R.id.collection_background_layout)
    public RelativeLayout mBackgroundLayout;
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.collection_rcy)
    public RecyclerView mRecyclerView;
    @BindView(R.id.collection_empty_view)
    public RelativeLayout mEmptyView;

    private CollectionActivityContract.Presenter mPresenter;

    private CollectionGroupBaseAdapter mGroupAdapter;
    private List<GroupSentencesInfo> mGroupSentencesList;

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
        mSlideAnimationLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                CollectionActivity.this.finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        mToolbar.setNavigationOnClickListener(view -> finish());

        mGroupAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GroupSentencesInfo info = (GroupSentencesInfo) adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.SHARE_INFO, info.t);

            ShareActivity.startActivity(this, bundle);
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
}
