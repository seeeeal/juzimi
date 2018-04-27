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
 * Created by dabutaizha on 2018/1/25 下午2:05.
 */
public class MenuAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public MenuAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
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
