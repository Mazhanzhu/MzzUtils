package com.mazhanzhu.myutils.base;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/21 14:12
 * Desc   :
 */
public interface NetInterface {
    /**
     * 车辆核查历史
     */
    @GET("/v1.hitokoto.cn")
    Call<String> getDayTxt(@QueryMap Map<String,String> map);
}
