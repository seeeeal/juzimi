package com.example.dabutaizha.lines.mvp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.AppThemeBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 下午1:59.
 */

public class AppThemeCardAdapter extends BaseQuickAdapter<AppThemeBean, BaseViewHolder> {

    private ThemeColorChangeListener mListener;

    public AppThemeCardAdapter(@Nullable List<AppThemeBean> data, ThemeColorChangeListener listener) {
        super(R.layout.app_theme_card_item, data);
        mListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, AppThemeBean item) {
        helper.setText(R.id.app_theme_card_title, item.getName());
        ImageView imageView = helper.getView(R.id.app_theme_card_bg);
        ImageLoader.loadRoundImageByRes(imageView.getContext(), imageView, item.getCardBackground(), 8, 8);

        TextView title = helper.getView(R.id.app_theme_card_title);
        title.setOnClickListener(view -> mListener.themeColorChange(item.getId()));
    }

    public interface ThemeColorChangeListener {
        void themeColorChange(int themeId);
    }

}
