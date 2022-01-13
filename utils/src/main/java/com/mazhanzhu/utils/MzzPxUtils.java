package com.mazhanzhu.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2021/6/17 10:27
 * Desc   : 尺寸工具类
 */
public class MzzPxUtils {
    public static final String TAG = "PxUtils";

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public float caculate(ArrayList<PointXY> list, Context context) {
        float temp = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                PointXY p1 = list.get(i);
                PointXY p2 = list.get(i + 1);
                temp += p1.getX() * p2.getY() - p2.getX() * p1.getY();
            } else {
                PointXY pn = list.get(i);
                PointXY p0 = list.get(0);
                temp += pn.getX() * p0.getY() - p0.getX() * pn.getY();
            }
        }
        temp = temp / 2;
        ArrayList<Float> area = getScreenSizeOfDevice(context);
        temp = (temp / area.get(0)) * area.get(1) * 2.54f;
        Log_Ma.e(TAG, "画图的面积 : " + temp);
        return temp;
    }

    private ArrayList<Float> getScreenSizeOfDevice(Context context) {
        ArrayList<Float> list02 = new ArrayList<Float>();
        Point point = new Point();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float area = point.x * point.y;//得到像素面积，
        Log_Ma.d(TAG, "屏幕的总像素面积: " + area);
        list02.clear();
        list02.add(area);
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);//得到尺寸大小
        list02.add((float) (x * y));//得到手机屏幕的物理面积
        // Log.d(TAG, "屏幕尺寸大小: "+screenInches);
        return list02;
    }

    public class PointXY {
        public float X;
        public float Y;

        public PointXY() {
            this.X = 0;
            this.Y = 0;
        }

        public PointXY(float x, float y) {
            X = x;
            Y = y;
        }

        public float getX() {
            return X;
        }

        public void setX(float x) {
            X = x;
        }

        public float getY() {
            return Y;
        }

        public void setY(float y) {
            Y = y;
        }
    }
}

