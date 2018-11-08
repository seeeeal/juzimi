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
import com.example.dabutaizha.lines.mvp.contract.MenuFragmentContract;
import com.example.dabutaizha.lines.mvp.presenter.MenuPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午2:14.
 */

public class MenuFragment extends BaseFragment implements MenuFragmentContract.View {

    @BindView(R.id.fg_menu_tab)
    public TabLayout mTabLayout;
    @BindView(R.id.fg_menu_viewpager)
    public ViewPager mViewPager;

    private MenuAdapter mMenuAdapter;

    private MenuFragmentContract.Presenter mPresenter;

    public static MenuFragment newInstance(String category) {
        Bundle args = new Bundle();

        MenuFragment fragment = new MenuFragment();
        args.putString(Constant.FRAGMENT_TITLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new MenuPresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mMenuAdapter = new MenuAdapter(getChildFragmentManager(), getContext());
        mViewPager.setAdapter(mMenuAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mTabLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mTabLayout.setTabTextColors(
                        ResUtil.getColor(R.color.colorAccentDark),
                        ResUtil.getColor(R.color.colorAccent)
                );
                mTabLayout.setSelectedTabIndicatorColor(ResUtil.getColor(R.color.yellow_dark));
                break;
            case Constant.NIGHT:
                mTabLayout.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mTabLayout.setTabTextColors(
                        ResUtil.getColor(R.color.gray),
                        ResUtil.getColor(R.color.white_light)
                );
                mTabLayout.setSelectedTabIndicatorColor(ResUtil.getColor(R.color.red_bg));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process();
    }


    /**
     *Description: View
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
