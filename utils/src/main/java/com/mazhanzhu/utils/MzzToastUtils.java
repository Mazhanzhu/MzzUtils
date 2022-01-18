package com.mazhanzhu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/18 10:34
 * Desc   : 吐司工具类
 */
public class MzzToastUtils {

    protected static Toast toast = null;

    /**
     * 吐司
     *
     * @param context 上下文
     * @param hint    提示文字
     */
    public static void showToast(Context context, String hint) {
        if (TextUtils.isEmpty(hint)) return;
        if (TextUtils.equals(hint, "null")) return;
        if (context == null) return;

        if (toast == null) {
            toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
        }
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Point size = new Point();
//        windowManager.getDefaultDisplay().getSize(size);
//        toast.setGravity(Gravity.BOTTOM, 0, size.y / 3);//距离底部三分之一位置

        toast.show();
    }
}
