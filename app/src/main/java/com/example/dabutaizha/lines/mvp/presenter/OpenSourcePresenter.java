package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.OpenSourceInfo;
import com.example.dabutaizha.lines.mvp.contract.OpenSourceContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/5 下午2:55.
 */

public class OpenSourcePresenter implements OpenSourceContract.Presenter {

    private OpenSourceContract.View mView;

    private List<OpenSourceInfo> mSourceInfoList;

    public OpenSourcePresenter(OpenSourceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initData() {
        mSourceInfoList = new ArrayList<>();

        String[] titleArray = ResUtil.getStringArray(R.array.os_title);
        String[] authorArray = ResUtil.getStringArray(R.array.os_author);
        String[] introArray = ResUtil.getStringArray(R.array.os_intro);
        String[] linkArray = ResUtil.getStringArray(R.array.os_link);

        for (int i = 0; i < titleArray.length; i ++) {
            OpenSourceInfo info = new OpenSourceInfo();
            info.setOpenSourceTitle(titleArray[i]);
            info.setOpenSourceAuthor(authorArray[i]);
            info.setOpenSourceIntro(introArray[i]);
            info.setOpenSourceLink(linkArray[i]);
            mSourceInfoList.add(info);
        }
    }

    @Override
    public void process() {
        mView.updateOpenSourceList(mSourceInfoList);
    }

    @Override
    public void showMessage(String msg) {
        mView.showMessage(msg);
    }
}
