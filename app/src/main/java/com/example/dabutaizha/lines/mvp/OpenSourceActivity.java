package com.example.dabutaizha.lines.mvp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.OpenSourceInfo;
import com.example.dabutaizha.lines.mvp.adapter.OpenSourceAdapter;
import com.example.dabutaizha.lines.mvp.contract.OpenSourceContract;
import com.example.dabutaizha.lines.mvp.presenter.OpenSourcePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/5 下午2:48.
 */

public class OpenSourceActivity extends BaseActivity implements OpenSourceContract.View {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.open_source_rcy)
    public RecyclerView mRecyclerView;

    private OpenSourceContract.Presenter mPresenter;

    private OpenSourceAdapter mAdapter;

    @Override
    protected void initData() {
        mPresenter = new OpenSourcePresenter(this);
    }

    @Override
    protected void initView() {
        mPresenter.initData();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.open_source_title);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);

        mAdapter = new OpenSourceAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initViewListener() {
        mToolbar.setNavigationOnClickListener(view -> finish());

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OpenSourceInfo info = (OpenSourceInfo) adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.WEBVIEW_URL, info.getOpenSourceLink());
            WebViewActivity.startActivity(OpenSourceActivity.this, bundle);
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_open_source;
    }

    /**
     *Description: OpenSourceContract.View
     */
    @Override
    public void updateOpenSourceList(List<OpenSourceInfo> list) {
        mAdapter.addData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
    }

}
