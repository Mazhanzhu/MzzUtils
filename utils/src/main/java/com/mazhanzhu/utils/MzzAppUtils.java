package com.mazhanzhu.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/18 14:36
 * Desc   :
 */
public class MzzAppUtils {
    //初始状态
    public static int ACTION = 0;
    // 加载
    public static final int ACTION_INIT = 1;
    //下拉刷新
    public static final int ACTION_REFRESH = 2;
    //加载更多
    public static final int ACTION_LOADMORE = 3;

    /**
     * 判断是否具有相关权限_权限组
     *
     * @param context     上下文
     * @param permissions 权限组
     * @return true 有权限
     */
    public static boolean checkPermission(Context context, String[] permissions) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        boolean isHave = true;
        for (String permission : permissions) {
            int per = packageManager.checkPermission(permission, packageName);
            if (PackageManager.PERMISSION_DENIED == per) {
                isHave = false;
                break;
            }
        }
        return isHave;
    }
}
