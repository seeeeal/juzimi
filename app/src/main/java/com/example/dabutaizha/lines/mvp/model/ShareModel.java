package com.example.dabutaizha.lines.mvp.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.database.SentencesObjectBox;
import com.example.dabutaizha.lines.mvp.BaseApplication;
import com.example.dabutaizha.lines.mvp.contract.ShareContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        saveImageToGallery(BaseApplication.getInstance(), bitmap);
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

    private void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Lines");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        Uri uri = Uri.fromFile(new File(Constant.SAVE_PATH + fileName));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

}
