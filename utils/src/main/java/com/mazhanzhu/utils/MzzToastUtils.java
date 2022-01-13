package com.mazhanzhu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Describe：Toast
 */

public class MzzToastUtils {
    private static final String TAG = "ToastUtils";

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static Toast toast2 = null;

    /**
     * 吐出一个显示时间较短的提示
     */
    public static void showToast(Context context, String hint) {
        if (!TextUtils.isEmpty(hint)) {
            if (context != null) {
                if (hint.length() <= 8) {
                    if (toast == null) {
                        toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        oneTime = System.currentTimeMillis();
                        toast.show();
                    } else {
                        twoTime = System.currentTimeMillis();
                        if (twoTime - oneTime > 500) {
                            toast.setText(hint);
                            toast.show();
                            oneTime = twoTime;
                        }
                    }
                } else {
                    if (toast2 == null) {
                        toast2 = Toast.makeText(context, hint, Toast.LENGTH_LONG);
                        toast2.setGravity(Gravity.CENTER, 0, 0);
                        oneTime = System.currentTimeMillis();
                        toast2.show();
                    } else {
                        twoTime = System.currentTimeMillis();
                        if (twoTime - oneTime > 500) {
                            toast2.setText(hint);
                            toast2.show();
                            oneTime = twoTime;
                        }
                    }
                }
                Log_Ma.w(TAG, "---------" + hint);
            }
        }
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param resId 显示内容资源ID
     */
    public static void showToast(Context context, int resId) {
        if (resId > 0)
            showToast(context, context.getString(resId));
    }
}
