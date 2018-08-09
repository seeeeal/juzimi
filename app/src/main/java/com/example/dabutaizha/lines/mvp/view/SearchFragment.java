package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.mvp.contract.SearchFragmentContract;
import com.example.dabutaizha.lines.mvp.presenter.SearchPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/26 下午1:41.
 */

public class SearchFragment extends BaseFragment implements SearchFragmentContract.View {

    @BindView(R.id.fg_search_edt)
    public MaterialEditText mEditText;
    @BindView(R.id.search_his_tags)
    public TagGroup mHistoryTagGroup;
    @BindView(R.id.fg_search_clean)
    public TextView mTextViewClean;
    @BindView(R.id.search_recommend_tags)
    public TagGroup mRecommendTagGroup;

    private SearchFragmentContract.Presenter mPresenter;

    /**
     *Description: BaseFragment
     */
    @Override
    protected int getPageLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new SearchPresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initViewListener() {
        mEditText.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                mEditText.setHint(R.string.null_string);
            }
            if (!focus) {
                mEditText.setHint(R.string.search_hint);
            }
        });

        mEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String searchTag = textView.getText().toString();
                searchTag = searchTag.trim();

                if (searchTag.length() > 0) {
                    mPresenter.addSearchTag(searchTag);
                    mEditText.setText("");

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.SEARCH, searchTag);
                    SearchResultActivity.startActivity(getActivity(), bundle);
                }
                if (searchTag.length() <= 0) {
                    showMessage(R.string.input_error);
                }
            }
            return false;
        });

        mRecommendTagGroup.setOnTagClickListener(tag -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SEARCH, tag);
            SearchResultActivity.startActivity(getActivity(), bundle);
        });

        mHistoryTagGroup.setOnTagClickListener(tag -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SEARCH, tag);
            SearchResultActivity.startActivity(getActivity(), bundle);
        });

        mTextViewClean.setOnClickListener(view -> mPresenter.clearSearchTags());
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPresenter.process();
    }

    /**
     *Description: SearchFragmentContract.View
     */
    @Override
    public void refreshRecommendTag(String[] recommendTags) {
        mRecommendTagGroup.setTags(recommendTags);
    }

    @Override
    public void refreshSearchTag(String[] searchTags) {
        mHistoryTagGroup.setTags(searchTags);

        if (searchTags.length > 0) {
            mHistoryTagGroup.removeViewAt(searchTags.length - 1);
        }
    }

    @Override
    public void addSearchTag(String searchTag) {
        mPresenter.addSearchTag(searchTag);
    }

    @Override
    public void clearSearchTags() {

    }

    @Override
    public void showMessage(int resid) {
        ResUtil.showToast(getContext(), getString(resid));
    }
}
