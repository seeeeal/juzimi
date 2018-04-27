package com.example.dabutaizha.lines.mvp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/12 下午6:47.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void setData(List<Fragment> fragmentList, String[] titles) {
        mFragmentList = fragmentList;
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "null" : mTitles[position];
    }

}
