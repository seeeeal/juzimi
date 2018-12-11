package com.example.dabutaizha.lines.mvp.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.socialize.share.core.SocializeMedia;
import com.bilibili.socialize.share.core.error.BiliShareStatusCode;
import com.bilibili.socialize.share.core.shareparam.BaseShareParam;
import com.bilibili.socialize.share.core.shareparam.ShareImage;
import com.bilibili.socialize.share.core.shareparam.ShareParamImage;
import com.bilibili.socialize.share.core.shareparam.ShareParamText;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.bean.model.SentencesModel;
import com.example.dabutaizha.lines.share.ShareHelper;
import com.example.dabutaizha.lines.mvp.adapter.ShareAdapter;
import com.example.dabutaizha.lines.mvp.contract.ShareContract;
import com.example.dabutaizha.lines.mvp.presenter.SharePresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午6:38.
 */

public class ShareActivity extends BaseActivity implements ShareContract.View, EasyPermissions.PermissionCallbacks, ShareHelper.Callback {

    @BindView(R.id.share_slide_layout)
    SlideDampingAnimationLayout mSlideAnimationLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.share_rcy)
    public RecyclerView mRecyclerView;
    @BindView(R.id.share_save)
    public TextView mSaveTv;
    @BindView(R.id.share_save_share)
    public TextView mShareTv;
    @BindView(R.id.share_bg_diy)
    public TextView mBgDIY;
    @BindView(R.id.share_title_col)
    public TextView mCharacterColorTv;

    private ShareContract.Presenter mPresenter;

    private List<SearchInfo.SentencesItem> mSentencesItemList;
    private ShareAdapter mShareAdapter;
    private SearchInfo.SentencesItem mSentencesItem;
    protected ShareHelper mShare;

    private String[] mPerms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private boolean mIsPermission = false;
    private Bitmap mShareBitmap = null;
    private int mSentenceId;
    private boolean mIsCollected = false;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override
    protected void onDestroy() {
        if (mShare != null) {
            mShare.reset();
            mShare = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            mIsPermission = EasyPermissions.hasPermissions(this, mPerms);
        }

        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            String imagePath = selectList.get(0).getCutPath();

            ImageView cardBackground = (ImageView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_image_bg);
            ImageView cardImage = (ImageView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_image);

            ImageLoader.loadTransformByPath(this, cardBackground, imagePath);
            ImageLoader.loadRoundImageByPath(this, cardImage, imagePath, 18, 0);
        }
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mPresenter = new SharePresenter(this);
        mPresenter.initData();

        mSentencesItemList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();

        mSentencesItem = bundle.getParcelable(Constant.SHARE_INFO);
        //为RcyView数据源赋值
        mSentencesItemList.add(mSentencesItem);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.share_title);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);

        mShareAdapter = new ShareAdapter(this, mSentencesItemList);
        mRecyclerView.setAdapter(mShareAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //检查是否已收藏
        mSentenceId = Integer.valueOf(mSentencesItem.getSentencesId());
        mPresenter.checkIsCollect(mSentenceId);
        mShareAdapter.bindToRecyclerView(mRecyclerView);
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

        mToolbar.setNavigationOnClickListener(view -> finish());

        mShareAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ImageView collectImg = (ImageView) view;

            ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f, 1f);
            ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f, 1f);

            AnimatorSet set = new AnimatorSet();
            set.setDuration(400);
            set.play(animatorScaleX).with(animatorScaleY);
            set.setInterpolator(new OvershootInterpolator());

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    SearchInfo.SentencesItem item = mShareAdapter.getData().get(0);
                    int sentenceId = Integer.valueOf(item.getSentencesId());
                    SentencesModel model = SentenceUtil.item2model(item);

                    //每一次点击都根据查询数据库显示结果
                    if (mIsCollected) {
                        collectImg.setImageDrawable(getDrawable(R.drawable.no_collection));
                        mPresenter.deleteData(sentenceId);
                    } else {
                        collectImg.setImageDrawable(getDrawable(R.drawable.collection));
                        mPresenter.addData(model);
                    }
                }
            });

            set.start();
        });

        mSaveTv.setOnClickListener(view -> {
            if (mIsPermission) {
                mPresenter.getScreenshot(mShareAdapter, mRecyclerView, true);
            } else {
                showMessage(ResUtil.getString(R.string.no_per));
            }
        });

        mShareTv.setOnClickListener(view -> {
            if (mIsPermission) {
                mPresenter.getScreenshot(mShareAdapter, mRecyclerView, false);
                startShare(view);
            } else {
                showMessage(ResUtil.getString(R.string.no_per));
            }
        });

        mBgDIY.setOnClickListener(view -> PictureSelector.create(ShareActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .isGif(false)
                .isCamera(false)
                .theme(R.style.picture_default_style)
                .enableCrop(true)
                .withAspectRatio(3, 2)
                .forResult(PictureConfig.CHOOSE_REQUEST));

        mCharacterColorTv.setOnClickListener(view -> {
            Resources resources = ShareActivity.this.getResources();
            TextView titleTv  = (TextView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_title);
            TextView authorTv  = (TextView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_author);
            if (titleTv.getCurrentTextColor() == ShareActivity.this.getResources().getColor(R.color.white)) {
                titleTv.setTextColor(resources.getColor(R.color.colorAccent));
                authorTv.setTextColor(resources.getColor(R.color.colorAccent));
            } else {
                titleTv.setTextColor(resources.getColor(R.color.white));
                authorTv.setTextColor(resources.getColor(R.color.white));
            }
        });
    }

    @Override
    protected void initTheme(int themeId) {
        mPresenter.notifyDataThemeChanged(mShareAdapter.getData(), themeId);
        mShareAdapter.notifyDataSetChanged();

        switch (themeId) {
            case Constant.DAY_TIME:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.black));
                mToolbar.setNavigationIcon(R.drawable.back);
                break;
            case Constant.NIGHT:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.white));
                mToolbar.setNavigationIcon(R.drawable.back_white);
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_share;
    }

    /**
     *Description: ShareContract.View
     */
    @Override
    public void getShareBitmap(Bitmap bitmap) {
        mShareBitmap = bitmap;
    }

    @Override
    public void refreshIsCollected(boolean isExist) {
        mIsCollected = isExist;
    }

    @Override
    public void hideCollectImg() {
        ImageView collectImg = (ImageView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_collect);
        collectImg.setVisibility(View.GONE);
    }

    @Override
    public void showCollectImg() {
        ImageView collectImg = (ImageView) mShareAdapter.getViewByPosition(mRecyclerView, 0, R.id.share_card_top_collect);
        collectImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
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

    /**
     *Description: ShareHelper.Callback
     */
    @Override
    public BaseShareParam getShareContent(ShareHelper helper, SocializeMedia target) {
        BaseShareParam param;

        if (target == SocializeMedia.COPY) {
            param = new ShareParamText(
                ResUtil.getString(R.string.app_name),
                mSentencesItem.getArticle() + "\n" + mSentencesItem.getWriter() + "\n" + mSentencesItem.getContent());
        } else {
            param = new ShareParamImage();
            ShareParamImage paramImage = (ShareParamImage) param;
            param.setTitle(ResUtil.getString(R.string.app_name));
            if (mShareBitmap != null) {
                paramImage.setImage(new ShareImage(mShareBitmap));
            } else {
                paramImage.setImage(new ShareImage(R.drawable.icon_x));
            }
        }

        return param;
    }

    @Override
    public void onShareStart(ShareHelper helper) {

    }

    @Override
    public void onShareComplete(ShareHelper helper, int code) {
        if (code == BiliShareStatusCode.ST_CODE_SUCCESSED) {
            showMessage(ResUtil.getString(R.string.share_succeed));
        }
        if (code == BiliShareStatusCode.ST_CODE_ERROR) {
            showMessage(ResUtil.getString(R.string.share_failed));
        }
    }

    @Override
    public void onDismiss(ShareHelper helper) {

    }

    public void startShare(@Nullable View anchor) {
        if (mShare == null) {
            mShare = ShareHelper.instance(this, this);
        }

        if (anchor != null) {
            mShare.showShareWarpWindow(anchor);
        }
    }

}
