package com.example.dabutaizha.lines.mvp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.dabutaizha.lines.R;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/23 下午2:58.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation = LinearLayoutManager.VERTICAL;

    /**item之间分割线的size，默认为1*/
    private int mItemSize = 1;

    /**绘制item分割线的画笔，和设置其属性*/
    private Paint mPaint;

    private int mStyle;

    public ItemDecoration(Context context, int style) {
        this(context,LinearLayoutManager.VERTICAL, R.color.gray, style);
    }

    public ItemDecoration(Context context, int orientation, int style) {
        this(context,orientation, R.color.gray, style);
    }

    public ItemDecoration(Context context, int orientation, int dividerColor, int style){
        this(context, orientation, dividerColor, 1, style);
    }

    /**
     * @param context
     * @param orientation 绘制方向
     * @param dividerColor 分割线颜色 颜色资源id
     * @param mItemSize 分割线宽度 传入dp值就行
     */
    public ItemDecoration(Context context, int orientation, int dividerColor, int mItemSize, int style){
        this.mOrientation = orientation;
        this.mStyle = style;
        if(orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL){
            throw new IllegalArgumentException("请传入正确的参数") ;
        }
        //把dp值换算成px
        this.mItemSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mItemSize,context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setColor(context.getResources().getColor(dividerColor));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            drawVertical(c,parent) ;
        }else {
            drawHorizontal(c,parent) ;
        }
    }

    /**
     * 绘制纵向 item 分割线
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();

        if (mStyle == 0) {
            left = left + 100;
            right = right - 100;
        }
        if (mStyle == 1) {
            left = left + 100;
        }

        final int childSize = parent.getChildCount() ;
        for(int i = 0 ; i < childSize ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin ;
            final int bottom = top + mItemSize ;
            canvas.drawRect(left,top,right,bottom,mPaint);
        }
    }

    /**
     * 绘制横向 item 分割线
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas,RecyclerView parent){
        final int top = parent.getPaddingTop() ;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() ;
        final int childSize = parent.getChildCount() ;
        for(int i = 0 ; i < childSize ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin + 100;
            final int right = left + mItemSize - 100;
            canvas.drawRect(left,top,right,bottom, mPaint);
        }
    }

    /**
     * 设置item分割线的size
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,mItemSize);//垂直排列 底部偏移
        }else {
            outRect.set(0,0,mItemSize,0);//水平排列 右边偏移
        }
    }

}
