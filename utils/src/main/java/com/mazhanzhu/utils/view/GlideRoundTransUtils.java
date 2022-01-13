package com.mazhanzhu.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.mazhanzhu.utils.MzzPxUtils;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2021/4/23 9:58
 * Desc   : Glide加载圆角图片:
 */
public class GlideRoundTransUtils extends BitmapTransformation {
    private float radius = 0f;

    public GlideRoundTransUtils(Context context, int dp) {
        this.radius = MzzPxUtils.dp2px(context,dp);
    }

    public GlideRoundTransUtils(int dp) {

    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        //返回一个正好匹配给定宽、高和配置的只包含透明像素的Bitmap
        // 如果BitmapPool中找不到这样的Bitmap，就返回null
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        //当返回null 时,创建给定宽、高和配置的新位图
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        // 设置shader
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        //抗锯齿
        paint.setAntiAlias(true);
        //画矩形
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        //绘制圆角矩形   (RectF对象,x方向上的圆角半径,y方向上的圆角半径,绘制时所使用的画笔)
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
