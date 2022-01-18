package com.mazhanzhu.utils.gps;

import com.mazhanzhu.utils.Log_Ma;
import com.mazhanzhu.utils.bean.Bean_WLData;

import java.util.List;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2021/8/12 15:09
 * Desc   : 判断线段与围栏集合是否相交【相交求出交点的经纬度】
 */
public class MzzGPSCheckUtils {
    public static final String TAG = "GPSCheckUtils";
    /**
     * 地球周长
     */
    private static double L = 6381372 * Math.PI * 2;
    /**
     * 平面展开后，x轴等于周长
     */
    private static double W = L;
    /**
     * y轴约等于周长一半
     */
    private static double H = L / 2;
    /**
     * 米勒投影中的一个常数，范围大约在正负2.3之间
     */
    private static double mill = 2.3;

    /**
     * 判断线段【AB两点组成的经纬度线段】与围栏集合是否相交
     *
     * @param lat_A1   A点纬度
     * @param lng_A1   A点经度
     * @param lat_B1   B点纬度
     * @param lng_B2   B点经度
     * @param queryAll 围栏集合【Bean_WLData】
     * @return 相交返回交点经纬度，默认是null
     */
    public static MzzGPSCheckUtils.LatLon getStart_End_WL(double lat_A1, double lng_A1,
                                                          double lat_B1, double lng_B2,
                                                          List<Bean_WLData> queryAll) {
        MzzGPSCheckUtils.LatLon wlPonint = null;
        SegmentLatLon LatLonOne = new SegmentLatLon();//线段一
        LatLonOne.setStartLatLon(new LatLon(lng_A1, lat_A1));
        LatLonOne.setEndLatLon(new LatLon(lng_B2, lat_B1));
        if (queryAll.size() > 1) {
            for (int j = 0; j < queryAll.size(); j++) {
                MzzGPSCheckUtils.SegmentLatLon LatLonTwo = new MzzGPSCheckUtils.SegmentLatLon();//线段二
                LatLonTwo.setStartLatLon(new MzzGPSCheckUtils.LatLon(queryAll.get(j).getLng(), queryAll.get(j).getLat()));
                if (j == queryAll.size() - 1) {
                    LatLonTwo.setEndLatLon(new MzzGPSCheckUtils.LatLon(queryAll.get(0).getLng(), queryAll.get(0).getLat()));
                } else {
                    LatLonTwo.setEndLatLon(new MzzGPSCheckUtils.LatLon(queryAll.get(j + 1).getLng(), queryAll.get(j + 1).getLat()));
                }
                wlPonint = getIntersectPoint(LatLonOne, LatLonTwo);//交点坐标（延长线相交不算）
                if (wlPonint != null) {
                    break;
                }
            }
        }
        return wlPonint;
    }

    /**
     * 将经纬度转换成X和Y轴
     * 米勒投影算法
     *
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    private static Point millierConvertion(double lat, double lon) {
        // 将经度从度数转换为弧度
        double x = lon * Math.PI / 180;
        // 将纬度从度数转换为弧度
        double y = lat * Math.PI / 180;
        // 米勒投影的转换
        y = 1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y));
        // 弧度转为实际距离
        x = (W / 2) + (W / (2 * Math.PI)) * x;
        y = (H / 2) - (H / (2 * mill)) * y;
        return new Point(x, y);
    }

    /**
     * xy轴转坐标
     *
     * @param x
     * @param y
     * @return 坐标点
     */
    private static LatLon xyToLatLon(double x, double y) {
        //实际距离 转为弧度
        x = (x - (W / 2)) / (W / (2 * Math.PI));
        y = -1 * (y - (H / 2)) / (H / (2 * mill));
        // 米勒投影的转换反转
        y = (Math.atan(Math.pow(Math.E, y / 1.25)) - 0.25 * Math.PI) / 0.4;
        //将经度从弧度转换为度数
        double lon = 180 / Math.PI * x;
        //将纬度从弧度转换为度数
        double lat = 180 / Math.PI * y;
        return new LatLon(lon, lat);
    }


