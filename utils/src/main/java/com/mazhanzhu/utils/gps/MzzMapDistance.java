package com.mazhanzhu.utils.gps;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/13 9:48
 * Desc   : 两个经纬度之间距离计算
 */
public class MzzMapDistance {
    public static final String TAG = "MzzDistanceUtils";
    private double DEF_PI = 3.14159265359; // PI
    private double DEF_2PI = 6.28318530712; // 2*PI
    private double DEF_PI180 = 0.01745329252; // PI/180.0
    private double DEF_R = 6378137.5; // 地球的半径 6378137.5

    private MzzMapDistance() {
    }

    private static MzzMapDistance instance;

    public static synchronized MzzMapDistance getInstance() {
        if (instance == null) {
            instance = new MzzMapDistance();
        }
        return instance;
    }

    /**
     * 返回为m，适合短距离测量
     * lon 经度 120.729388
     * lat 纬度 31.2579921
     */
    public double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * 返回为m,适合长距离测量
     */
    public double getLongDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2)
                * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }

    /**
     * 返回为m,适合长距离测量
     */
    public double getLongDistance(String lon1, String lat1, String lon2, String lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = Double.parseDouble(lon1) * DEF_PI180;
        ns1 = Double.parseDouble(lat1) * DEF_PI180;
        ew2 = Double.parseDouble(lon2) * DEF_PI180;
        ns2 = Double.parseDouble(lat2) * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2)
                * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }

    public interface Date_Mz {
        void success(double SUMKM);
    }
}
