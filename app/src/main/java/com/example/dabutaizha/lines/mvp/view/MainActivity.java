package com.example.dabutaizha.lines.mvp.view;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dabutaizha.lines.ActivityManager;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.TimeUtil;
import com.example.dabutaizha.lines.mvp.adapter.AppThemeCardAdapter;
import com.example.dabutaizha.lines.mvp.contract.MainActivityContract;
import com.example.dabutaizha.lines.mvp.adapter.TabAdapter;
import com.example.dabutaizha.lines.mvp.presenter.MainPresenter;
import com.example.dabutaizha.lines.mvp.view.dialog.AboutDialog;
import com.example.dabutaizha.lines.mvp.view.dialog.BaseDialog;
import com.example.dabutaizha.lines.mvp.view.dialog.BaseDialogInterface;
import com.example.dabutaizha.lines.mvp.view.dialog.RewardDialog;
import com.example.dabutaizha.lines.mvp.view.dialog.UpdateDialog;
import com.example.dabutaizha.lines.mvp.view.dialog.WidgetThemeDialog;
import com.example.dabutaizha.lines.provider.WidgetModel;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityContract.View
        , NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_nav)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_tab)
    TabLayout mTabLayout;

    private TextView mAddSentences;
    private TextView mAccountInfo;
    private TabAdapter mTabAdapter;
    private ImageView mNavHeaderBackground;

    private MainActivityContract.Presenter mPresenter;

    /**
     *Description: 双击退出
     */
    @Override
    public void onBackPressed() {
        if (TimeUtil.checkDoubleClick()) {
            super.onBackPressed();
            ActivityManager.finishAllActivities();
        } else {
            showMessage(ResUtil.getString(R.string.clike_finish));
        }
    }

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.initData(getIntent());
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_icon);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTabAdapter = new TabAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        View navHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header_main);
        mAccountInfo = navHeaderView.findViewById(R.id.app_collect_info);
        mAddSentences = navHeaderView.findViewById(R.id.app_add_personal_sentences);
        mNavHeaderBackground = navHeaderView.findViewById(R.id.app_nav_header_bg);

        mAddSentences.post(() -> {
            if (Constant.INPUT_SENTENCE_PREMISSION) {
                mAddSentences.setText(getString(R.string.add_personal_sentences));
            } else {
                //do anything
            }
        });
    }

    @Override
    protected void initViewListener() {
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(Gravity.START));
        mNavigationView.setNavigationItemSelectedListener(this);

        mAddSentences.setOnClickListener(view -> {
            if (Constant.INPUT_SENTENCE_PREMISSION) {
                Intent intent = new Intent(MainActivity.this, AddSentenceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.black));
                mToolbar.setNavigationIcon(R.drawable.nav_icon);
                mTabLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mTabLayout.setTabTextColors(
                        ResUtil.getColor(R.color.colorAccentDark),
                        ResUtil.getColor(R.color.colorAccent)
                );
                mTabLayout.setSelectedTabIndicatorColor(ResUtil.getColor(R.color.yellow_dark));

                mNavigationView.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mNavigationView.setItemTextColor(ResUtil.getColorStateList(R.color.nav_item_text_color_list));

                mNavHeaderBackground.setImageDrawable(ResUtil.getDrawable(R.drawable.share_card_default_bg));
                break;
            case Constant.NIGHT:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.white));
                mToolbar.setNavigationIcon(R.drawable.nav_icon_white);
                mTabLayout.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mTabLayout.setTabTextColors(
                        ResUtil.getColor(R.color.gray),
                        ResUtil.getColor(R.color.white_light)
                );
                mTabLayout.setSelectedTabIndicatorColor(ResUtil.getColor(R.color.red_bg));

                mNavigationView.setBackgroundColor(ResUtil.getColor(R.color.background_night));
                mNavigationView.setItemTextColor(ResUtil.getColorStateList(R.color.nav_item_text_color_list_night));

                mNavHeaderBackground.setImageDrawable(ResUtil.getDrawable(R.drawable.nav_header_bg_night));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    /**
     *Description: MainActivityContract.View
     */
    @Override
    public void setSelectPage(int item) {
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void setTab(List<Fragment> fragmentList, String[] titles) {
        mTabAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showUpdateDialog(Bundle bundle) {
        UpdateDialog updateDialog = new UpdateDialog(this, R.style.aboutDialog, bundle);
        updateDialog.show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void refreshView(int collectSize) {
        mAccountInfo.setText(String.format("本地收藏：%d", collectSize));
    }

    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_collection :
                Intent collectionIntent = new Intent(this, CollectionActivity.class);
                startActivity(collectionIntent);
                break;
            case R.id.nav_menu_widget_theme:
                new WidgetThemeDialog(this, R.style.aboutDialog).show();
                break;
            case R.id.nav_menu_app_theme:
                Intent appThemeIntent = new Intent(this, AppThemeActivity.class);
                startActivity(appThemeIntent);
                break;
            case R.id.nav_menu_clear_cache:
                mPresenter.clearCache();
                break;
            case R.id.nav_menu_feedback:
                try {
                    Intent feedBackIntent = mPresenter.buildFeedBackData(MainActivity.this);
                    startActivity(feedBackIntent);
                } catch (ActivityNotFoundException e) {
                    showMessage(ResUtil.getString(R.string.feedback_error));
                }
                break;
            case R.id.nav_menu_update:
                // 暂停酷安升级逻辑
                // mPresenter.getRecentVersionInfo();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEBVIEW_URL, ResUtil.getString(R.string.github_project_url));
                WebViewActivity.startActivity(MainActivity.this, bundle);
                break;
            case R.id.nav_menu_coffee:
                new RewardDialog(this, R.style.rewardDialog).show();
                break;
            case R.id.nav_menu_open_source:
                 Intent openSourceIntent = new Intent(MainActivity.this, OpenSourceActivity.class);
                 startActivity(openSourceIntent);
                break;
            case R.id.nav_menu_about:
                AboutDialog aboutDialog = new AboutDialog(this, R.style.aboutDialog, new CyclicBarrier(1, new Runnable() {
                    @Override
                    public void run() {
                        mAddSentences.setText(getString(R.string.add_personal_sentences));
                    }
                }));
                aboutDialog.show();
                break;
            default :
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.querySentencesSize();
    }
}
