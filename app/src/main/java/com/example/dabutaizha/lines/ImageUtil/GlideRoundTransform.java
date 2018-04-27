package com.example.dabutaizha.lines.ImageUtil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/2/1 下午5:10.
 */

public class GlideRoundTransform extends BitmapTransformation {
    private float radiusTop = 0f;
    private float radiusBottom = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 5);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radiusTop = Resources.getSystem().getDisplayMetrics().density * dp;
        this.radiusBottom = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    public GlideRoundTransform(Context context, int dpTop, int dpBottom) {
        super(context);
        this.radiusTop = Resources.getSystem().getDisplayMetrics().density * dpTop;
        this.radiusBottom = Resources.getSystem().getDisplayMetrics().density * dpBottom;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        Path path = new Path();
        path.addRoundRect(rectF
                , new float[] {radiusTop, radiusTop, radiusTop, radiusTop,
                radiusBottom, radiusBottom, radiusBottom, radiusBottom}
                ,Path.Direction.CW);

        canvas.clipPath(path);
        canvas.drawRect(rectF, paint);
        return result;
    }

    public String getId() {
        return getClass().getName() + Math.round(radiusTop);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
