package com.mazhanzhu.utils;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 16:58
 * Desc   :
 */
public class Log_Ma {
    private static final String TAG = "mzz";
    private static boolean isShow = false;

    public static void setIsShow(boolean b) {
        isShow = b;
    }

    public static void e(String tt, String string) {
        try {
            if (isShow) {
                Log.e(tt, string);
            }
        } catch (Exception e) {

        }
    }

    public static void e(String log) {
        try {
            if (isShow) {
                Log.e(TAG, log + "");
            }
        } catch (Exception e) {

        }
    }

    public static void e(Object object) {
        try {
            if (isShow) {
                Log.e(TAG, new Gson().toJson(object));
            }
        } catch (Exception e) {

        }
    }

    public static void d(String Tag, String log) {
        try {
            if (isShow) {
                Log.d(Tag, log + "");
            }
        } catch (Exception e) {

        }
    }

    public static void i(String Tag, String log) {
        try {
            if (isShow) {
                Log.i(Tag, log + "");
            }
        } catch (Exception e) {

        }
    }

    public static void w(String Tag, String log) {
        try {
            if (isShow) {
                Log.w(Tag, log + "");
            }
        } catch (Exception e) {

        }
    }

    public static void v(String Tag, String log) {
        try {
            if (isShow) {
                Log.v(Tag, log + "");
            }
        } catch (Exception e) {

        }
    }
}
