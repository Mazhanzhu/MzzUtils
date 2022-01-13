package com.mazhanzhu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mazhanzhu.utils.view.GlideRoundTransUtils;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2020/7/30 10:08
 * Desc   : 图片工具类
 */
public class MzzGlide {
    public static final String TAG = "MzzGlide";

    /**
     * 将图片按照比例放大到imageview的尺寸；
     * <p>
     * ShapeImageView 必须用这个
     * </p>
     */
    public static void toView(ImageView imageView, Object url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.mzzic_test)   //图片加载出来前，显示的图片
                .error(R.mipmap.mzzic_test)            //图片加载失败后，显示的图片
                .fallback(R.mipmap.mzzic_test);        //url为空的时候,显示的图片
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .centerCrop()//将图片按照比例放大到imageview的尺寸；
//                    .fitCenter()//将图片按照比例缩小到imageview的尺寸；
//                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))//设置加载动画
                .into(imageView);
    }

    /**
     * 将图片按照比例缩小到imageview的尺寸；
     */
    public static void toViewfitCenter(ImageView imageView, Object url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.mzzic_test)   //图片加载出来前，显示的图片
                .error(R.mipmap.mzzic_test)            //图片加载失败后，显示的图片
                .fallback(R.mipmap.mzzic_test);        //url为空的时候,显示的图片
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
//                .centerCrop()//将图片按照比例放大到imageview的尺寸；
                .fitCenter()//将图片按照比例缩小到imageview的尺寸；
//                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))//设置加载动画
                .into(imageView);
    }

    /**
     * @param imageView 加载圆角图片
     * @param url       链接地址
     * @param dp        圆角大小
     */
    private static void loadImageCircle(Context context, ImageView imageView, Object url, int dp) {
        GlideRoundTransUtils transUtils = new GlideRoundTransUtils(context, dp);
        Glide.with(context).load(url).transform(transUtils).into(imageView);
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取照片角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {

        }
        return degree;
    }

    /**
     * 旋转照片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
}
