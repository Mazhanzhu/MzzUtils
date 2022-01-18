package com.mazhanzhu.utils.bean;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2021/9/15 10:02
 * Desc   : 地理围栏
 */
public class Bean_WLData {
    private int id;
    private double lng;//经度 X轴
    private double lat;//纬度 Y轴

    public Bean_WLData(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public Bean_WLData(String lng, String lat) {
        this.lng = Double.parseDouble(lng);
        this.lat = Double.parseDouble(lat);
    }

    public Bean_WLData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
