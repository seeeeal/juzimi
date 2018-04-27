package com.example.dabutaizha.lines.mvp.presenter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.mvp.adapter.ShareAdapter;
import com.example.dabutaizha.lines.mvp.contract.ShareContract;
import com.example.dabutaizha.lines.mvp.model.ShareModel;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午6:41.
 */

public class SharePresenter implements ShareContract.Presenter {

    private ShareContract.View mView;
    private ShareContract.Module mModule;

    public SharePresenter(ShareContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initData() {
        mModule = new ShareModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void getScreenshot(ShareAdapter adapter, RecyclerView recyclerView, boolean isSave) {
        mView.hideCollectImg();

        getLongScreenShotRecycleView(recyclerView, adapter, bitmap -> {
            if (isSave) {
                mModule.saveShareData(bitmap);
            } else {
                mView.getShareBitmap(bitmap);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        mView.showMessage(msg);
    }

//    /**
//     *Description: 截屏获得recyclerview内容
//     */
//    private Bitmap getRecyclerViewScreenshot(ShareAdapter adapter, RecyclerView view) {
//        Bitmap bigBitmap = null;
//        if (adapter != null) {
//            int size = adapter.getData().size();
//            int height = 0;
//            Paint paint = new Paint();
//            int iHeight = 0;
//            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//
//            // Use 1/8th of the available memory for this memory cache.
//            final int cacheSize = maxMemory / 8;
//            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
//            for (int i = 0; i < size; i++) {
//                BaseViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
//                //此处需要调用convert方法，否则绘制出来的都是空的item
//                adapter.startConvert(holder, adapter.getData().get(i));
//                holder.itemView.measure(
//                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
//                        holder.itemView.getMeasuredHeight());
//                holder.itemView.setDrawingCacheEnabled(true);
//                holder.itemView.buildDrawingCache();
//                Bitmap drawingCache = holder.itemView.getDrawingCache();
//                if (drawingCache != null) {
//
//                    bitmaCache.put(String.valueOf(i), drawingCache);
//                }
//                height += holder.itemView.getMeasuredHeight();
//            }
//
//            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.RGB_565);
//            Canvas bigCanvas = new Canvas(bigBitmap);
//            Drawable lBackground = view.getBackground();
//            if (lBackground instanceof ColorDrawable) {
//                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
//                int lColor = lColorDrawable.getColor();
//                bigCanvas.drawColor(lColor);
//            }
//
//            for (int i = 0; i < size; i++) {
//                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
//                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
//                iHeight += bitmap.getHeight();
//                bitmap.recycle();
//            }
//        }
//        return bigBitmap;
//    }

    /**
     *Description: 长截屏获得recyclerview内容
     */
    @Override
    public void getLongScreenShotRecycleView(final RecyclerView mRecyclerView,
                                             ShareAdapter adapter,
                                             final RecycleViewRecCallback callBack
                                             ) {
        if (mRecyclerView == null) {
            return;
        }
        final Paint paint = new Paint();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
        final int oneScreenHeight = mRecyclerView.getMeasuredHeight();
        int shotHeight = 0;
        if (adapter != null && adapter.getData().size() > 0) {
            int headerSize = adapter.getHeaderLayoutCount();
            int dataSize = adapter.getData().size();
            for (int i = 0; i < headerSize + dataSize; i++) {
                BaseViewHolder holder = adapter.createViewHolder(mRecyclerView, adapter.getItemViewType(i));
                if (i >= headerSize) {
                    adapter.startConvert(holder, adapter.getData().get(i - headerSize));
                }
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(mRecyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                //holder.itemView.destroyDrawingCache();//释放缓存占用的资源
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                shotHeight += holder.itemView.getHeight();
                if (shotHeight > 12000) {
                    //设置截图最大值
                    if (callBack != null) {
                        callBack.onRecFinished(null);
                    }
                    return;
                }
            }
            //添加底部高度(加载更多或loading布局高度,此处为固定值)
            final float scale = mRecyclerView.getContext().getResources().getDisplayMetrics().density;
            final int footHight = (int) (0 * scale + 0f);
            shotHeight += footHight;

            //返回到顶部
            while (mRecyclerView.canScrollVertically(-1)) {
                mRecyclerView.scrollBy(0, -oneScreenHeight);
            }

            //绘制截图的背景
            final Bitmap bigBitmap = Bitmap.createBitmap(mRecyclerView.getMeasuredWidth(), shotHeight, Bitmap.Config.ARGB_8888);
            final Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = mRecyclerView.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }


            final int[] drawOffset = {0};
            final Canvas canvas = new Canvas();
            if (shotHeight <= oneScreenHeight) {
                //仅有一页
                Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
                canvas.setBitmap(bitmap);
                mRecyclerView.draw(canvas);
                if (callBack != null) {
                    callBack.onRecFinished(bitmap);
                    mView.showCollectImg();
                }
            } else {
                //超过一页
                final int finalShotHeight = shotHeight;
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ((drawOffset[0] + oneScreenHeight < finalShotHeight)) {
                            //超过一屏
                            Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
                            canvas.setBitmap(bitmap);
                            mRecyclerView.draw(canvas);
                            bigCanvas.drawBitmap(bitmap, 0, drawOffset[0], paint);
                            drawOffset[0] += oneScreenHeight;
                            mRecyclerView.scrollBy(0, oneScreenHeight);
                            try {
                                bitmap.recycle();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            mRecyclerView.postDelayed(this, 10);
                        } else {
                            //不足一屏时的处理
                            int leftHeight = finalShotHeight - drawOffset[0] - footHight;
                            mRecyclerView.scrollBy(0, leftHeight);
                            int top = oneScreenHeight - (finalShotHeight - drawOffset[0]);
                            if (top > 0 && leftHeight > 0) {
                                Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
                                canvas.setBitmap(bitmap);
                                mRecyclerView.draw(canvas);
                                //截图,只要补足的那块图
                                bitmap = Bitmap.createBitmap(bitmap, 0, top, bitmap.getWidth(), leftHeight, null, false);
                                bigCanvas.drawBitmap(bitmap, 0, drawOffset[0], paint);
                                try {
                                    bitmap.recycle();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (callBack != null) {
                                callBack.onRecFinished(bigBitmap);
                                mView.showCollectImg();
                            }
                        }
                    }
                }, 10);
            }
        }
    }

    public interface RecycleViewRecCallback {
        void onRecFinished(Bitmap bitmap);
    }

    @Override
    public void checkIsCollect(int sentenceId) {
        mModule.checkIsCollect(sentenceId);
    }

    @Override
    public void refreshIsCollected(boolean isExist) {
        mView.refreshIsCollected(isExist);
    }

    @Override
    public void deleteData(long id) {
        mModule.deleteData(id);
    }

    @Override
    public void addData(SentencesModel model) {
        mModule.addData(model);
    }

}
