package com.example.dabutaizha.lines.mvp.model;

import android.graphics.Bitmap;

import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;
import com.example.dabutaizha.lines.mvp.contract.ShareContract;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午6:42.
 */

public class ShareModel implements ShareContract.Module {

    private ShareContract.Presenter mPresenter;

    public ShareModel(ShareContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void saveShareData(Bitmap bitmap) {
        ImageLoader.saveImageToGallery(BaseApplication.getInstance(), bitmap, ImageLoader.CARD);
        mPresenter.showMessage(ResUtil.getString(R.string.save_successd));
    }

    @Override
    public void checkIsCollect(int sentenceId) {
        SentencesObjectBox.getInstance().checkIsExistByRxJava(sentenceId).subscribe(aBoolean -> mPresenter.refreshIsCollected(aBoolean));
    }

    @Override
    public void deleteData(long id) {
        SentencesObjectBox
                .getInstance()
                .deleteByRxJava(id)
                .subscribe(aLong -> {
                    mPresenter.refreshIsCollected(false);
                    mPresenter.showMessage(ResUtil.getString(R.string.delete_sentence));
                });
    }

    @Override
    public void addData(SentencesModel model) {
        SentencesObjectBox
                .getInstance()
                .addByRxJava(model)
                .subscribe(aLong -> {
                    mPresenter.refreshIsCollected(true);
                    mPresenter.showMessage(ResUtil.getString(R.string.add_sentence));
                });
    }

}
