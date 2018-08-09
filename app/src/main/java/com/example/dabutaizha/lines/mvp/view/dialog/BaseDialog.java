package com.example.dabutaizha.lines.mvp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BaseDialog extends Dialog {

    private Context mContext;

    private String mContent;
    private String mPositive;
    private String mNegative;

    private TextView mDialogContent;
    private TextView mPositiveBtn;
    private TextView mNegativeBtn;

    private BaseDialogInterface mBaseDialogInterface;

    public BaseDialog(@NonNull Context context, int themeResId, String content, BaseDialogInterface inter) {
        super(context, themeResId);
        this.mContext = context;
        mContent = content;
        mPositive = ResUtil.getString(R.string.positive_defalult);
        mNegative = ResUtil.getString(R.string.negative_defalult);
        mBaseDialogInterface = inter;
    }

    public BaseDialog(@NonNull Context context, int themeResId, String content, String positive, String negative, BaseDialogInterface inter) {
        super(context, themeResId);
        this.mContext = context;
        mContent = content;
        mPositive = positive;
        mNegative = negative;
        mBaseDialogInterface = inter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base);
        ButterKnife.bind(this);

        initView();
        initViewListener();
    }

    private void initViewListener() {
        mPositiveBtn.setOnClickListener(v -> {
                mBaseDialogInterface.positive();
                BaseDialog.this.dismiss();
            }
        );
        mNegativeBtn.setOnClickListener(v -> {
            mBaseDialogInterface.negative();
            BaseDialog.this.dismiss();
        });
    }

    private void initView() {
        mDialogContent = findViewById(R.id.base_dialog_content);
        mPositiveBtn = findViewById(R.id.base_dialog_positive);
        mNegativeBtn = findViewById(R.id.base_dialog_negative);

        mDialogContent.setText(mContent);
        mPositiveBtn.setText(mPositive);
        mNegativeBtn.setText(mNegative);
    }

}