    /**
     * 获取两条直线相交的点
     *
     * @param segmentLatLonOne 线段一
     * @param segmentLatLonTwo 线段二
     * @return 相交点坐标  为null 不相交
     */
    public static LatLon getIntersectPoint(SegmentLatLon segmentLatLonOne, SegmentLatLon segmentLatLonTwo) {
        //先判断有没有相交 (延长线不算) 不相交返回null 相交执行获取交点坐标 （如需获取延长线交点注释此方法）----
        if (!segIntersect(segmentLatLonOne, segmentLatLonTwo)) {
            return null;
        }
        //转换对象
        Point[] points = segmentLatLonToPoint(segmentLatLonOne, segmentLatLonTwo);
        //获取两条直线相交的点
        Point latLon = getIntersectPoint(points[0], points[1], points[2], points[3]);
        if (latLon != null) {
            return xyToLatLon(latLon.x, latLon.y);
        }
        return null;
    }


    /**
     * 获取两条直线相交的点
     *
     * @param p1 线段一 开始点
     * @param p2 线段一 结束点
     * @param p3 线段二 开始点
     * @param p4 线段二 结束点
     */
    public static Point getIntersectPoint(Point p1, Point p2, Point p3, Point p4) {

        double A1 = p1.getY() - p2.getY();
        double B1 = p2.getX() - p1.getX();
        double C1 = A1 * p1.getX() + B1 * p1.getY();

        double A2 = p3.getY() - p4.getY();
        double B2 = p4.getX() - p3.getX();
        double C2 = A2 * p3.getX() + B2 * p3.getY();

        double det_k = A1 * B2 - A2 * B1;

        if (Math.abs(det_k) < 0.00001) {
            return null;
        }

        double a = B2 / det_k;
        double b = -1 * B1 / det_k;
        double c = -1 * A2 / det_k;
        double d = A1 / det_k;

        double x = a * C1 + b * C2;
        double y = c * C1 + d * C2;

        return new Point(x, y);
    }

    /**
     * 验证两条线有没有相交
     *
     * @param segmentLatLonOne 线段1
     * @param segmentLatLonTwo 线段2
     * @return true 相交
     */
    public static boolean segIntersect(SegmentLatLon segmentLatLonOne, SegmentLatLon segmentLatLonTwo) {
        //转换对象
        Point[] points = segmentLatLonToPoint(segmentLatLonOne, segmentLatLonTwo);
        //验证两条线有没有相交
        return segIntersect(points[0], points[1], points[2], points[3]) > 0;
    }

    /**
     * 线段转换为点对象
     *
     * @param segmentLatLonOne 线段一
     * @param segmentLatLonTwo 线段二
     * @return 点对象数组
     */
    private static Point[] segmentLatLonToPoint(SegmentLatLon segmentLatLonOne, SegmentLatLon segmentLatLonTwo) {
        //线段1
        Double oneStartLat = segmentLatLonOne.getStartLatLon().getLat();
        Double oneStartLon = segmentLatLonOne.getStartLatLon().getLon();
        Double oneEndLat = segmentLatLonOne.getEndLatLon().getLat();
        Double oneEndLon = segmentLatLonOne.getEndLatLon().getLon();
        // 线段2
        Double twoStartLat = segmentLatLonTwo.getStartLatLon().getLat();
        Double twoStartLon = segmentLatLonTwo.getStartLatLon().getLon();
        Double twoEndLat = segmentLatLonTwo.getEndLatLon().getLat();
        Double twoEndLon = segmentLatLonTwo.getEndLatLon().getLon();
        Point[] points = new Point[4];
        //将经纬度转换成X和Y轴
        points[0] = millierConvertion(oneStartLat, oneStartLon);
        points[1] = millierConvertion(oneEndLat, oneEndLon);
        points[2] = millierConvertion(twoStartLat, twoStartLon);
        points[3] = millierConvertion(twoEndLat, twoEndLon);

        return points;

    }

