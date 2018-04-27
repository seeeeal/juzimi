package com.example.dabutaizha.lines.mvp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.BlockInfoItem;
import com.example.dabutaizha.lines.mvp.adapter.CategoryAdapter;
import com.example.dabutaizha.lines.mvp.contract.MenuItemFragmentContract;
import com.example.dabutaizha.lines.mvp.presenter.MenuItemPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/25 下午2:58.
 */
public class MenuItemFragment extends BaseFragment implements MenuItemFragmentContract.View {

    @BindView(R.id.fg_menu_refresh)
    public SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.fg_menu_rcy)
    public RecyclerView mRecyclerView;
    @BindView(R.id.fg_menu_error)
    public RelativeLayout mErrorLayout;

    private CategoryAdapter mCategoryAdapter;

    private MenuItemFragmentContract.Presenter mPresenter;

    public static MenuItemFragment newInstance(String category) {
        Bundle args = new Bundle();

        MenuItemFragment fragment = new MenuItemFragment();
        args.putString(Constant.FRAGMENT_TITLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_menu_item;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new MenuItemPresenter(this);
        mPresenter.initData(getArguments(), savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRefreshLayout.setColorSchemeColors(
                BaseApplication.getInstance().getResources().getColor(R.color.red_bg)
        );

        mRefreshLayout.setRefreshing(true);

        mCategoryAdapter = new CategoryAdapter(new ArrayList<>());
        mCategoryAdapter.setHeaderAndEmpty(true);
        mCategoryAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mCategoryAdapter.setEnableLoadMore(false);

        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process(savedInstanceState);
    }

    /**
     *Description: MenuItemFragmentContract.View
     */
    @Override
    public void updateList(List<BlockInfoItem> list) {
        if (!mRefreshLayout.isRefreshing()) {
            mCategoryAdapter.addData(list);
        }
        if (mRefreshLayout.isRefreshing()) {
            mCategoryAdapter.setNewData(list);
            // 不显示动画需要设置在setNewData之后，否则无效
            mCategoryAdapter.setNotDoAnimationCount(5);
            mRefreshLayout.setRefreshing(false);
        }

        mCategoryAdapter.notifyDataSetChanged();
        mCategoryAdapter.loadMoreComplete();
    }

    @Override
    public void initViewListener() {
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            BlockInfoItem infoItem = (BlockInfoItem) adapter.getData().get(position);

            String link = infoItem.getmBlockItemContent().getHyperLink();
            String articleId = link.replace(Constant.ARTICLE_URL_HEADER, "");

            String title = infoItem.getmBlockItemContent().getTitle();

            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_ID, articleId);
            bundle.putString(Constant.ARTICLE_TITLE, title);
            ArticleActivity.startActivity(getActivity(), bundle);
        });

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.pullToRefresh(false));
        mCategoryAdapter.setOnLoadMoreListener(() -> mPresenter.pullToRefresh(true));

        mErrorLayout.setOnClickListener(view -> {
            mRefreshLayout.setRefreshing(true);
            mPresenter.pullToRefresh(false);
            mErrorLayout.setVisibility(View.GONE);
        });
    }

    @Override
    public void showLoadMoreRequestError() {
        mCategoryAdapter.loadMoreFail();
    }

    @Override
    public void showRequestError() {
        mRefreshLayout.setRefreshing(false);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(CharSequence msg) {
        ResUtil.showToast(getContext(), msg);

        if (msg.equals(getString(R.string.load_end))) {
            mCategoryAdapter.loadMoreEnd();
        }
    }
}
