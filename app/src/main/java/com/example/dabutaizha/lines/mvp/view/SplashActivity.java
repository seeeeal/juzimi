package com.example.dabutaizha.lines.mvp.view;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.mvp.contract.SplashActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.SplashPresenter;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/7 上午10:58.
 */
public class SplashActivity extends BaseActivity implements SplashActivityContract.View {

    @BindView(R.id.banner_background)
    public BGABanner mBannerBackground;
    @BindView(R.id.banner_foreground)
    public BGABanner mBannerForeground;
    @BindView(R.id.btn_enter)
    public TextView mBtnEnter;

    private SplashActivityContract.Presenter mPresenter;

    @Override
    protected void initData() {
        mPresenter = new SplashPresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initViewListener() {
        mBannerForeground.setEnterSkipViewIdAndDelegate(R.id.btn_enter, 0, () -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_splash;
    }

    /**
     *Description: SplashActivityContract.View
     */
    @Override
    public void refreshGuidePage() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
        // 设置数据源
        mBannerBackground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.splash_bg_0,
                R.drawable.splash_bg_1,
                R.drawable.splash_bg_2,
                R.drawable.splash_bg_3);

        mBannerForeground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.splash0,
                R.drawable.splash1,
                R.drawable.splash2,
                R.drawable.splash3);
    }

    @Override
    public void enterMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

}
