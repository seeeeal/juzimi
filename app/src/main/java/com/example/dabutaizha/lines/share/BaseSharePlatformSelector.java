package com.example.dabutaizha.lines.share;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bilibili.socialize.share.core.SocializeMedia;
import com.example.dabutaizha.lines.R;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/6 下午4:59.
 */

public abstract class BaseSharePlatformSelector {

    private AppCompatActivity mContext;
    private OnShareSelectorDismissListener mDismissListener;
    private AdapterView.OnItemClickListener mItemClickListener;

    private static ShareTarget[] shareTargets = {
            //new ShareTarget(SocializeMedia.SINA),
            new ShareTarget(SocializeMedia.WEIXIN),
            new ShareTarget(SocializeMedia.WEIXIN_MONMENT),
            //new ShareTarget(SocializeMedia.QQ),
            //new ShareTarget(SocializeMedia.QZONE),
            //new ShareTarget(SocializeMedia.GENERIC),
            new ShareTarget(SocializeMedia.COPY)
    };


    public BaseSharePlatformSelector(AppCompatActivity context, OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        mContext = context;
        mDismissListener = dismissListener;
        mItemClickListener = itemClickListener;
    }

    public abstract void show();

    public abstract void dismiss();

    public void release() {
        mContext = null;
        mDismissListener = null;
        mItemClickListener = null;
    }

    protected static GridView createShareGridView(final Context context, AdapterView.OnItemClickListener onItemClickListener) {
        GridView grid = new GridView(context);
        ListAdapter adapter = new ArrayAdapter<ShareTarget>(context, 0, shareTargets) {
            // no need scroll
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shareboard_item, parent, false);
                view.setBackgroundDrawable(null);
                ImageView image = view.findViewById(R.id.shareboard_image);
                TextView platform = view.findViewById(R.id.shareboard_pltform);

                ShareTarget target = getItem(position);
                image.setImageResource(target.iconId);
                platform.setText(target.titleId);
                return view;
            }
        };
        grid.setNumColumns(3);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setColumnWidth(context.getResources().getDimensionPixelSize(R.dimen.shareboard_size));
        grid.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        grid.setSelector(R.drawable.background_shareboard);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(onItemClickListener);
        return grid;
    }

    public AppCompatActivity getContext() {
        return mContext;
    }

    public AdapterView.OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public OnShareSelectorDismissListener getDismissListener() {
        return mDismissListener;
    }

    public static class ShareTarget {
        public int titleId;
        public int iconId;
        public SocializeMedia media;

        public ShareTarget(SocializeMedia media) {
            this.media = media;
            switch (this.media) {
                case WEIXIN:
                    init(R.string.wx_share, R.drawable.wechat);
                    break;
                case WEIXIN_MONMENT:
                    init(R.string.wx_circle_share, R.drawable.wxcircle);
                    break;

//                case SINA:
//                    init(R.string.bili_socialize_text_sina_key, R.drawable.bili_socialize_sina_on);
//                    break;
//                case QQ:
//                    init(R.string.bili_socialize_text_qq_key, R.drawable.bili_socialize_qq_on);
//                    break;
//                case QZONE:
//                    init(R.string.bili_socialize_text_qq_zone_key, R.drawable.bili_socialize_qzone_on);
//                    break;
//                case COPY:
//                    init(R.string.bili_socialize_text_copy_url, R.drawable.bili_socialize_copy_url);
//                    break;
//                case GENERIC:

                default:
                    init(R.string.building, R.drawable.building);
                    break;
            }
        }

        private void init(int titleId, int iconId) {
            this.titleId = titleId;
            this.iconId = iconId;
        }
    }

    public interface OnShareSelectorDismissListener {
        void onDismiss();
    }

}
