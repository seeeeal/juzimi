package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.mvp.adapter.MenuAdapter;
import com.example.dabutaizha.lines.mvp.contract.HotPageFragmentContract;
import com.example.dabutaizha.lines.mvp.presenter.HotpagePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/20 下午2:14.
 */

public class HotPageFragment extends BaseFragment implements HotPageFragmentContract.View {

    @BindView(R.id.fg_menu_tab)
    public TabLayout mTabLayout;
    @BindView(R.id.fg_menu_viewpager)
    public ViewPager mViewPager;

    private MenuAdapter mMenuAdapter;

    private HotPageFragmentContract.Presenter mPresenter;

    public static HotPageFragment newInstance(String category) {
        Bundle args = new Bundle();

        HotPageFragment fragment = new HotPageFragment();
        args.putString(Constant.FRAGMENT_TITLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_hotpage;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new HotpagePresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mMenuAdapter = new MenuAdapter(getChildFragmentManager(), getContext());
        mViewPager.setAdapter(mMenuAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initViewListener() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process();
    }
    /**
     *Description: HotPageFragmentContract.View
     */
    @Override
    public void setSelectPage(int item) {
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void setTab(List<Fragment> fragmentList, String[] titles) {
        mMenuAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showMessage(int resid) {
        ResUtil.showToast(getContext(), getString(resid));
    }

}
