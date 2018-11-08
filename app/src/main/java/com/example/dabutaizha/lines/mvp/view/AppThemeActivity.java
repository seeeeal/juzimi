package com.example.dabutaizha.lines.mvp.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.AppThemeBean;
import com.example.dabutaizha.lines.mvp.adapter.AppThemeCardAdapter;
import com.example.dabutaizha.lines.mvp.contract.AppThemeContract;
import com.example.dabutaizha.lines.mvp.presenter.AppThemePresenter;

import java.util.List;

import butterknife.BindView;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 上午10:10.
 */

public class AppThemeActivity extends BaseActivity implements AppThemeContract.View, AppThemeCardAdapter.ThemeColorChangeListener {

    @BindView(R.id.app_theme_sliding_layout)
    public SlideDampingAnimationLayout mSlideDampingAnimationLayout;
    @BindView(R.id.app_theme_bg)
    public RelativeLayout mBackgroundLayout;
    @BindView(R.id.toolbar)
    public android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.app_theme_rcy)
    RecyclerView mAppThemeRcy;

    private AppThemeContract.Presenter mPresenter;
    private AppThemeCardAdapter mAdapter;

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mPresenter = new AppThemePresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_theme_title);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);
    }

    @Override
    protected void initViewListener() {
        mSlideDampingAnimationLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void initTheme(int themeId) {
        notifyTheme(themeId);
    }

    @Override
    protected void process() {

    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_app_theme;
    }

    /**
     *Description: AppThemeContract.View
     */
    @Override
    public void showThemeList(List<AppThemeBean> themeList) {
        mAdapter = new AppThemeCardAdapter(themeList, this);

        mAppThemeRcy.setLayoutManager(new LinearLayoutManager(this));
        mAppThemeRcy.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
    }

    /**
     *Description: AppThemeCardAdapter.ThemeColorChangeListener
     */
    @Override
    public void themeColorChange(int themeId) {
        //保存当前所选至SP
        mPresenter.saveThemeId(themeId);
        //根据所选改变当前页面主题
        notifyTheme(themeId);
    }

    private void notifyTheme(int themeId) {
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
}
