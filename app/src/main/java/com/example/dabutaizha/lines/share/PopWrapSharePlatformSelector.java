package com.example.dabutaizha.lines.share;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.example.dabutaizha.lines.R;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/6 下午5:28.
 */

public class PopWrapSharePlatformSelector extends  BaseSharePlatformSelector {
    protected PopupWindow mShareWindow;
    protected View mAnchorView;

    public PopWrapSharePlatformSelector(AppCompatActivity context, View anchorView, OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        super(context, dismissListener, itemClickListener);
        mAnchorView = anchorView;
    }

    @Override
    public void show() {
        createShareWindowIfNeed();
        if (!mShareWindow.isShowing()) {
            mShareWindow.showAtLocation(mAnchorView, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void dismiss() {
        if (mShareWindow != null) {
            mShareWindow.dismiss();
        }
    }

    @Override
    public void release() {
        dismiss();
        mShareWindow = null;
        super.release();
        mAnchorView = null;
    }

    protected void createShareWindowIfNeed() {
        if (mShareWindow != null) {
            return;
        }

        Context context = getContext();
        GridView grid = createShareGridView(context, getItemClickListener());
        mShareWindow = new PopupWindow(grid, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mShareWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mShareWindow.setOutsideTouchable(true);
        mShareWindow.setAnimationStyle(R.style.shareboard_animation);
        mShareWindow.setOnDismissListener(() -> {
            if (getDismissListener() != null) {
                getDismissListener().onDismiss();
            }
        });
    }
}
