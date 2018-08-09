package com.example.dabutaizha.lines.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.mvp.view.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/22 下午6:18.
 */
public class ImageLoader {

    public static final int CARD = 0;
    public static final int DIALOGUE = 1;

    // 根据资源id加载资源
    public static void loadImageByRes(Context context, ImageView imageView, int resID) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(resID)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView);
    }

    // 加载资源图片成圆角矩形
    public static void loadRoundImageByRes(Context context, ImageView imageView, int resID, int radiusTop, int radiusBottom) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(resID)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .transform(new GlideRoundTransform(BaseApplication.getInstance(), radiusTop, radiusBottom))
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView);
    }

    // 加载资源图片成高斯模糊
    public static void loadTransformByRes(Context context, ImageView imageView, int resId) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Bitmap bitmap = drawableToBitmap(resource);
                        Bitmap blurBitmap = blurRenderScript(context, bitmap, 24);
                        imageView.setImageBitmap(addGradient(blurBitmap));
                    }

                    private Bitmap addGradient(Bitmap blurBitmap) {
                        int[] mBackShadowColors = new int[] { 0xFF000000 , 0x00000000};
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP, mBackShadowColors);
                        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gradientDrawable.setBounds(0, 0, blurBitmap.getWidth(), blurBitmap.getHeight());
                        Canvas canvas = new Canvas(blurBitmap);
                        gradientDrawable.draw(canvas);
                        return blurBitmap;
                    }
                });
    }

    // 根据路径加载资源
    public static void loadImageByPath(Context context, ImageView imageView, String path) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(new File(path))
                .placeholder(R.drawable.background_gray)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .transform(new GlideRoundTransform(BaseApplication.getInstance(), 5))
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView);
    }

    // 根据路径加载资源成圆角矩形
    public static void loadRoundImageByPath(Context context, ImageView imageView, String path, int radiusTop, int radiusBottom) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .transform(new GlideRoundTransform(BaseApplication.getInstance(), radiusTop, radiusBottom))
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView);
    }

    // 根据路径加载资源成高斯模糊
    public static void loadTransformByPath(Context context, ImageView imageView, String path) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Bitmap bitmap = drawableToBitmap(resource);
                        Bitmap blurBitmap = blurRenderScript(context, bitmap, 24);
                        imageView.setImageBitmap(addGradient(blurBitmap));
                    }

                    private Bitmap addGradient(Bitmap blurBitmap) {
                        int[] mBackShadowColors = new int[] { 0xFF000000 , 0x00000000};
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP, mBackShadowColors);
                        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gradientDrawable.setBounds(0, 0, blurBitmap.getWidth(), blurBitmap.getHeight());
                        Canvas canvas = new Canvas(blurBitmap);
                        gradientDrawable.draw(canvas);
                        return blurBitmap;

                    }
                });
    }

    // 加载网络图片
    public static void loadImageByUrl(Context context, ImageView imageView, String url) {
        // 防止在图片加载完成之前Activity视图已经销毁造成的crash
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(url)
                .fitCenter()
                .placeholder(R.color.black)
                .dontTransform()
                .into(imageView);
    }

    // 加载网络图片成喵喵喵？
    public static void loadRoundByUrl(Context context, ImageView imageView, String url) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(url)
                .error(
                        GlideApp.with(context)
                                .load(url.replace("file.juzimi.com", "www.juzimi.com/sites/default/files"))
                )
//                .transform(new GlideRoundTransform(BaseApplication.getInstance(), 24))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView);
    }

    // 加载网络图片黑色渐变模糊
    public static void loadGradientByUrl(Context context, ImageView imageView, String url) {
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Bitmap bitmap = drawableToBitmap(resource);
                        imageView.setImageBitmap(addGradient(bitmap));
                    }

                    private Bitmap addGradient(Bitmap blurBitmap) {
                        int[] mBackShadowColors = new int[] { 0x80000000 , 0x00000000};
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP, mBackShadowColors);
                        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gradientDrawable.setBounds(0, 0, blurBitmap.getWidth(), blurBitmap.getHeight());
                        Canvas canvas = new Canvas(blurBitmap);
                        gradientDrawable.draw(canvas);
                        return blurBitmap;

                    }
                });
    }

    // 加载网络图片成高斯模糊
    public static void loadTransformByUrl(Context context, ImageView imageView, String url) {
        // 防止在图片加载完成之前Activity视图已经销毁造成的crash
        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Bitmap bitmap = drawableToBitmap(resource);
                        Bitmap blurBitmap = blurRenderScript(context, bitmap, 4);
                        imageView.setImageBitmap(addGradient(blurBitmap));
                    }

                    private Bitmap addGradient(Bitmap blurBitmap) {
                        int[] mBackShadowColors = new int[] { 0x80000000 , 0x00000000};
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP, mBackShadowColors);
                        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gradientDrawable.setBounds(0, 0, blurBitmap.getWidth(), blurBitmap.getHeight());
                        Canvas canvas = new Canvas(blurBitmap);
                        gradientDrawable.draw(canvas);
                        return blurBitmap;

                    }
                });
    }

    public static void getBitmapByUrl(Context context, String url) {

        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }

        SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                saveImageToGallery(context, resource, DIALOGUE);
            }
        };

        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(simpleTarget);
    }

    // 清理Glide磁盘缓存
    public static void clearDiskCache() {
        GlideApp.get(BaseApplication.getInstance()).clearDiskCache();
    }

    // 保存图片到本地
    public static void saveImageToGallery(Context context, Bitmap bmp, int type) {
        File appDir = null;
        if (type == CARD) {
            appDir = new File(Environment.getExternalStorageDirectory(), "Lines");
        }
        if (type == DIALOGUE) {
            appDir = new File(Environment.getExternalStorageDirectory(), "LinesDialogue");
        }

        // 首先保存图片
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        String pathName = (type == CARD ? Constant.SAVE_CARD_PATH : Constant.SAVE_DIALOGUE_PATH);
        Uri uri = Uri.fromFile(new File(pathName + fileName));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    private static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        RenderScript rs = RenderScript.create(context);
        Bitmap blurredBitmap = smallBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        script.setRadius(radius);
        script.forEach(output);
        output.copyTo(blurredBitmap);

        return blurredBitmap;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }
}
