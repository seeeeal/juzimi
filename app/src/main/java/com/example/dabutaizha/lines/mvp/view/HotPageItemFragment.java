package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.mvp.contract.HotPageItemFragmentContract;
import com.example.dabutaizha.lines.mvp.adapter.HotPageSentencesAdapter;
import com.example.dabutaizha.lines.mvp.presenter.HotpageItemPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.security.AccessController.getContext;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/11 下午8:25.
 */

public class HotPageItemFragment extends BaseFragment implements HotPageItemFragmentContract.View {

    @BindView(R.id.hotpage_background_layout)
    public RelativeLayout mBackgroundLayout;
    @BindView(R.id.hotpage_refresh)
    public SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.hotpage_lines)
    public RecyclerView mRecyclerView;
    @BindView(R.id.hotpage_connent_error)
    public RelativeLayout mErrorLayout;

    private HotPageSentencesAdapter mHotPageSentencesAdapter;

    private HotPageItemFragmentContract.Presenter mPresenter;

    private TextView mHeaderTag;
    private ImageView mHeaderImg;

    public static HotPageItemFragment newInstance(String category) {
        Bundle args = new Bundle();

        HotPageItemFragment fragment = new HotPageItemFragment();
        args.putString(Constant.FRAGMENT_TITLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_hotpage_item;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new HotpageItemPresenter(this);
        mPresenter.initData(getArguments(), savedInstanceState);
    }

    @Override
    protected void initView(android.view.View view, Bundle savedInstanceState) {
        mRefreshLayout.setColorSchemeColors(
                BaseApplication.getInstance().getResources().getColor(R.color.red_bg)
        );

        mHotPageSentencesAdapter = new HotPageSentencesAdapter(new ArrayList<>());
        mHotPageSentencesAdapter.setHeaderAndEmpty(true);
        mHotPageSentencesAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mHotPageSentencesAdapter.setEnableLoadMore(false);

        mRecyclerView.setAdapter(mHotPageSentencesAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mHotPageSentencesAdapter.removeAllHeaderView();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout. hotpage_header, null);
        mHotPageSentencesAdapter.addHeaderView(headerView);

        mHeaderTag = headerView.findViewById(R.id.header_tag);
        mHeaderImg = headerView.findViewById(R.id.header_img);

        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void initViewListener() {
        // 刷新监听
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.pullToRefresh(false));
        // 加载更多监听
        mHotPageSentencesAdapter.setOnLoadMoreListener(() -> mPresenter.pullToRefresh(true));

        mHotPageSentencesAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchInfo.SentencesItem sentencesItem = (SearchInfo.SentencesItem) adapter.getItem(position);
            sentencesItem = SentenceUtil.completeSentence(sentencesItem);

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.SHARE_INFO, sentencesItem);

            ShareActivity.startActivity(getActivity(), bundle);
        });

        mHeaderImg.setOnClickListener(view -> {
            String content = mHeaderTag.getText().toString().trim();
            if (content.length() > 0) {
                SearchInfo.SentencesItem item = SentenceUtil.buildOnlyContentSentence(content);
                item = SentenceUtil.completeSentence(item);

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.SHARE_INFO, item);

                ShareActivity.startActivity(getActivity(), bundle);
            }
        });

        mErrorLayout.setOnClickListener(view -> {
            mRefreshLayout.setRefreshing(true);
            mPresenter.pullToRefresh(false);
            mErrorLayout.setVisibility(View.GONE);
        });
    }

    @Override
    protected void initTheme(int themeId) {
        mPresenter.notifyDataThemeChanged(mHotPageSentencesAdapter.getData(), themeId);
        mHotPageSentencesAdapter.notifyDataSetChanged();

        switch (themeId) {
            case Constant.DAY_TIME:
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                break;
            case Constant.NIGHT:
                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.hotpage_content_bg));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process(savedInstanceState);
    }

    /**
     *Description: HotPageItemFragmentContract.View
     */
    @Override
    public void updateHeaderView(String url) {
        ImageLoader.loadGradientByUrl(getActivity(), mHeaderImg, url);
    }

    @Override
    public void refreshHeaderTag(String tag) {
        mHeaderTag.setText(tag);
    }

    @Override
    public void updateData(List<SearchInfo.SentencesItem> sentences) {

        if (!mRefreshLayout.isRefreshing()) {
            mHotPageSentencesAdapter.addData(sentences);
        }
        if (mRefreshLayout.isRefreshing()) {
            mHotPageSentencesAdapter.setNewData(sentences);

            // 不显示动画需要设置在setNewData之后，否则无效
            mHotPageSentencesAdapter.setNotDoAnimationCount(5);
            mRefreshLayout.setRefreshing(false);
        }

        // 由于添加了headerview,所以从下标1开始刷新
        mHotPageSentencesAdapter.notifyItemChanged(1, sentences.size());
        mHotPageSentencesAdapter.loadMoreComplete();
    }

    @Override
    public void showMessage(CharSequence msg) {

        if (!isAdded()) {
            return;
        }

        ResUtil.showToast(getActivity(), msg);
        if (msg.equals(getString(R.string.load_end))) {
            mHotPageSentencesAdapter.loadMoreEnd();
        }

    }

    @Override
    public void showLoadMoreRequestError() {
        mHotPageSentencesAdapter.loadMoreFail();
    }

    @Override
    public void showRequestError() {
        mRefreshLayout.setRefreshing(false);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

}
