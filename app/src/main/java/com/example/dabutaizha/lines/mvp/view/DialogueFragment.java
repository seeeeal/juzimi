package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.DialogueInfo;
import com.example.dabutaizha.lines.mvp.adapter.DialogueAdapter;
import com.example.dabutaizha.lines.mvp.contract.DialogueFragmentContract;
import com.example.dabutaizha.lines.mvp.presenter.DialoguePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/20 下午11:12.
 */

public class DialogueFragment extends BaseFragment implements DialogueFragmentContract.View {

    @BindView(R.id.fg_dialogue_refresh)
    public SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.fg_dialogue_rcy)
    public RecyclerView mDialogueRcy;
    @BindView(R.id.fg_dialogue_error)
    public RelativeLayout mErrorLayout;

    private DialogueFragmentContract.Presenter mPresenter;
    private DialogueAdapter mDialogueAdapter;

    public static DialogueFragment newInstance(String category) {
        Bundle args = new Bundle();

        DialogueFragment fragment = new DialogueFragment();
        args.putString(Constant.FRAGMENT_TITLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_dialogue;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new DialoguePresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRefreshLayout.setColorSchemeColors(
                BaseApplication.getInstance().getResources().getColor(R.color.red_bg)
        );

        mRefreshLayout.setRefreshing(true);

        mDialogueAdapter = new DialogueAdapter(new ArrayList<>());
        mDialogueAdapter.setHeaderAndEmpty(true);
        mDialogueAdapter.setEnableLoadMore(false);

        mDialogueRcy.setAdapter(mDialogueAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mDialogueRcy.setLayoutManager(layoutManager);
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process();
    }

    @Override
    public void initViewListener() {
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.pullToRefresh(false));
        mDialogueAdapter.setOnLoadMoreListener(() -> mPresenter.pullToRefresh(true));

        mErrorLayout.setOnClickListener(view -> {
            mRefreshLayout.setRefreshing(true);
            mPresenter.pullToRefresh(false);
            mErrorLayout.setVisibility(View.GONE);
        });

        mDialogueAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            DialogueInfo.DialogueItem item = (DialogueInfo.DialogueItem) adapter.getItem(position);
            String url = item.getmDialogueItemUrl();
            ImageLoader.getBitmapByUrl(getContext(), url);
            showMessage(ResUtil.getString(R.string.save_successd));
            return true;
        });

        mDialogueAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            DialogueInfo.DialogueItem item = (DialogueInfo.DialogueItem) adapter.getItem(position);
            String photoUrl = item.getmDialogueItemUrl();
            bundle.putString(ExplorePhotoActivity.EXPLORE_PHOTO_KEY, photoUrl);
            ExplorePhotoActivity.startActivity(getContext(), bundle);
        });
    }

    @Override
    public void updateList(List<DialogueInfo.DialogueItem> list) {
        if (!mRefreshLayout.isRefreshing()) {
            mDialogueAdapter.addData(list);
        }
        if (mRefreshLayout.isRefreshing()) {
            mDialogueAdapter.setNewData(list);
            // 不显示动画需要设置在setNewData之后，否则无效
            mDialogueAdapter.setNotDoAnimationCount(5);
            mRefreshLayout.setRefreshing(false);
        }

        mDialogueAdapter.notifyDataSetChanged();
        mDialogueAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadMoreRequestError() {
        mDialogueAdapter.loadMoreFail();
    }

    @Override
    public void showRequestError() {
        mRefreshLayout.setRefreshing(false);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(getContext(), msg);
    }
}
