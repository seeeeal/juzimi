package com.example.dabutaizha.lines.mvp.presenter;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.bean.AppThemeBean;
import com.example.dabutaizha.lines.mvp.contract.AppThemeContract;
import com.example.dabutaizha.lines.mvp.model.AppThemeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/10/9 上午10:17.
 */

public class AppThemePresenter implements AppThemeContract.Presenter {

    private AppThemeContract.View mView;
    private AppThemeContract.Model mModel;
    private Integer[] themesBackground;

    public AppThemePresenter(AppThemeContract.View view) {
        mView = view;
    }

    @Override
    public void initData() {
        mModel = new AppThemeModel(this);

        List<AppThemeBean> modelList = new ArrayList<>();
        String[] themes = ResUtil.getStringArray(R.array.app_theme_item);
        Integer[] themesBackground = getThemesBackground();
        int themeSize = themes.length;
        for (int i = 0; i < themeSize; i++) {
            AppThemeBean appThemeBean = new AppThemeBean();
            appThemeBean.setId(i);
            appThemeBean.setName(themes[i]);
            appThemeBean.setCardBackground(themesBackground[i]);
            modelList.add(appThemeBean);
        }
        mView.showThemeList(modelList);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void saveThemeId(int themeId) {
        mModel.saveThemeId(themeId);
    }

    private Integer[] getThemesBackground() {
        return new Integer[] {R.drawable.app_theme_default_mode_bg
                , R.drawable.app_theme_night_mode_bg};
    }
}
