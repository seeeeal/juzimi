package com.example.dabutaizha.lines.mvp.presenter;

import android.support.v4.app.Fragment;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.mvp.MenuItemFragment;
import com.example.dabutaizha.lines.mvp.contract.MenuFragmentContract;

import java.util.ArrayList;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午2:23.
 */

public class MenuPresenter implements MenuFragmentContract.Presenter {

    private MenuFragmentContract.View mView;
    private String[] mTitles;
    private ArrayList<Fragment> mFragmentList;

    public MenuPresenter(MenuFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void initData() {
        mTitles = ResUtil.getStringArray(R.array.menu_item);
        initFragmentList();
    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i ++) {
            mFragmentList.add(MenuItemFragment.newInstance(mTitles[i]));
        }
    }

    @Override
    public void process() {
        mView.setTab(mFragmentList, mTitles);
        mView.setSelectPage(0);
    }

}
