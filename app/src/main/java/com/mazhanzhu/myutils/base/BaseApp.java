package com.mazhanzhu.myutils.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.mazhanzhu.utils.Log_Ma;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 15:49
 * Desc   :
 */
public class BaseApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Log_Ma.setIsShow(isApkInDebug());
        CrashHandler_Ma.getInstance().init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = mContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