    /**
     * 验证两条线有没有相交
     *
     * @param A 线段一 开始点
     * @param B 线段一 结束点
     * @param C 线段二 开始点
     * @param D 线段二 结束点
     * @return
     */
    public static int segIntersect(Point A, Point B, Point C, Point D) {
        Point intersection = new Point();

        if (Math.abs(B.getY() - A.getY()) + Math.abs(B.getX() - A.getX()) + Math.abs(D.getY() - C.getY())
                + Math.abs(D.getX() - C.getX()) == 0) {
            if ((C.getX() - A.getX()) + (C.getY() - A.getY()) == 0) {
                Log_Ma.e(TAG, "ABCD是同一个点！");
            } else {
                Log_Ma.e(TAG, "AB是一个点，CD是一个点，且AC不同！");
            }
            return 0;
        }

        if (Math.abs(B.getY() - A.getY()) + Math.abs(B.getX() - A.getX()) == 0) {
            if ((A.getX() - D.getX()) * (C.getY() - D.getY()) - (A.getY() - D.getY()) * (C.getX() - D.getX()) == 0) {
                Log_Ma.e(TAG, "A、B是一个点，且在CD线段上！");
            } else {
                Log_Ma.e(TAG, "A、B是一个点，且不在CD线段上！");
            }
            return 0;
        }
        if (Math.abs(D.getY() - C.getY()) + Math.abs(D.getX() - C.getX()) == 0) {
            if ((D.getX() - B.getX()) * (A.getY() - B.getY()) - (D.getY() - B.getY()) * (A.getX() - B.getX()) == 0) {
                Log_Ma.e(TAG, "C、D是一个点，且在AB线段上！");
            } else {
                Log_Ma.e(TAG, "C、D是一个点，且不在AB线段上！");
            }
            return 0;
        }

        if ((B.getY() - A.getY()) * (C.getX() - D.getX()) - (B.getX() - A.getX()) * (C.getY() - D.getY()) == 0) {
            Log_Ma.e(TAG, "线段平行，无交点！");
            return 0;
        }

        intersection
                .setX(((B.getX() - A.getX()) * (C.getX() - D.getX())
                        * (C.getY() - A.getY()) - C.getX()
                        * (B.getX() - A.getX()) * (C.getY() - D.getY()) + A
                        .getX() * (B.getY() - A.getY()) * (C.getX() - D.getX()))
                        / ((B.getY() - A.getY()) * (C.getX() - D.getX()) - (B
                        .getX() - A.getX()) * (C.getY() - D.getY())));
        intersection
                .setY(((B.getY() - A.getY()) * (C.getY() - D.getY())
                        * (C.getX() - A.getX()) - C.getY()
                        * (B.getY() - A.getY()) * (C.getX() - D.getX()) + A
                        .getY() * (B.getX() - A.getX()) * (C.getY() - D.getY()))
                        / ((B.getX() - A.getX()) * (C.getY() - D.getY()) - (B
                        .getY() - A.getY()) * (C.getX() - D.getX())));

        if ((intersection.getX() - A.getX()) * (intersection.getX() - B.getX()) <= 0
                && (intersection.getX() - C.getX())
                * (intersection.getX() - D.getX()) <= 0
                && (intersection.getY() - A.getY())
                * (intersection.getY() - B.getY()) <= 0
                && (intersection.getY() - C.getY())
                * (intersection.getY() - D.getY()) <= 0) {

            if ((A.getX() == C.getX() && A.getY() == C.getY()) || (A.getX() == D.getX() && A.getY() == D.getY())
                    || (B.getX() == C.getX() && B.getY() == C.getY()) || (B.getX() == D.getX() && B.getY() == D.getY())) {

                Log_Ma.e(TAG, "线段相交于端点上");
                return 2;
            } else {
                Log_Ma.e(TAG, "线段相交于点(" + intersection.getX() + "," + intersection.getY() + ")");
                //相交
                return 1;
            }
        } else {
            Log_Ma.e(TAG, "线段相交于虚交点(" + intersection.getX() + "," + intersection.getY() + ")");
            //相交但不在线段上
            return -1;
        }
    }

    /**
     * 点对象
     */
    public static class Point {
        private double x;
        private double y;

        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    /**
     * @program: Wys
     * @description 经纬度实体
     * @author: wys
     * @create: 2020-11-26 10:44
     **/
    public static class LatLon {

        /**
         * 经度
         */
        private Double lon;

        /**
         * 纬度
         */
        private Double lat;

        public LatLon() {
        }

        public LatLon(Double lon, Double lat) {
            this.lon = lon;
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return lon + "," + lat;
        }
    }

    /**
     * @program: Wys
     * @description 线段坐标实体
     * @author: wys
     * @create: 2020-11-27 10:44
     **/
    public static class SegmentLatLon {
        /**
         * 开始点的坐标
         */
        private LatLon startLatLon;
        /**
         * 结束点的坐标
         */
        private LatLon endLatLon;

        public SegmentLatLon(LatLon startLatLon, LatLon endLatLon) {
            this.startLatLon = startLatLon;
            this.endLatLon = endLatLon;
        }

        public SegmentLatLon() {
        }

        public LatLon getStartLatLon() {
            return startLatLon;
        }

        public void setStartLatLon(LatLon startLatLon) {
            this.startLatLon = startLatLon;
        }

        public LatLon getEndLatLon() {
            return endLatLon;
        }

        public void setEndLatLon(LatLon endLatLon) {
            this.endLatLon = endLatLon;
        }
    }
}
