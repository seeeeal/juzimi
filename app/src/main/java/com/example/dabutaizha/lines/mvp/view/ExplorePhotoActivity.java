package com.example.dabutaizha.lines.mvp.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.mvp.contract.ExplorePhotoActivityContract;
import com.example.dabutaizha.lines.mvp.presenter.ExplorePhotoActivityPresenter;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;


/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/8/8 下午3:03.
 */

public class ExplorePhotoActivity extends BaseActivity implements ExplorePhotoActivityContract.View, EasyPermissions.PermissionCallbacks {

    public static final String EXPLORE_PHOTO_KEY = "explore_photo_key";

    @BindView(R.id.photo_sliding_layout)
    public SlideDampingAnimationLayout mSlideDampingAnimationLayout;
    @BindView(R.id.explore_photo_item)
    public PhotoView mPhotoView;
    @BindView(R.id.explore_question_icon)
    public TextView mQuestionIcon;
    @BindView(R.id.explore_tip_content)
    public TextView mTipContent;


    private String[] mPerms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private boolean mIsPermission = false;

    private ExplorePhotoActivityContract.Presenter mPresenter;
    private String mPhotoUrl;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ExplorePhotoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        mPresenter = new ExplorePhotoActivityPresenter(this);
        mPhotoUrl = getPhotoUrl();
    }

    @Override
    protected void initView() {

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
                if (mIsPermission) {
                    ImageLoader.getBitmapByUrl(ExplorePhotoActivity.this , mPhotoUrl);
                    showMessage(ResUtil.getString(R.string.save_successd));
                } else {
                    showMessage(ResUtil.getString(R.string.no_per));
                }
            }
        });

        mQuestionIcon.setOnClickListener(view -> startAlternateAnimation());

        mTipContent.setOnClickListener(view -> startAlternateAnimation());
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mSlideDampingAnimationLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                break;
            case Constant.NIGHT:
                mSlideDampingAnimationLayout.setBackgroundColor(ResUtil.getColor(R.color.background_night));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {
        if (!EasyPermissions.hasPermissions(this, mPerms)) {
            EasyPermissions.requestPermissions(this,
                    ResUtil.getString(R.string.per_request),
                    R.string.accept,
                    R.string.cancel,
                    0,
                    mPerms);
        } else {
            mIsPermission = true;
        }

        ImageLoader.loadImageByUrlFitLayout(this, mPhotoView, mPhotoUrl);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_explore_photo;
    }

    /**
     *Description: ExplorePhotoActivityContract.View
     */
    @Override
    public void showMessage(String message) {
        ResUtil.showToast(this, message);
    }

    /**
     *Description: EasyPermissions.PermissionCallbacks
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        showMessage(ResUtil.getString(R.string.pre_success));
        mIsPermission = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(R.string.pre_tip)
                    .setPositiveButton(R.string.pre_positive)
                    .setNegativeButton(R.string.pre_negative)
                    .setRationale(R.string.rationale_tip)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            mIsPermission = EasyPermissions.hasPermissions(this, mPerms);
        }
    }

    public String getPhotoUrl() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPhotoUrl = args.getString(EXPLORE_PHOTO_KEY, "");
        }
        return mPhotoUrl;
    }

    private void startAlternateAnimation() {
        Log.d("dabutaizha", "into startAlternateAnimation");
        Object obj = mQuestionIcon.getTag();
        if (obj == null || "animation_start".equals(obj)) {
            float curTranslationX = mQuestionIcon.getTranslationX();
            ObjectAnimator iconAnimatorStart = ObjectAnimator.ofFloat(mQuestionIcon, "translationX", curTranslationX, -800f);
            iconAnimatorStart.start();
            mQuestionIcon.setTag("animation_end");

            mTipContent.setVisibility(View.VISIBLE);
            ObjectAnimator contentAnimatorStart = ObjectAnimator.ofFloat(mTipContent, "alpha", 0f, 1f);
            contentAnimatorStart.start();
        }
        if ("animation_end".equals(obj)) {
            float curTranslationX = mQuestionIcon.getTranslationX();
            ObjectAnimator iconAnimatorStart = ObjectAnimator.ofFloat(mQuestionIcon, "translationX", curTranslationX, 0f);
            iconAnimatorStart.start();
            mQuestionIcon.setTag("animation_start");

            ObjectAnimator contentAnimatorStart = ObjectAnimator.ofFloat(mTipContent, "alpha", 1f, 0f);
            contentAnimatorStart.start();
        }
    }

}
